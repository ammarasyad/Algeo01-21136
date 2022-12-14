package com.tubes.main;

public class Menu {

    protected static void identitas(){
        System.out.println("TUGAS BESAR 1 ALGEO IF2123");
        System.out.println("Sistem Persamaan Linier, Determinan, dan Aplikasinya");
        System.out.println("Dibuat oleh KANI (Kelompok 12):");
        System.out.println("1. Ammar Rasyad Chaeroel    13521136");
        System.out.println("2. Edia Zaki Naufal Ilman   13521141");
        System.out.println("3. Bintang Dwi Marthen      13521144");
        System.out.println("------------------------------------");
    }

    protected static void menuUtama(){
        System.out.println("MENU");
        System.out.println("1. Sistem Persamaan Linier");
        System.out.println("2. Determinan");
        System.out.println("3. Matriks Balikan");
        System.out.println("4. Interpolasi Polinom");
        System.out.println("5. Interpolasi Bicubic");
        System.out.println("6. Regresi Linier Berganda");
        System.out.println("7. Keluar");
    }

    protected static void menuSPL(){
        System.out.println("SISTEM PERSAMAAN LINIER AKAN DISELESAIKAN DENGAN:");
        System.out.println("1. Metode Eliminasi Gauss");
        System.out.println("2. Metode Eliminasi Gauss-Jordan");
        System.out.println("3. Metode Matriks Balikan");
        System.out.println("4. Kaidah Cramer");
    }

    protected static void menuDeterminan(){
        System.out.println("MENU DETERMINAN");
        System.out.println("1. Metode Reduksi Baris");
        System.out.println("2. Metode Ekspansi Kofaktor");
    }

    protected static void menuBalikan(){
        System.out.println("MENU MATRIKS BALIKAN");
        System.out.println("1. Metode Matriks Adjoin");
        System.out.println("2. Metode Gauss-Jordan");
    }

    protected static void menuInput(){
        System.out.println("TENTUKAN JENIS INPUT");
        System.out.println("1. Input Keyboard");
        System.out.println("2. Input File");
    }

    protected static void menuOutput(){
        System.out.println("SIMPAN HASIL KE FILE");
        System.out.println("1. Ya");
        System.out.println("2. Tidak");
    }

    protected static void menuBicubic(){
        System.out.println("MENU INTERPOLASI BICUBIC");
        System.out.println("1. Memperbesar citra");
        System.out.println("2. Interpolasi matriks");
    }
}
