package com.tubes.algeo;

// Motivasi dari menggunakan DoubleMatrix disebabkan oleh hasilnya yang sangat memungkinkan mendapatkan koefisien desimal

public class InterpolasiPolinomial{
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
        //Konversi dari input jadi matrix SPL
        DoubleMatrix matrixSPL = new DoubleMatrix(m.getRow(),m.getCol());
        for(int i=0;i<m.getRow();i++){
            for(int j=0;j<m.getCol();j++){
                if(j==(m.getRow()-1)){
                    matrixSPL.setElement(i, j, m.getElement(i, j));
                }
                else if(j==0){
                    matrixSPL.setElement(i, j, 1D);
                }
                else{
                    matrixSPL.setElement(i, j, Math.pow(m.getElement(i, j),j));
                }
            }
        }

        //Menyelesaikan SPL dalam bentuk ax=b
        DoubleMatrix solved = mOps.gaussJordan(matrixSPL);
        double[] b = new double[m.getRow()];
        for(int i=0;i<m.getRow();i++){
            b[i] = solved.getElement(i, m.getCol()-1);
        }

        return b;
    }

    public static void printPolinom(double[] b){
        boolean x0 = true;
        System.out.println("Persamaan polinomial yang didapatkan dari interpolasi:");
        for(int i=b.length-1;i>=0;i--){
            if(b[i]!=0){
                if(x0)x0=false;
                else if(b[i]>0&&!x0)System.out.print(" +");
                System.out.printf(" %f",b[i]);
                if(i!=0){
                    System.out.printf(" x^%d",i);
                }
            }
        }
    }
}
