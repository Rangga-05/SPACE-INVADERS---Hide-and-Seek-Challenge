Janji
"Saya Muhammad Rangga Nur Praditha dengan NIM 2400297 mengerjakan evaluasi Tugas Masa Depan
dalam mata kuliah Desain dan Pemrograman Berorientasi Objek untuk keberkahanNya maka saya tidak
melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin."

Space Invaders - Hide and Seek Challenge
Tugas Masa Depan (TMD) untuk mata kuliah Desain dan Pemrograman Berorientasi Objek (DPBO) 2025. Program ini dibangun menggunakan arsitektur MVVM (Model-View-ViewModel) sesuai standar modul perkuliahan.

Penataan File
Susunan folder mengikuti standar yang ditetapkan dalam modul:
- src/: Berisi kode sumber Java (.java).
- lib/: Berisi library mysql-connector-j-9.4.0.jar.
- asset/: Berisi file gambar dan suara untuk kebutuhan game.
- out/: Direktori hasil kompilasi file biner (.class).

Prasyarat
- Java SDK terpasang di sistem.
- XAMPP (MySQL) aktif.
- Database db_game dengan tabel tbenefit sudah terimpor.

Cara Menjalankan via Terminal (PowerShell)
Pastikan Anda berada di root direktori proyek (TMD_DPBO), lalu jalankan perintah berikut:
1. Kompilasi Program
Gunakan perintah ini untuk mengompilasi seluruh package:
javac -d out/production/TMD_DPBO -cp "lib/*" src/Main.java src/model/*.java src/model/entity/*.java src/model/repository/*.java src/view/*.java src/viewmodel/*.java
2. Menjalankan Game
Gunakan perintah ini untuk mengeksekusi program dengan menyertakan library database:
java -cp "out/production/TMD_DPBO;lib/mysql-connector-j-9.4.0.jar;src" Main

Catatan Teknis
- Database Connector: Menggunakan com.mysql.cj.jdbc.Driver untuk mendukung pustaka MySQL Connector versi 8.0 ke atas.
- Arsitektur: Pemisahan logika database di model, logika permainan di viewmodel, dan antarmuka di view.

Credits & Assets Reference
Terima kasih kepada para kreator berikut atas aset berkualitas yang digunakan dalam pengembangan game ini:

Graphics & Visuals:
- Alien & UFO: Alien UFO Pack Kenney CC0 1.0
- Player & Obstacles: Space Shooter Redux Kenney CC0 1.0
- Background: Space Backgrounds by StarSoftware itch.io

Audio & Sound Effects:
- Backsound Music: Looping Retro Game Music by plasterbrain
- Shoot Sound: SFX Laser Shoot 02 by bolkmar
- Explosion Sound: 8-bit Explosion by combine2005
