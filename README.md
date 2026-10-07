# Quiz App — Technical Test Android Developer

Aplikasi Quiz Android Native yang dibuat menggunakan **Kotlin + Jetpack Compose** untuk memenuhi Technical Test Android Developer.

## Fitur

- Alur aplikasi: Home → Pilih Tingkat Kesulitan → Quiz → Hasil
- Tersedia tingkat kesulitan Easy, Medium, dan Hard
- Setiap quiz terdiri dari 10 pertanyaan yang dibuat secara dinamis
- Setiap pertanyaan memiliki 4 pilihan jawaban unik dengan tepat 1 jawaban benar
- Menampilkan progress pertanyaan dan score saat quiz berlangsung
- User dapat memainkan quiz kembali setelah selesai
- Best score disimpan secara lokal untuk setiap tingkat kesulitan
- Unit test untuk logic utama question generator

## Tech Stack

- Kotlin
- Native Android
- Jetpack Compose + Material 3
- State management bergaya MVVM menggunakan `ViewModel` + `StateFlow`
- Navigation Compose
- DataStore Preferences
- JUnit 4

## Cara Menjalankan Project

1. Clone repository ini.
2. Buka project menggunakan Android Studio.
3. Gunakan **JDK 17**.
4. Tunggu hingga Gradle Sync selesai.
5. Jalankan konfigurasi `app` menggunakan emulator atau perangkat Android fisik.

Konfigurasi project:
- Minimum SDK: 24
- Target SDK: 35
- Compile SDK: 35

> Jika Gradle Wrapper belum tersedia di repository, project dapat disinkronkan menggunakan konfigurasi Gradle yang tersedia di Android Studio atau Gradle Wrapper dapat dibuat secara lokal menggunakan perintah `gradle wrapper`.

## Struktur Project

```text
app/src/main/java/com/arwin/quizapp/
├── data/
│   └── BestScoreRepository.kt
├── domain/
│   ├── Models.kt
│   └── QuestionGenerator.kt
├── ui/
│   ├── screens/
│   │   ├── HomeScreen.kt
│   │   ├── DifficultyScreen.kt
│   │   ├── QuizScreen.kt
│   │   └── ResultScreen.kt
│   └── theme/
│       └── Theme.kt
├── MainActivity.kt
└── QuizViewModel.kt
```

Project ini menggunakan struktur MVVM sederhana. Pemisahan menjadi banyak module atau clean architecture penuh sengaja tidak digunakan karena scope aplikasi relatif kecil dan hal tersebut dapat menambah kompleksitas yang tidak diperlukan.

## Alur Quiz

```text
Home
  ↓
Pilih Tingkat Kesulitan
  ↓
Quiz (10 pertanyaan)
  ↓
Hasil
  ├── Main Lagi
  └── Kembali ke Home
```

## Question Generator

Pertanyaan dibuat secara dinamis saat aplikasi dijalankan, bukan diambil dari bank soal statis.

Setiap object `Question` memiliki:
- teks pertanyaan
- 4 pilihan jawaban berupa integer yang unik
- tepat 1 jawaban yang benar

Pilihan jawaban yang salah dibuat berdasarkan nilai di sekitar jawaban benar, kemudian dilakukan deduplikasi dan pengacakan bersama jawaban yang benar.

### Perbedaan Tingkat Kesulitan

**Easy**
- Penjumlahan dan pengurangan
- Angka yang digunakan sebagian besar berada pada rentang 1 sampai 20

Contoh:
```text
12 + 7 = ?
```

**Medium**
- Perkalian
- Pembagian dengan hasil bilangan bulat
- Operand sebagian besar berada pada rentang 2 sampai 12

Contoh:
```text
8 × 7 = ?
```

**Hard**
- Menggunakan kombinasi beberapa operator
- Membutuhkan pemahaman sederhana mengenai prioritas operator

Contoh:
```text
16 + 4 × 6 = ?
```

Dengan pendekatan ini, perbedaan antara Easy, Medium, dan Hard diterapkan langsung pada algoritma pembentukan pertanyaan, bukan hanya pada label tingkat kesulitan.

## State Management

`QuizViewModel` menyimpan dan mengelola `QuizState`, yang mencakup:

- tingkat kesulitan yang dipilih
- daftar pertanyaan yang dihasilkan
- index pertanyaan saat ini
- score
- best score
- status apakah quiz sudah selesai

UI melakukan observasi terhadap `StateFlow<QuizState>` dan menampilkan tampilan berdasarkan state tersebut.

## Local Persistence

`BestScoreRepository` menggunakan **AndroidX DataStore Preferences** untuk menyimpan best score secara lokal.

Best score disimpan secara terpisah untuk:
- Easy
- Medium
- Hard

DataStore dipilih dibandingkan Room karena data yang perlu disimpan hanya berupa beberapa nilai sederhana dan tidak membutuhkan relational database.

## Testing

`QuestionGeneratorTest` digunakan untuk menguji business logic yang penting pada aplikasi.

Test yang tersedia memastikan bahwa:

- setiap quiz menghasilkan tepat 10 pertanyaan
- setiap pertanyaan memiliki tepat 4 pilihan jawaban yang unik
- jawaban benar terdapat tepat satu kali pada pilihan jawaban
- pertanyaan Hard menggunakan kombinasi beberapa operasi

Fokus testing bukan untuk mendapatkan coverage setinggi mungkin, tetapi untuk memastikan logic inti dari quiz berjalan sesuai requirement.

Unit test dapat dijalankan melalui Android Studio atau menggunakan:

```bash
./gradlew test
```

jika Gradle Wrapper sudah tersedia.

## Keputusan Teknis

### Jetpack Compose

Jetpack Compose dipilih karena sesuai untuk UI yang sederhana dan berbasis state. Compose juga mengurangi kebutuhan terhadap XML layout dan membuat implementasi UI lebih ringkas.

### Tidak Menggunakan Dependency Injection Framework

Framework seperti Hilt atau Koin tidak digunakan karena scope project relatif kecil. Menambahkan dependency injection framework akan menambah setup dan abstraction yang belum diperlukan.

Dependency yang dibutuhkan diberikan melalui `ViewModelProvider.Factory` sederhana.

### Pertanyaan Dibuat Secara Dinamis

Question generator dibuat secara lokal sehingga aplikasi dapat berjalan sepenuhnya secara offline dan sekaligus menunjukkan implementasi business logic tanpa membutuhkan API atau database soal.

### DataStore Dibandingkan Room

Karena data persistence hanya berupa best score, DataStore lebih sederhana dan sesuai dibandingkan menggunakan Room.

## Trade-off dan Pengembangan Selanjutnya

Jika memiliki waktu pengembangan lebih banyak, beberapa improvement yang dapat dilakukan antara lain:

- menampilkan feedback visual untuk jawaban benar atau salah sebelum berpindah ke pertanyaan berikutnya
- menambahkan mode quiz dengan timer
- menambahkan kategori pertanyaan
- menyimpan riwayat score yang lebih lengkap
- menambahkan UI test dan instrumentation test
- mendukung localization
- meningkatkan accessibility
- menyimpan state quiz apabila aplikasi tertutup ketika quiz sedang berlangsung

## Author

**Muhammad Arwin Renardi Widyoko**
