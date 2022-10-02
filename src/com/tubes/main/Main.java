package com.tubes.main;

import java.io.IOException;

import com.tubes.algeo.BicubicInterpolation;

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
                    try {
                        BicubicInterpolation.START();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                case 6 -> {
                    Driver.driverRegresi();
                }
                case 7 -> {
                    System.out.println("Terima kasih telah menggunakan program ini ^^");
                    System.exit(0);
                }
            }
        }
    }
}
