# TV Apps Challenge

Aplikasi Android modern untuk menjelajahi daftar film/acara TV yang dibangun menggunakan Kotlin dan mengikuti praktik terbaik arsitektur Android saat ini.

---

## 1. Cara Menjalankan Aplikasi (How to Run)

### Prasyarat
* **Android Studio**: Ladybug / Jellyfish (atau versi yang lebih baru)
* **JDK**: Versi 17 atau lebih tinggi
* **Android SDK**: Minimum API Level 24+ (Android 7.0)

### Langkah-langkah
1. **Clone Repository**
   ```bash
   git clone https://github.com/TsukaMizu/tv_apps_challenge.git
   cd tv_apps_challenge
   ```

2. **Buka di Android Studio**
   * Buka Android Studio lalu pilih **Open**.
   * Arahkan ke direktori hasil clone `tv_apps_challenge` dan klik **OK**.
   * Tunggu proses sinkronisasi Gradle selesai.

3. **Build dan Jalankan**
   * Hubungkan perangkat Android fisik atau jalankan emulator.
   * Klik **Run 'app'** (`Shift + F10`) di Android Studio.

4. **Menjalankan Unit Test**
   ```bash
   ./gradlew test
   ```

---

## 2. Arsitektur Aplikasi (Your Architecture)

Proyek ini menerapkan **Clean Architecture** yang dikombinasikan dengan pola arsitektur **MVVM (Model-View-ViewModel)** untuk memastikan pemisahan tanggung jawab (*separation of concerns*), kemudahan pengujian (*testability*), dan pemeliharaan kode (*maintainability*).

```
┌──────────────────────────────────────────────┐
│                  UI / View                   │
│           (Activities / Composable)          │
└──────────────────────┬───────────────────────┘
                       │ Mengamati StateFlow / LiveData
┌──────────────────────▼───────────────────────┐
│                  ViewModel                   │
│         (Logika Presentasi & State)          │
└──────────────────────┬───────────────────────┘
                       │ Memanggil suspend function
┌──────────────────────▼───────────────────────┐
│               Data / Repository              │
│      (Sumber Data Remote & Caching Lokal)    │
└──────────────────────────────────────────────┘
```

### Lapisan Utama & Komponen Arsitektur:
* **UI / Presentation Layer:** Berisi komponen View/Screen dan ViewModel. Elemen UI mengamati UI state asinkron (`StateFlow` / `LiveData`) yang dipancarkan oleh ViewModel.
* **Domain / Data Layer:** Membungkus logika bisnis melalui antarmuka (`Repository`) dan implementasinya (`DefaultRepository`).
* **Konkurensi & Tugas Asinkron:** Menggunakan **Kotlin Coroutines** dan **Flow** bersama dengan `Dispatchers.IO` untuk operasi jaringan/I/O agar tidak mengganggu main thread.
* **Dependency Injection:** Memanfaatkan **Hilt / Koin** (atau Manual Constructor Injection) untuk mengurangi ketergantungan antar modul agar komponen mudah diuji secara independen.

---

## 3. Rencana Peningkatan Jika Ada Waktu Lebih (What I Would Improve With More Time)

* **Penyimpanan Lokal & Fitur Offline (Caching):** Mengimplementasikan **Room Database** agar data yang sudah di-fetch tetap dapat diakses saat perangkat tidak memiliki koneksi internet.
* **Cakupan Pengujian (Test Coverage):** Memperluas unit test untuk ViewModel dan Repository, serta menambahkan UI test (instrumentation test).
* **Peningkatan UI/UX:** Menambahkan fitur paginasi (*infinite scrolling* menggunakan `Paging 3`), efek *shimmer loading*, dan animasi transisi state yang lebih halus.
* **Modularisasi Aplikasi:** Memisah kode ke dalam beberapa modul terpisah (`:core`, `:feature:movies`, `:data`) untuk mempercepat proses build dan mempertegas batas antarmodul.