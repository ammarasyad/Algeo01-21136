package com.tubes.main;
import java.io.*;
import java.util.*;
import com.tubes.algeo.DoubleMatrix;
import com.tubes.algeo.MatrixFileOperator;

public class IOHandler {
    static Scanner sc = new Scanner(System.in);

    /**
     * Untuk meminta input angka dari awal hingga akhir
     * @param awal batas bawah input
     * @param akhir batas atas input
     * @return input yang berhasil
     */
    protected static int opsi (int awal, int akhir){
        int input;
        System.out.println("Masukkan angka antara " + awal + " hingga " + akhir);
        while(true){
            System.out.print("> ");
            input = sc.nextInt();
            if(awal<=input && input<=akhir){
                break;
            }
            else{
                System.out.println("Input invalid. Mohon input lagi!");
            }
        }
        return input;
    }

    /**
     * Menentukan apakah input dari keyboard atau dari file
     * @return
     */
    protected static boolean inputFile(){
        Menu.menuInput();
        int pilihan=opsi(1,2);
        return (pilihan==2);
    }

    /**
     * Proses input DoubleMatrix
     * @param row jumlah baris matriks
     * @param col jumlah kolom matriks
     * @return DoubleMatrix yang telah terisi
     */
    protected static DoubleMatrix inputDoubleMatrix(int row, int col){
        DoubleMatrix res = new DoubleMatrix(row, col);
        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                res.setElement(i, j, sc.nextDouble());
            }
        }
        return res;
    }

    /**
     * Membuat doubleMatrix dari input sebuah file
     * @return DoubleMatrix dari file sebuah absolute path
     */
    protected static DoubleMatrix fileDoubleMatrix(){
        File tmpdir;String path;
        do{
            System.out.print("Masukkan nama file\n> ");
            path = sc.next();
            path = "./test/"+path;
            tmpdir = new File(path);
            System.out.println(tmpdir.getAbsolutePath());
        }while(!tmpdir.exists());
        DoubleMatrix res = MatrixFileOperator.createDMFromFile(path);
        return res;
    }

    protected static boolean fileOutput(){
        Menu.menuOutput();
        int pilihan=opsi(1,2);
        return (pilihan==1);
    }

    protected static String outputFile(){
        System.out.print("Masukkan nama file\n> ");
        String path = sc.next();
        path = "./test/"+path;
        return path;
    }
}
