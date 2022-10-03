package com.tubes.persoalan;

import com.tubes.algeo.*;

import java.util.*;


// Motivasi dari menggunakan DoubleMatrix disebabkan oleh hasilnya yang sangat memungkinkan mendapatkan koefisien desimal

public class BicubicInterpolation{
    static MatrixOperators mOps = MatrixOperators.getInstance();
    static Scanner inp = new Scanner(System.in);
    
    /**
     * Mendapatkan hasil interpolasi dalam satu fungsi
     * @param x titik x yang akan diinterpolasi
     * @param y titik y yang akan diinterpolasi
     * @param m sumber matriks
     * @return hasil interpolasi
     */
    public static double matrixInterpolation(double x,double y,DoubleMatrix m){
        DoubleMatrix func = new DoubleMatrix(16,1,getFunctionMatrix(m).getMatrix());
        DoubleMatrix coeff = new DoubleMatrix(16,16,getCoeffMatrix().getMatrix());
        DoubleMatrix invCoeff = new DoubleMatrix(16,16,mOps.inverse(coeff).getMatrix());
        DoubleMatrix values = new DoubleMatrix(16,1,findValues(invCoeff,func).getMatrix());
        float hasil = interpolation(x,y,values);
        double potongHasil = Double.parseDouble(String.format("%.2f", (double)hasil));
        return potongHasil;
    }

    /**
     * Untuk mendapatkan matriks koefisien 16x16
     * @return matriks koefisien X
     */
    public static DoubleMatrix getCoeffMatrix(){
        DoubleMatrix mX = new DoubleMatrix(16, 16);
        int m=-1;
        int n=-1;
        for(int i=0;i<16;i++){
            if(m==3){
                n++;
                m=-1;
            }
            int k=0;
            int l=0;
            for(int j=0;j<16;j++){
                if(k==4){
                    l++;
                    k=0;
                }
                if(Math.pow(m, k)*Math.pow(n, l)==-0.0){
                    mX.setElement(i,j,0.0);
                }else{
                    mX.setElement(i, j, Math.pow(m, k)*Math.pow(n, l));
                }
                k++;
            }
            m++;
        }
        return mX;
    }

    /**
     * Mengekstrak matriks fungsi 16x1
     * @param masukan sumber matriks 4x4
     * @return matriks fungsi
     */
    public static DoubleMatrix getFunctionMatrix(DoubleMatrix masukan){
        DoubleMatrix mF = new DoubleMatrix(16, 1);
        int k=0;
        int l=0;
        for(int i=0;i<16;i++){
            if(k==4){
                l++;
                k=0;
            }
            mF.setElement(i, 0, masukan.getElement(k,l));
            k++;
        }
        return mF;
    }

    /**
     * Mendapatkan nilai variabel-variabel a dengan mengalikan matriks invers X dengan matriks fungsi
     * @param m matriks invers koefisien
     * @param n matriks fungsi
     * @return matriks nilai variabel a 16x1
     */
    public static DoubleMatrix findValues(DoubleMatrix m, DoubleMatrix n){
        DoubleMatrix copy = new DoubleMatrix(16,1,mOps.multiplyMatrixByMatrix(m,n).getMatrix());
        return copy;
    }

    /**
     * Menghitung hasil interpolasi pada titik X dan Y
     * @param x titik x
     * @param y titik y
     * @param m matriks nilai variabel a
     * @return hasil interpolasi yang dipotong menjadi 2 angka dibelakang koma
     */
    public static float interpolation(double x, double y, DoubleMatrix m){
        float sum=0;
        int k=0;
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                //System.out.println(process);
                sum+= m.getElement(k,0)*Math.pow(x,i)*Math.pow(y,j);
                k++;
                //process++;
            }
        }
        return sum;
    }
}
