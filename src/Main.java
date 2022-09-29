import com.tubes.algeo.DoubleMatrix;
import com.tubes.algeo.InterpolasiPolinomial;
import com.tubes.algeo.MatrixFileOperator;
import com.tubes.algeo.Matrix;

public class Main extends Interface{
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
        DoubleMatrix input = mFile.createDMFromFile("D:\\Kuliah\\Semester 3\\AlGeo\\Algeo01-21136\\src\\a.txt");
        Matrix.printMatrix(input);
        double[] b = {1,2,3,4};
        InterpolasiPolinomial.printPolinom(b);
        double[] c = {0.6762,0.2266,-0.0064};
        double prediksiHasil = InterpolasiPolinomial.getEstimate(c, 9.2);
        System.out.println(prediksiHasil);
        // double[] coeff = InterpolasiPolinomial.getCoefficient(input);
        // InterpolasiPolinomial.printPolinom(coeff);
    }
}
