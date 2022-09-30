package com.tubes.persoalan;

import java.util.Scanner;

import com.tubes.algeo.DoubleMatrix;
import com.tubes.algeo.MatrixOperators;

public class InterpolasiPolinomial{
    static Scanner sc = new Scanner(System.in);
    static MatrixOperators mOps = MatrixOperators.getInstance();

    /**
     * 
     * @param coefficient List berisi nilai dari koefisien polinomial [a0,a1,...,an]
     * @param x Inputan x untuk f(x)
     * @return Perkiraan nilai f(x)
     */
    public static double getEstimate(double[] coefficient, double x){
        double res=0;
        
        for(int i=0;i<coefficient.length;i++){
            res+=(Math.pow(x,i)*coefficient[i]); //Menjumlahkan bentuk polinomial standar
        }

        return res;
    }

    /**
     * 
     * @param m matrix
     * @return list berisikan koefisien polinomial [a0,a1,...,an]
     */
    public static double[] getCoefficient(DoubleMatrix m){
        //buat jadi matrix spl terlebih dahulu
        DoubleMatrix mSPL = new DoubleMatrix(m.getRow(), m.getRow()+1);
        for(int i=0;i<mSPL.getRow();i++){
            for(int j=0;j<mSPL.getCol();j++){
                if(j==mSPL.getCol()-1){//posisi nilai y
                    mSPL.setElement(i, j, m.getElement(i, 1));
                }
                else{//a0,a1,a2,...
                    mSPL.setElement(i, j, Math.pow(m.getElement(i, 0),j));
                }
            }
        }

        //penyelesaian spl
        DoubleMatrix solved = mOps.gaussJordan(mSPL);
        double[] b = new double[mSPL.getRow()];
        for(int i=0;i<mSPL.getRow();i++){
            b[i]= solved.getElement(i, solved.getCol()-1);
        }

        return b;
    }

    public static void printPolinom(double[] b){
        boolean x0 = true; //x dengan pangkat 0
        System.out.println("Persamaan polinomial yang didapatkan dari interpolasi:");
        System.out.print("p(x) = ");
        for(int i=b.length-1;i>=0;i--){
            if(b[i]!=0){
                if(x0)x0=false;
                else if(b[i]>0&&!x0)System.out.print(" + ");
                System.out.printf("%f",b[i]);
                if(i!=0){
                    System.out.printf(" x^%d",i);
                }
            }
        }
        System.out.println();
    }
}
