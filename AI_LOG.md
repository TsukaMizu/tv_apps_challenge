# AI_LOG.md

## Entry 1 – Penyusunan Struktur Project Kotlin

### 1. Apa yang saya tanyakan kepada AI / masalah yang saya selesaikan
Saya memberikan struktur awal proyek Kotlin Android yang akan digunakan untuk membuat aplikasi TV Apps. Saya meminta AI untuk melakukan review terhadap struktur tersebut, memberikan kritik, serta menyarankan perbaikan agar struktur proyek lebih rapi, scalable, dan mengikuti praktik pengembangan aplikasi Android modern.

### 2. Apa yang diberikan oleh AI
AI memberikan rekomendasi struktur proyek yang lebih terorganisir dengan menerapkan pemisahan berdasarkan tanggung jawab (separation of concerns). AI menyarankan pembagian folder seperti `data`, `repository`, `remote`, `model`, `ui`, `viewmodel`, `navigation`, dan `util`.

AI juga menyarankan beberapa penambahan file seperti:
- Model tambahan seperti `EpisodeDto`, `SeasonDto`, dan `CastDto` untuk mendukung fitur detail serial televisi.
- Folder `theme` untuk menyimpan konfigurasi tampilan Jetpack Compose.
- Folder testing untuk unit test dan instrumented test.

### 3. Apa yang saya lakukan
Saya menerima rekomendasi AI secara keseluruhan (as-is) karena struktur yang diberikan lebih mendetail dan sesuai dengan pola arsitektur aplikasi Android modern seperti MVVM.

Saya menerapkan struktur tersebut ke dalam proyek TV Apps karena pemisahan antara data layer, UI layer, dan ViewModel membuat kode lebih mudah dikembangkan dan dipelihara.

### 4. Satu hal yang AI lakukan kurang tepat atau yang saya verifikasi sendiri
AI memberikan struktur yang terlihat baik secara teori, tetapi saya tetap melakukan verifikasi dengan mencari contoh proyek Android Kotlin berbasis MVVM di GitHub.

Saya menemukan referensi proyek:
https://github.com/its-me-debk007/kotlin-android-mvvm-template/tree/main/app/src/main/java/com/debk007/template

Dari hasil perbandingan tersebut, saya memastikan bahwa struktur yang diberikan AI sudah sesuai dengan praktik umum pengembangan aplikasi Android.


---

## Entry 2 – Menambahkan Dependencies Android

### 1. Apa yang saya tanyakan kepada AI / masalah yang saya selesaikan
Saya memberikan Product Requirements Document (PRD) dari aplikasi TV Apps yang akan dibuat dan meminta AI untuk mengidentifikasi dependencies Android yang diperlukan, seperti Jetpack Compose, Navigation, Retrofit, Gson, Coil, Lifecycle, dan dependencies pendukung lainnya.

Saya juga meminta AI menjelaskan cara menambahkan dependencies tersebut ke dalam proyek.

### 2. Apa yang diberikan oleh AI
AI memberikan daftar dependencies yang diperlukan untuk proyek TV Apps dan menyarankan agar seluruh dependencies ditambahkan secara langsung ke file `build.gradle.kts`.

### 3. Apa yang saya lakukan
Saya awalnya mengikuti saran AI dengan menambahkan dependencies langsung ke `build.gradle.kts`.

Namun saat melakukan proses build, muncul banyak error karena Android Studio tidak dapat mengenali beberapa dependencies tersebut. Saya kemudian membaca error log dari Gradle dan mencari penyebabnya melalui forum Stack Overflow.

Dari hasil pencarian, saya menemukan bahwa proyek saya menggunakan sistem **Version Catalog** melalui file `libs.versions.toml`, sehingga dependencies tidak didefinisikan langsung pada `build.gradle.kts`.

Saya kemudian mencoba memindahkan satu dependency terlebih dahulu ke `libs.versions.toml` untuk memastikan apakah masalah memang berasal dari konfigurasi dependency. Setelah berhasil, saya meminta AI kembali untuk membantu membuat konfigurasi `versions` dan `libraries` yang diperlukan untuk seluruh dependencies proyek.

Konfigurasi tersebut kemudian saya terapkan ke proyek.

### 4. Satu hal yang AI lakukan kurang tepat atau yang saya verifikasi sendiri
AI mengasumsikan bahwa proyek menggunakan metode deklarasi dependencies langsung melalui `build.gradle.kts`, sedangkan proyek saya menggunakan Version Catalog (`libs.versions.toml`).

Saya memverifikasi penyebab masalah melalui error log Gradle dan referensi Stack Overflow sebelum menerapkan solusi yang diberikan AI.


---

## Entry 3 – Melakukan Testing API pada Kotlin

### 1. Apa yang saya tanyakan kepada AI / masalah yang saya selesaikan
Saya memberikan beberapa source code yang berkaitan dengan data layer, seperti `TvMazeApiService`, `NetworkModule`, dan repository. Saya meminta AI menjelaskan bagaimana cara melakukan testing koneksi API pada aplikasi Kotlin sebelum membuat tampilan utama.

### 2. Apa yang diberikan oleh AI
AI memberikan contoh kode untuk melakukan testing API melalui `MainActivity.kt` menggunakan `lifecycleScope`.

Contoh solusi yang diberikan adalah:
- Memanggil API melalui `NetworkModule.api.getShows()`.
- Menampilkan jumlah data dan beberapa nama serial melalui Logcat.
- Menangkap error menggunakan blok `try-catch`.

### 3. Apa yang saya lakukan
Saya menerima solusi tersebut dengan beberapa modifikasi.

Kode yang diberikan AI tidak dapat langsung digunakan karena tidak menyertakan import yang dibutuhkan dan tidak sepenuhnya menyesuaikan kondisi `MainActivity.kt` pada proyek yang sedang dikembangkan.

Saya mengambil bagian fungsi testing API saja dan mengintegrasikannya ke dalam `MainActivity.kt` yang sudah ada. Saya juga menambahkan import yang diperlukan serta menyesuaikan struktur kode dengan proyek TV Apps.

### 4. Satu hal yang AI lakukan kurang tepat atau yang saya verifikasi sendiri
AI tidak memberikan import yang diperlukan dan tidak mempertimbangkan bahwa `MainActivity.kt` pada tahap produksi biasanya tidak digunakan untuk melakukan testing API secara langsung.

Saya memverifikasi kembali struktur proyek dan menyesuaikan kode testing agar tidak mengganggu implementasi aplikasi utama.


---

## Entry 4 – Debugging ShowListScreen dan Error Build

### 1. Apa yang saya tanyakan kepada AI / masalah yang saya selesaikan
Setelah selesai membuat `ShowListScreen`, saya mencoba menjalankan aplikasi. Namun proses build gagal dan aplikasi tidak dapat berjalan.

Pada saat itu, `MainActivity.kt` masih berisi kode untuk testing API melalui Logcat sehingga cukup sulit menentukan sumber error. Saya meminta AI untuk menganalisis file `ShowListScreen.kt`, `MainActivity.kt`, ViewModel, dan file terkait untuk menemukan penyebab aplikasi gagal dijalankan.

### 2. Apa yang diberikan oleh AI
AI melakukan analisis terhadap kode yang diberikan dan menemukan beberapa kesalahan penulisan serta import yang tidak sesuai.

Beberapa contoh kesalahan yang ditemukan:
- Penulisan tipe data Kotlin:
```kotlin
int