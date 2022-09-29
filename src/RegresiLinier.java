package com.tubes.algeo;

public class RegresiLinier {
    public static double[] getSolution(DoubleMatrix m, double[] y){
        //buat matrix augmented dari data
        DoubleMatrix augmented = new DoubleMatrix(m.getRow(),m.getCol()+2);
        //set kolom pertama jadi satu semua
        for(int i=0;i<augmented.getRow();i++){
            augmented.setElement(i, 0, 1D);
        }
        //salin isi matrix m ke bagian tengah matrix augmented
        for(int i=0;i<augmented.getRow();i++){
            for(int j=0;j<m.getCol();j++){
                augmented.setElement(i, j+1, m.getElement(i, j));
            }
        }
        //salin isi y ke matrix augmented
        for(int i=0;i<augmented.getRow();i++){
            augmented.setElement(i, augmented.getCol()-1, y[i]);
        }
        //matrix augmented sudah aman

        //buat matrix SPL [need fix]
        DoubleMatrix mSPL = new DoubleMatrix(m.getCol()+1,m.getCol()+2); //dimensi udah bener
        for(int i=0;i<mSPL.getRow();i++){
            for(int j=0;j<mSPL.getCol();j++){
                mSPL.setElement(i, j, 0D);
                for(int k=0;k<m.getRow();k++){
                    //fix this
                    mSPL.setElement(i, j, augmented.getElement(k, i)*augmented.getElement(k, j));
                }
            }
        }
        System.out.println("\nDiperoleh Matriks SPL sebagai berikut:");
        mSPL.printMatrix(mSPL);

        return null;
    }
}
