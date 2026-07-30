# Reflection

## 1. Which part of your submission are you least confident about, and why?
Bagian dari submission yang paling kurang saya percaya diri adalah implementasi UI menggunakan Jetpack Compose dan Kotlin.

Sebelum mengerjakan project ini, saya hanya pernah mempelajari dasar-dasar Kotlin ketika kuliah, tetapi belum pernah mengimplementasikan Kotlin ke dalam sebuah project secara lengkap. Pengalaman saya sebelumnya dalam pengembangan aplikasi mobile lebih banyak menggunakan Flutter dan React Native, sehingga penggunaan Kotlin dan Jetpack Compose menjadi tantangan baru bagi saya.

Karena itu, pada awal pengerjaan saya merasa kurang percaya diri dalam memahami syntax Kotlin, cara membangun UI menggunakan Jetpack Compose, serta bagaimana menyusun struktur aplikasi Android menggunakan arsitektur yang baik.

Untuk mengatasi hal tersebut, saya melakukan pembelajaran secara mandiri melalui pendekatan project-based learning, terutama dengan mempelajari bagaimana sebuah project Android Kotlin dibangun, pattern apa saja yang umum digunakan, serta bagaimana hubungan antara berbagai layer seperti UI, ViewModel, Repository, dan Data Source.

Ketika mengalami kesulitan dalam memahami syntax Kotlin atau konsep tertentu, saya menggunakan AI sebagai alat bantu untuk menjelaskan konsep, melakukan review kode, dan memberikan alternatif solusi. Namun, saya tetap melakukan verifikasi melalui dokumentasi, error message dari compiler, serta membandingkan dengan praktik pengembangan Android yang umum digunakan.
---

## 2. Describe a moment during this project (or any past project) where you got completely stuck. What did you do, step by step?
Salah satu momen ketika saya benar-benar mengalami kesulitan dalam project ini adalah ketika saya selesai membuat bagian model dan remote API configuration, tetapi project mengalami error pada dependency sehingga aplikasi tidak dapat melakukan build.

Pada awalnya saya mengira masalah tersebut disebabkan oleh versi dependency yang tidak sesuai. Saya mencoba membaca error log dari Gradle untuk memahami sumber masalah dan melakukan pengecekan terhadap konfigurasi project.

Setelah melakukan investigasi lebih lanjut, saya menemukan bahwa project saya menggunakan sistem **Version Catalog (`libs.versions.toml`)**, sedangkan saya sebelumnya menambahkan dependency langsung melalui `build.gradle.kts`. Hal tersebut menyebabkan Android Studio tidak dapat mengenali beberapa dependency yang digunakan.

Langkah yang saya lakukan adalah:

1. Membaca error log Gradle untuk mengetahui masalah utama yang menyebabkan build gagal.
2. Mengecek bagaimana struktur pengelolaan dependency pada project saya.
3. Mencari referensi tambahan melalui forum developer seperti Stack Overflow untuk memahami masalah yang terjadi.
4. Menguji solusi dengan memindahkan satu dependency terlebih dahulu ke `libs.versions.toml` untuk memastikan bahwa penyebab masalah memang berasal dari konfigurasi dependency.
5. Setelah terbukti berhasil, saya memperbaiki konfigurasi dependency lainnya dan menyesuaikannya dengan struktur project.
6. Melakukan rebuild hingga project berhasil dijalankan kembali.

Selain masalah dependency, saya juga mengalami kesulitan ketika melakukan testing API call. Beberapa kali API tidak berjalan sesuai ekspektasi sehingga saya kesulitan menentukan apakah masalah berasal dari API service, repository, coroutine, atau konfigurasi aplikasi.

Untuk mengatasinya, saya membuat proses testing sederhana melalui Logcat, mengecek response dari API, melakukan pengecekan terhadap konfigurasi Retrofit, dan mengintegrasikan setiap bagian secara bertahap.

Dari pengalaman tersebut, saya belajar bahwa proses debugging harus dilakukan secara sistematis dengan memahami pesan error, mempersempit sumber masalah, dan melakukan validasi terhadap setiap asumsi.
---

