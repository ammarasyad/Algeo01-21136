Tubes 1 Aljabar Linier dan Geometri Kelompok 12 KANI
===
# Tugas Besar I IF 2123 Aljabar Linier dan Geometri Sistem Persamaan Linier, Determinan, dan Aplikasinya 
**_Program Ini Dibuat Untuk Memenuhi Tugas Perkuliahan Mata Kuliah Aljabar Linier dan Geometri (IF2123)_**
<p align="center">
Prodi Teknik Informatika <br/>
Sekolah Teknik Elektro dan Informatika<br/>
Institut Teknologi Bandung<br/>
Semester I Tahun 2021/2022<br/>
</p>


## Deskripsi
Program ini digunakan untuk:
- Menyelesaikan SPL sembarang dengan beberapa metode, yaitu metode eliminasi Gauss, metode eliminasi Gauss-Jordan, 
metode matriks balikan, dan kaidah Cramer (khusus untuk SPL dengan n peubah dan n persamaan). 
- Menyelesaikan berbagai persoalan yang dimodelkan dalam bentuk SPL, persoalan interpolasi, dan persoalan regresi. 

## Instalasi
### Prasyarat (Prerequisites)

**[ PASTIKAN CABANG REPOSITORY BERADA DI `main`, BUKAN YANG LAIN! ]** <br />

**Clone repository ini menggunakan command berikut (git bash):**
```
$ git clone https://github.com/JeremyRio/Algeo01-20077.git
```

**Download dan install:**
- [Java](https://www.java.com/en/download/)
- [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/downloads/)

*Ada dua cara untuk melakukan instalasi program, yaitu:*
### 1. Menggunakan .jar library
- [Baca cara penggunaan di sini](https://docs.google.com/document/d/1PMAWmRwJWDxBSBkY-S54BpKTzQ5K8s9Gomkzsg7dseg/edit)

### 2. Tidak Menggunakan .jar library
- Buka Terminal Operating System (Command Prompt untuk **Windows**)
- Arahkan direktori ke dalam folder repository `Algeo01-20077`
- Jalankan command:
```
$ javac -cp src src\*.java -d bin
```

## Eksekusi Program
- Buka Terminal Operating System (Command Prompt untuk **Windows**)
- Arahkan direktori ke dalam folder repository `Algeo01-20077`
- Jalankan command:
```
$ java -cp bin MenuUI
```

### Opsional
Bagi yang menggunakan **Windows** Operating System, program dapat dijalankan melalui file `Run.bat`

## Penggunaan (Usage)
### Membaca input lewat file **.txt**
Jika ingin menggunakan fungsi baca program lewat file **.txt**, diperlukan `classpath` untuk membaca file tersebut <br />
Contohnya: `test/studikasus_1a.txt`

*Default classpath berada pada folder repository `Algeo01-20077`*

### Output file **.txt**
File yang ingin disimpan melalui program terletak pada folder `output` <br />

Nama file yang ingin disimpan diperlukan ekstensi **.txt** <br />
Contohnya: `hasilkasus_1a.txt`

## Penulis
<table>
    <tr>
      <td><b>Nama</b></td>
      <td><b>NIM</b></td>
    </tr>
    <tr>
      <td><b>Ammar Rasyad Chaeroel</b></td>
      <td><b>13521136</b></td>
    </tr>
    <tr>
      <td><b>Edia Zaki Naufal Ilman</b></td>
      <td><b>13521141</b></td>
    </tr>
    <tr>
      <td><b>Bintang Dwi Marthen</b></td>
      <td><b>13521144</b></td>
    </tr>
</table>
