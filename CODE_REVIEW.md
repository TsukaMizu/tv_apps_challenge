# Review Code Snippet & Refactoring Guide

## Code Snippet Original
```kotlin
class MovieViewModel : ViewModel() {
    var movies: List<Movie> = emptyList()
    fun loadMovies() {
        val url = URL("https://api.example.com/movies")
        val data = url.readText()
        movies = parseMovies(data)
    }
}
```

Setelah mereview snippet yang diberikan, ditemukan beberapa masalah yang perlu diperbaiki.

---

## Analysis & Issues Identified

### 1. Tidak Adanya Error Handling
Pada snippet yang diberikan, pemanggilan fungsi `readText()` pada variabel `data` tidak memiliki error handling untuk menjaga apabila `readText()` gagal. Terlepas dari kesalahan terkait enkapsulasi dan layering secara keseluruhan, kesalahan ini dapat menyebabkan aplikasi mengalami kegagalan (crash) tanpa penjelasan kepada pengguna. 

Untuk memperbaiki hal tersebut, perlu ditambahkan error handling seperti berikut:

```kotlin
class MovieViewModel : ViewModel() {
    var movies: List<Movie> = emptyList()
    var errorMessage: String? = null
    fun loadMovies() {
        try {
            val url = URL("https://api.example.com/movies")
            val data = url.readText()
            movies = parseMovies(data)
            errorMessage = null
        } catch (e: Exception) {
            errorMessage = e.message ?: "Unknown error"
        }
    }
}
```

*Snippet ini telah menyelesaikan sederhana masalah tersebut, akan tetapi masih terdapat masalah lain dalam step ini. Untuk perbaikan secara menyeluruh akan dibahas pada masalah berikutnya.*

---

### 2. Masalah Enkapsulasi dan Layering (Encapsulation & Layering)
Pada snippet asli, `ViewModel` langsung melakukan operasi jaringan dengan memanggil `URL("https://api.example.com/movies").readText()`. Meskipun menambahkan `try/catch` seperti contoh di atas menangkap error pada pemanggilan `readText()`, solusi tersebut hanya menutup gejala — `ViewModel` tetap melakukan tugas yang bukan tanggung jawabnya. 

#### Akibat dari struktur tersebut:
* **Pelanggaran Enkapsulasi:** Logika akses data (networking, parsing) tersebar di dalam layer presentasi (`ViewModel`). Seharusnya logika tersebut ditempatkan di lapisan data/repository.
* **Sulit di-Unit Test:** Karena `ViewModel` membuat `URL` dan memanggil `readText()` secara langsung, kita tidak dapat dengan mudah mengganti sumber data saat melakukan unit test (mocking).
* **Ketergantungan yang Ketat:** Perubahan endpoint, parser, atau mekanisme HTTP mengharuskan perubahan langsung pada `ViewModel`.
* **Risiko Performa:** Tanpa menjalankan I/O di `Dispatchers.IO` / background thread, aplikasi dapat melakukan blocking pada main thread.

---

## Perbaikan yang Disarankan

1. Pindahkan seluruh akses jaringan dan parsing ke **data layer** (implementasi `MovieRepository`).
2. Buat `MovieRepository` sebagai interface dan sediakan implementasi `DefaultMovieRepository` yang menjalankan I/O di `Dispatchers.IO`.
3. Biarkan `ViewModel` hanya memanggil repository melalui interface tersebut, menjalankan panggilan secara non-blocking lewat `viewModelScope`, dan mengekspos status lewat `StateFlow` / `LiveData` yang read-only.

Dengan pola ini, error ditangani di titik yang tepat dan state dilindungi (enkapsulasi). Pendekatan yang lebih menyeluruh ini meletakkan `try/catch` di repository dan mengembalikan hasil terstruktur ke `ViewModel` (misal menggunakan `Result` atau `sealed class Resource`).

---

## Refactored Implementation

### 1. Movie Model
```kotlin
data class Movie(
    val id: String,
    val title: String,
    val overview: String
)
```

### 2. MovieRepository Interface
```kotlin
package com.example.tvapps.data

import kotlin.Result

interface MovieRepository {
    suspend fun getMovies(): Result<List<Movie>>
}
```

### 3. DefaultMovieRepository Implementation
```kotlin
class DefaultMovieRepository(
    private val baseUrl: String = "https://api.example.com"
) : MovieRepository {
    override suspend fun getMovies(): Result<List<Movie>> {
        return withContext(Dispatchers.IO) {
            try {
                val url = URL("$baseUrl/movies")
                val data = url.readText()
                val movies = parseMovies(data)
                Result.success(movies)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    private fun parseMovies(json: String): List<Movie> {
        // Implementation for parsing JSON
        return emptyList()
    }
}
```

### 4. Refactored MovieViewModel
```kotlin
class MovieViewModel(
    private val repository: MovieRepository
) : ViewModel() {
    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies: StateFlow<List<Movie>> = _movies.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    fun loadMovies() {
        viewModelScope.launch {
            val result = repository.getMovies()
            result.fold(
                onSuccess = {
                    _movies.value = it
                    _errorMessage.value = null
                },
                onFailure = {
                    _errorMessage.value = it.message ?: "Unknown error"
                }
            )
        }
    }
}
```