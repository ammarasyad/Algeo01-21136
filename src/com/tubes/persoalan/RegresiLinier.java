package com.tubes.persoalan;

import com.tubes.algeo.Matrix;
import com.tubes.algeo.DoubleMatrix;
import com.tubes.algeo.MatrixOperators;
import com.tubes.algeo.MatrixFileOperator;
import com.tubes.main.InputHandler;
import java.util.*;
import java.io.*;

public class RegresiLinier {
    static Scanner sc = new Scanner(System.in);
    static MatrixOperators mOps = MatrixOperators.getInstance();

    public static double[] getSolution(DoubleMatrix m){
        //buat matrix augmented dari data
        DoubleMatrix augmented = new DoubleMatrix(m.getRow(),m.getCol()+1);
        //set kolom pertama jadi satu semua
        for(int i=0;i<augmented.getRow();i++){
            augmented.setElement(i, 0, 1D);
        }
        //salin isi matrix m ke matrix augmented
        for(int i=0;i<augmented.getRow();i++){
            for(int j=0;j<m.getCol();j++){
                augmented.setElement(i, j+1, m.getElement(i, j));
            }
        }

        //buat matrix SPL
        DoubleMatrix mSPL = new DoubleMatrix(m.getCol(),m.getCol()+1);
        for(int i=0;i<mSPL.getRow();i++){
            for(int j=0;j<mSPL.getCol();j++){
                for(int k=0;k<m.getRow();k++){
                    mSPL.setElement(i, j, mSPL.getElement(i, j)+(augmented.getElement(k, i)*augmented.getElement(k, j)));
                }
            }
        } 
        System.out.println("\nDiperoleh Matriks SPL sebagai berikut:");
        mSPL.printMatrix(mSPL);

        //Solve SPL
        DoubleMatrix solved = mOps.gaussJordan(mSPL);
        //Ekstrak Hasil SPL
        double[] res = new double[solved.getRow()];
        for(int i=0;i<solved.getRow();i++){
            res[i]= solved.getElement(i, solved.getCol()-1);
        }

        return res;
    }

    public static void cetakFungsi(double[] a){
        System.out.println("Persamaan yang didapatkan:");
        System.out.printf("y = %f",a[0]);
        for(int i=1;i<a.length;i++){
            if(a[i]>0){
                System.out.printf(" + %f x%d", a[i],i);
            }
            else{
                System.out.printf(" - %f x%d",Math.abs(a[i]),i);
            }
        }
    }

    public static double getEstimate(double[] a, double[] peubah){
        double res = a[0];
        for(int i=0;i<peubah.length;i++){
            res+=a[i+1]*peubah[i];
        }
        return res;
    }

    public static void driver(){
        //Buat Matriks untuk Diregresikan dulu
        DoubleMatrix mat;
        if(InputHandler.inputFile()){ //Input dari file
            mat = InputHandler.fileDoubleMatrix();
        }
        else{
            System.out.print("Masukkan banyak peubah x: ");
            int kolom = sc.nextInt();
            System.out.print("Masukkan banyak persamaan: ");
            int baris = sc.nextInt();
            System.out.printf("Masukkan %d persamaannya\n",baris);
            mat = InputHandler.inputDoubleMatrix(baris, kolom+1);
        }

        //Selesaikan Regresi
        double[] res = getSolution(mat);
        double hasil = 0;
        System.out.println();cetakFungsi(res);
        System.out.println();
        System.out.println("Apakah ingin lanjut ke tahapan mengtaksir nilai?\n1. Ya\n2. Tidak");
        int lanjut = InputHandler.opsi(1,2);
        if(lanjut == 1){
            //Input Nilai Peubah ke List
            double[] peubah = new double[res.length-1];
            System.out.printf("Masukkan nilai dari %d peubahnya\n",res.length-1);
            for(int i=0;i<res.length-1;i++){
                peubah[i]=sc.nextDouble();
            }

            //Dapat Estimasi
            hasil = getEstimate(res, peubah);
            //Cetak Hasil
            System.out.printf("Hasil estimasi atau taksirannya: %f\n",hasil);
        }
        //Output File
        if(InputHandler.fileOutput()){
            boolean done = false;
            while(!done){
                try{
                    System.out.print("Path file yang dituju: ");
                    String path = sc.next();
                    FileWriter fw = new FileWriter(path);
                    fw.write("Persamaan yang didapatkan:\ny = ");
                    fw.write(Double.toString(res[0]));
                    for(int i=1;i<res.length;i++){
                        if(res[i]>0){
                            fw.write(" + ");
                        }
                        else{
                            fw.write(" - ");
                        }
                        fw.write(Double.toString(Math.abs(res[i])));
                        fw.write(" x");
                        fw.write(Integer.toString(i));
                    }
                    if(lanjut==1){
                        fw.write("\nHasil estimasi atau taksirannya: ");
                        fw.write(Double.toString(hasil));
                    }
                    fw.close();
                    System.out.println("Berhasil menuliskan pada "+path);
                    done = true;
                }
                catch(IOException error){
                    System.out.println("Error!");
                }
            }
        }
    }
}
