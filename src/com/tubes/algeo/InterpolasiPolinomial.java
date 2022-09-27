package com.tubes.algeo;

// Motivasi dari menggunakan DoubleMatrix disebabkan oleh hasilnya yang sangat memungkinkan mendapatkan koefisien desimal

public class InterpolasiPolinomial<T>{
    /**
     * 
     * @param coefficient List berisi nilai dari koefisien polinomial [a0,a1,...,an]
     * @param x Inputan x untuk f(x)
     * @return Perkiraan nilai f(x)
     */
    public static double getEstimate(double[]coefficient, double x){
        double res = 0;
        
        for(int i=0;i<coefficient.length;i++){
            res+=(Math.pow(x,i)*coefficient[i]); //Menjumlahkan bentuk polinomial standar
        }

        return res;
    }
}
