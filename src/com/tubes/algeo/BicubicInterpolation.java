package com.tubes.algeo;

import com.tubes.algeo.*;

import java.util.*;
import java.lang.reflect.Array;

// Motivasi dari menggunakan DoubleMatrix disebabkan oleh hasilnya yang sangat memungkinkan mendapatkan koefisien desimal

public class BicubicInterpolation{
    static MatrixOperators mOps = MatrixOperators.getInstance();

    public static void START(){
        Scanner inp = new Scanner(System.in);
        DoubleMatrix test = new DoubleMatrix(3,3);
        ArrayList manual = new ArrayList<>(3);
        manual.add(1);
        //test.setRowElements(0,([1,);
        DoubleMatrix m = new DoubleMatrix(4,4);
        for (int i=0;i<m.getRow();i++){
            for(int j=0;j<m.getCol();j++){
                m.setElement(i,j,inp.nextDouble());
            }
        }
        System.out.println("MASUKKAN X DAN Y UNTUK DIINTERPOLASI: ");
        double x = inp.nextDouble();
        double y = inp.nextDouble();
        DoubleMatrix func = new DoubleMatrix(16,1,getFunctionMatrix(m).getMatrix());
        DoubleMatrix coeff = new DoubleMatrix(16,16,getCoeffMatrix().getMatrix());
        DoubleMatrix invCoeff = new DoubleMatrix(16,16,mOps.inverse(coeff).getMatrix());
        DoubleMatrix values = new DoubleMatrix(16,1,findValues(invCoeff,func).getMatrix());
        double hasil = interpolation(x,y,values);
        System.out.println("INI HASIL INTERPOLASI");
        System.out.println(hasil);
    }

    private static DoubleMatrix manualInverse(){
        DoubleMatrix inv = new DoubleMatrix(16,16);
        ArrayList<Double> row1= new ArrayList<Double>(Arrays.asList(0.0,0.0,0.0,0.0,0.0,36.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0));
        inv.setRowElements(0,row1);
        ArrayList<Double> row2= new ArrayList<Double>(Arrays.asList(0.0,0.0,0.0,0.0,-12.0,-18.0,36.0,-6.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0));
        ArrayList<Double> row3= new ArrayList<Double>(Arrays.asList(0.0,0.0,0.0,0.0,18.0,-36.0,18.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0));
        ArrayList<Double> row4= new ArrayList<Double>(Arrays.asList(0.0,0.0,0.0,0.0,-6.0,18.0,-18.0,6.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0));
        ArrayList<Double> row5= new ArrayList<Double>(Arrays.asList(0.0,-12.0,0.0,0.0,0.0,-18.0,0.0,0.0,0.0,36.0,0.0,0.0,0.0,-6.0,0.0,0.0));
        ArrayList<Double> row6= new ArrayList<Double>(Arrays.asList(4.0,6.0,-12.0,2.0,6.0,9.0,-18.0,3.0,-12.0,-18.0,36.0,-6.0,2.0,3.0,-6.0,1.0));
        ArrayList<Double> row7= new ArrayList<Double>(Arrays.asList(-6.0,12.0,-6.0,0.0,-9.0,18.0,-9.0,0.0,18.0,-36.0,18.0,0.0,-3.0,6.0,-3.0,0.0));
        ArrayList<Double> row8= new ArrayList<Double>(Arrays.asList(2.0,-6.0,6.0,-2.0,3.0,-9.0,9.0,-3.0,-6.0,18.0,-18.0,6.0,1.0,-3.0,3.0,-1.0));
        //BELUM KELAR
        return inv;
    }

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
        //DoubleMatrix.printMatrix(mX);
        return mX;
    }


    public static DoubleMatrix getFunctionMatrix(DoubleMatrix masukkan){
        DoubleMatrix mF = new DoubleMatrix(16, 1);
        int k=0;
        int l=0;
        for(int i=0;i<16;i++){
            if(k==4){
                l++;
                k=0;
            }
            mF.setElement(i, 0, masukkan.getElement(k,l));
            k++;
        }
        return mF;
    }

    public static DoubleMatrix findValues(DoubleMatrix m, DoubleMatrix n){
        DoubleMatrix copy = new DoubleMatrix(16,1,mOps.multiplyMatrixByMatrix(m,n).getMatrix());
        //DoubleMatrix.printMatrix(copy);
        return copy;
    }

    public static double interpolation(double x, double y, DoubleMatrix m){
        double sum=0;
        int k=0;
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                sum+= m.getElement(k,0)*Math.pow(x,i)*Math.pow(y,j);
                k++;
            }
        }
        return sum;
    }

}
