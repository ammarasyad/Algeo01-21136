package com.tubes.main;
import java.util.*;
import com.tubes.algeo.DoubleMatrix;

public class InputHandling {
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
    public static boolean inputFile(){
        System.out.println("Pilih Masukan:\n1. Keyboard\n2. File");
        int pilihan=opsi(1,2);
        return (pilihan==2);
    }

    /**
     * Proses input DoubleMatrix
     * @param row jumlah baris matriks
     * @param col jumlah kolom matriks
     * @return DoubleMatrix yang telah terisi
     */
    public static DoubleMatrix inputDoubleMatrix(int row, int col){
        DoubleMatrix res = new DoubleMatrix(row, col);
        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                res.setElement(i, j, sc.nextDouble());
            }
        }
        return res;
    }
    
}
