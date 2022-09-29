import com.tubes.algeo.BicubicInterpolation;
import com.tubes.algeo.DoubleMatrix;
import com.tubes.algeo.MatrixOperators;

import java.util.*;

public class Main extends Interface{
    public static void main(String[] args){
         boolean running=true; //Digunakan untuk mempermudah kembali ke menu utama
         int input; //Pilihan user sesuai interface

         while(running){
             menuUtama();
             input = opsi(1,7);
             switch(input){
                 case 1: //SPL
                     break;
                 case 2: //Determinan
                     break;
                 case 3: //Inverse
                     break;
                 case 4: //Interpolasi Polinom
                     break;
                 case 5: //Interpolasi Bicubic
                     BicubicInterpolation.START();
                     break;
                 case 6: //Regresi Linier Berganda
                     break;
                 default: //Input 7
                     System.out.println("Terima kasih telah menggunakan program ini.");
                     running = false;
                     break;
             }
         }
    }
}
