package com.tubes.main;
import com.tubes.algeo.DoubleMatrix;
import com.tubes.algeo.Matrix;
import com.tubes.algeo.MatrixFileOperator;
import com.tubes.persoalan.InterpolasiPolinomial;
import com.tubes.persoalan.RegresiLinier;
import com.tubes.main.*;

public class Main extends Menu{
    public static void main(String[] args){
        Menu.identitas();
        while(true){
            Menu.menuUtama();
            int input=IOHandler.opsi(1,8);
            switch(input){
                case 1 -> {
                    Driver.driverSPL();
                }
                case 2 -> {
                    Driver.driverDeterminan();
                }
                case 3 -> {
                    Driver.driverBalikan();
                }
                case 4 -> {
                    Driver.driverPolinomial();
                }
                case 5 -> {
                    Driver.driverBicubic();
                }
                case 6 -> {
                    Driver.driverRegresi();
                }
                case 7 -> {
                    Driver.driverBonus();
                }
                case 8 -> {
                    System.out.println();
                    System.out.println("Terima kasih telah menggunakan program ini ^^");
                    System.exit(0);
                }
            }
        }
    }
}
