package com.tubes.persoalan;

import com.tubes.algeo.DoubleMatrix;
import com.tubes.algeo.MatrixOperators;

public class RegresiLinier {
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
        DoubleMatrix.printMatrix(mSPL);

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
}