## 3. Imagine: it's Thursday, your task is due Friday, and you realize you misunderstood the requirement, half your work is wrong. What are you doing now?
Jika saya menyadari bahwa saya salah memahami requirement ketika deadline sudah dekat, hal pertama yang saya lakukan adalah mengevaluasi dampak dari kesalahan tersebut terlebih dahulu, bukan langsung menghapus atau membuat ulang seluruh pekerjaan.

Langkah yang akan saya lakukan:

1. Membaca kembali requirement awal dan mengidentifikasi bagian mana yang masih sesuai dan dapat digunakan.
2. Memisahkan bagian yang salah berdasarkan tingkat prioritas, terutama fitur utama yang wajib diselesaikan.
3. Memperbaiki bagian yang paling berdampak terhadap hasil akhir terlebih dahulu.
4. Jika risiko tidak selesai tepat waktu cukup besar, saya akan mengkomunikasikan kondisi tersebut kepada mentor atau anggota tim dengan menjelaskan progress saat ini, masalah yang ditemukan, serta solusi yang saya usulkan.

Saya akan fokus memastikan fungsi utama dapat berjalan dengan baik terlebih dahulu dibandingkan mencoba menyelesaikan seluruh fitur tetapi menghasilkan kualitas yang kurang baik.

Bagi saya, mengelola prioritas dan komunikasi merupakan bagian penting ketika menghadapi situasi dengan tekanan waktu.

---

## 4. Your mentor asks you to change an approach you believe is worse. What do you do?
Jika mentor meminta saya mengganti pendekatan yang menurut saya kurang optimal, saya akan mencoba memahami terlebih dahulu alasan dan pertimbangan di balik perubahan tersebut.

Saya akan berdiskusi secara terbuka dengan menyampaikan pendapat saya beserta alasan teknisnya, termasuk kelebihan dan kekurangan dari pendekatan yang sedang digunakan. Jika diperlukan, saya akan memberikan pendukung berupa dokumentasi, referensi, atau dampak terhadap project.

Namun, apabila setelah diskusi keputusan akhirnya tetap menggunakan pendekatan yang disarankan oleh mentor, saya akan mengikuti keputusan tersebut dan mencoba memahami alasan mengapa pendekatan tersebut dipilih.

Menurut saya, tujuan utama dalam sebuah project bukan mempertahankan pendapat pribadi, tetapi menemukan solusi terbaik untuk menghasilkan produk yang berkualitas. Feedback dari orang yang lebih berpengalaman juga dapat memberikan perspektif baru yang sebelumnya belum saya pertimbangkan.
---

## 5. What's something technical you taught yourself recently outside of class/work, and how did you learn it?
Hal teknis yang baru-baru ini saya pelajari secara mandiri adalah pengembangan aplikasi Android menggunakan Kotlin dan Jetpack Compose.

Walaupun saya sudah mempelajari dasar Kotlin ketika kuliah, saya belum pernah menggunakan Kotlin untuk membangun aplikasi mobile secara lengkap. Karena project ini memiliki requirement menggunakan Kotlin dan Jetpack Compose, saya memutuskan untuk memperdalam kemampuan tersebut secara mandiri.

Saya mempelajarinya melalui beberapa metode, seperti:
- Mengikuti tutorial berbasis project untuk memahami bagaimana aplikasi Android Kotlin dibangun.
- Membaca dokumentasi Android Developer.
- Mempelajari beberapa repository open source di GitHub untuk melihat struktur project Android yang sudah menerapkan pattern tertentu.
- Mencoba langsung membuat implementasi menggunakan MVVM Architecture, Repository Pattern, Retrofit API Integration, ViewModel, dan state management pada Jetpack Compose.

Selama proses belajar, saya juga menggunakan AI tools sebagai alat bantu untuk memahami syntax Kotlin yang belum familiar, melakukan review kode, dan membantu menganalisis error yang muncul.

Namun, saya tidak hanya menerima jawaban dari AI secara langsung. Saya tetap melakukan validasi melalui error message compiler, dokumentasi resmi, serta melakukan percobaan langsung pada project.

Melalui proses ini, saya tidak hanya belajar menggunakan Kotlin dan Jetpack Compose, tetapi juga belajar bagaimana mempelajari teknologi baru secara mandiri dan menyelesaikan masalah secara sistematis.
