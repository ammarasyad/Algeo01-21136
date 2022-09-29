import com.tubes.algeo.DoubleMatrix;
import com.tubes.algeo.InterpolasiPolinomial;
import com.tubes.algeo.Matrix;
import com.tubes.algeo.MatrixFileOperator;
import com.tubes.algeo.RegresiLinier;

public class Main extends Menu{
    public static void main(String[] args){
        // boolean running=true; //Digunakan untuk mempermudah kembali ke menu utama
        // int input; //Pilihan user sesuai interface

        // while(running){
        //     menuUtama();
        //     input = opsi(1,7);
        //     switch(input){
        //         case 1: //SPL
        //             break;
        //         case 2: //Determinan
        //             break;
        //         case 3: //Inverse
        //             break;
        //         case 4: //Interpolasi Polinom
        //             break;
        //         case 5: //Interpolasi Bicubic
        //             break;
        //         case 6: //Regresi Linier Berganda
        //             break;
        //         default: //Input 7
        //             System.out.println("Terima kasih telah menggunakan program ini.");
        //             running = false;
        //             break;
        //     }
        // }

        //---------- BATAS SUCI PENGETESAN ----------
        MatrixFileOperator mFile = MatrixFileOperator.getInstance();
        DoubleMatrix m = mFile.createDMFromFile("D:\\Kuliah\\Semester 3\\AlGeo\\Algeo01-21136\\src\\a.txt");
        double[] y = {0.9,0.91,0.96,0.89,1.00,1.10,1.15,1.03,0.77,1.07,1.07,0.94,1.10,1.10,1.10,0.91,0.87,0.78,0.82,0.95};
        RegresiLinier.getSolution(m, y);
    }
}
