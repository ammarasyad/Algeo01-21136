package com.tubes.main;

import com.tubes.persoalan.*;
import com.tubes.algeo.*;
import java.util.*;
import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.imageio.stream.MemoryCacheImageInputStream;

public class Driver {
    static Scanner sc = new Scanner(System.in);
    static MatrixOperators mOps = MatrixOperators.getInstance();

    protected static void printGauss(DoubleMatrix m){

    }

    protected static void driverSPL(){
        DoubleMatrix mat;
        if(IOHandler.inputFile()){
            mat = IOHandler.fileDoubleMatrix();
        }
        else{
            System.out.print("Masukkan jumlah persamaan:\n> ");
            int row = sc.nextInt();
            System.out.print("Masukkan jumlah peubah:\n> ");
            int col = sc.nextInt();
            col++;
            mat = new DoubleMatrix(row,col);
            for(int i=0;i<row;i++){
                for(int j=0;j<col-1;j++){
                    System.out.printf("Masukkan nilai dari a%d,%d\n> ",i+1,j+1);
                    double x = sc.nextDouble();
                    mat.setElement(i, j, x);
                }
                System.out.printf("Masukkan nilai dari b%d\n> ",i+1);
                double x = sc.nextDouble();
                mat.setElement(i, col-1, x);
            }
        }
        double[] res;DoubleMatrix mRes;
        Menu.menuSPL();
        int pilihan = IOHandler.opsi(1,4);
        switch(pilihan){
            case 1->{
                //SPL Gauss
                System.out.println("HASIL DARI SPL TERSEBUT DENGAN METODE GAUSS");
                mRes = mOps.gauss(mat);
                DoubleMatrix.printMatrix(mRes);
                if(IOHandler.fileOutput()){
                    try{
                        String path = IOHandler.outputFile();
                        FileWriter fw = new FileWriter(path);
                        for(int i=0;i<mRes.getRow();i++){
                            fw.write("x");fw.write(Integer.toString(i+1));fw.write(" = ");
                            fw.write(Double.toString(mRes.getElement(i, 0)));fw.write("\n");
                        }
                        fw.close();
                    }
                    catch(IOException error){
                        System.out.println("Error!");
                    }
                }
            }
            case 2->{
                //SPL Gauss-Jordan
                System.out.println("HASIL DARI SPL TERSEBUT DENGAN METODE GAUSS-JORDAN");
                mRes = mOps.gaussJordan(mat);
                DoubleMatrix.printMatrix(mRes);
            }
            case 3->{
                //SPL Inverse
                DoubleMatrix mY = new DoubleMatrix(mat.getRow(),1);
                for(int i=0;i<mat.getRow();i++){
                    mY.setElement(i, 0, mat.getElement(i, mat.getCol()-1));
                }
                mRes = mOps.multiplyMatrixByMatrix(mOps.inverse(mat.getLHS()), mY);
                System.out.println("HASIL DARI SPL TERSEBUT DENGAN METODE MATRIKS BALIKAN");
                for(int i=0;i<mRes.getRow();i++){
                    System.out.printf("x%d = %f\n",i+1,mRes.getElement(i, 0));
                }
                if(IOHandler.fileOutput()){
                    try{
                        String path = IOHandler.outputFile();
                        FileWriter fw = new FileWriter(path);
                        for(int i=0;i<mRes.getRow();i++){
                            fw.write("x");fw.write(Integer.toString(i+1));fw.write(" = ");
                            fw.write(Double.toString(mRes.getElement(i, 0)));fw.write("\n");
                        }
                        fw.close();
                    }
                    catch(IOException error){
                        System.out.println("Error!");
                    }
                }
            }
            case 4->{
                //SPL Cramer
                System.out.println("HASIL DARI SPL TERSEBUT DENGAN METODE CRAMER");
                res = mOps.cramer(mat);
                for(int i=0;i<res.length;i++){
                    System.out.printf("x%d = %f\n",i+1,res[i]);
                }
                if(IOHandler.fileOutput()){
                    try{
                        String path = IOHandler.outputFile();
                        FileWriter fw = new FileWriter(path);
                        for(int i=0;i<res.length;i++){
                            fw.write("x");fw.write(Integer.toString(i+1));fw.write(" = ");
                            fw.write(Double.toString(res[i]));fw.write("\n");
                        }
                        fw.close();
                    }
                    catch(IOException error){
                        System.out.println("Error!");
                    }
                }
            }
        }
    }

    protected static void driverDeterminan(){
        DoubleMatrix mat;
        double res=0D;
        if(IOHandler.inputFile()){
            mat = IOHandler.fileDoubleMatrix();
        }
        else{
            System.out.print("Masukkan dimensi matriks (square matriks)\n> ");
            int n = sc.nextInt();
            mat = new DoubleMatrix(n);
            for(int i=0;i<n;i++){
                for(int j=0;j<n;j++){
                    System.out.printf("Masukkan nilai a%d,%d\n> ",i+1,j+1);
                    double x = sc.nextDouble();
                    mat.setElement(i, j, x);
                }
            }
        }
        System.out.println("Matriks yang diinput:");
        DoubleMatrix.printMatrix(mat);
        Menu.menuDeterminan();
        int pilihan = IOHandler.opsi(1,2);
        switch(pilihan){
            case 1->{ //reduksi baris
                res = mOps.determinant(mat,MatrixOperators.ROW_REDUCTION);
            }
            case 2->{ //ekspansi kofaktor
                res = mOps.determinant(mat,MatrixOperators.COFACTOR_EXPANSION);
            }
        }
        System.out.println("Determinan dari matriks tersebut:");
        System.out.println(res);
        if(IOHandler.fileOutput()){
            try{
                String path = IOHandler.outputFile();
                FileWriter fw = new FileWriter(path);
                fw.write("Determinan dari matriks tersebut:\n");
                fw.write(Double.toString(res));
                fw.close();
            }
            catch(IOException error){
                System.out.println("Error!");
            }
        }
    }

    protected static void driverBalikan(){
        DoubleMatrix mat,res=new DoubleMatrix(0);
        if(IOHandler.inputFile()){
            mat = IOHandler.fileDoubleMatrix();
        }
        else{
            System.out.print("Masukkan dimensi matriks (square matriks)\n> ");
            int n = sc.nextInt();
            mat = new DoubleMatrix(n);
            for(int i=0;i<n;i++){
                for(int j=0;j<n;j++){
                    System.out.printf("Masukkan nilai a%d,%d\n> ",i+1,j+1);
                    double x = sc.nextDouble();
                    mat.setElement(i, j, x);
                }
            }
        }
        System.out.println("Matriks yang diinput:");
        DoubleMatrix.printMatrix(mat);
        Menu.menuBalikan();
        int pilihan = IOHandler.opsi(1,2);
        switch(pilihan){
            case 1->{ //balikan metode matriks adjoin
                res = mOps.inverse(mat, MatrixOperators.CLASSIC);
            }
            case 2->{ //balikan metode gauss-jordan
                DoubleMatrix temp = mOps.inverse(mat,MatrixOperators.GAUSS_JORDAN);
                res = new DoubleMatrix(temp.getRow());
                for(int i=0;i<temp.getRow();i++){
                    for(int j=temp.getRow();j<temp.getCol();j++){
                        res.setElement(i, j-temp.getRow(), temp.getElement(i, j));
                    }
                }
            }
        }
        if(res==null){
            System.out.println("Matriks tidak memiliki balikan");
        }
        else{
            System.out.println("Balikan dari matriks tersebut:");
            DoubleMatrix.printMatrix(res);
        }
        if(IOHandler.fileOutput()){
            String path = IOHandler.outputFile();
            MatrixFileOperator.writeMatrixToFile(path, res);
        }
    }

    protected static void driverPolinomial(){
        DoubleMatrix mat;double x=0D; double res=0D;
        if(IOHandler.inputFile()){
            mat = IOHandler.fileDoubleMatrix();
        }
        else{
            System.out.print("Masukkan jumlah persamaan: ");
            int row = sc.nextInt();
            int col = 2;
            System.out.printf("Masukkan %d persamaannya\n",row);
            mat = IOHandler.inputDoubleMatrix(row, col);
        }
        double[] coeff = InterpolasiPolinomial.getCoefficient(mat);
        InterpolasiPolinomial.printPolinom(coeff);
        System.out.println("Apakah ingin mengtaksir suatu nilai?\n1. Ya\n2. Tidak");
        int lanjut = IOHandler.opsi(1,2);
        if(lanjut==1){
            System.out.print("Masukkan nilai dari x yang ingin ditaksir\n> ");
            x = sc.nextDouble();
            res = InterpolasiPolinomial.getEstimate(coeff, x);
            System.out.printf("Hasil estimasi dari f(%f): %f\n",x,res);
        }
        //Output file
        if(IOHandler.fileOutput()){
            boolean done = false;
            while(!done){
                try{
                    String path = IOHandler.outputFile();
                    FileWriter fw = new FileWriter(path);Boolean x0=true;
                    fw.write("Persamaan polinomial yang didapatkan dari interpolasi:\np(x) = ");
                    for(int i=coeff.length-1;i>=0;i--){
                        if(coeff[i]!=0){
                            if(x0)x0=false;
                            else if(coeff[i]>0&&!x0)fw.write(" + ");
                            fw.write(Double.toString(coeff[i]));
                            if(i!=0){
                                fw.write("x^"+Integer.toString(i));
                            }
                        }
                    }
                    if(lanjut==1){
                        fw.write("\nHasil estimasi dari f("+Double.toString(x)+"): "+Double.toString(res));
                    }
                    fw.close();
                    System.out.println("Berhasil output pada "+path);
                    done = true;
                }
                catch(IOException error){
                    System.out.println("Error!");
                }
            }
        }
    }

    protected static void driverBicubic()throws IOException{
        Menu.menuBicubic();
        int input = IOHandler.opsi(1, 2);
        if(input==1){
            driverBonus();
        }else{
            System.out.println("MASUKKAN MATRIKS 4x4!");
            DoubleMatrix m;
            if(IOHandler.inputFile()){
                m = new DoubleMatrix(IOHandler.fileDoubleMatrix().getMatrix());
            }else{
                m = new DoubleMatrix(IOHandler.inputDoubleMatrix(4, 4).getMatrix());
            }
            System.out.print("MASUKKAN TITIK X DAN Y UNTUK DIINTERPOLASI\n> ");
            double x = sc.nextDouble();
            double y = sc.nextDouble();
            DoubleMatrix func = new DoubleMatrix(16,1,BicubicInterpolation.getFunctionMatrix(m).getMatrix());
            DoubleMatrix coeff = new DoubleMatrix(16,16,BicubicInterpolation.getCoeffMatrix().getMatrix());
            DoubleMatrix invCoeff = new DoubleMatrix(16,16,mOps.inverse(coeff).getMatrix());
            DoubleMatrix values = new DoubleMatrix(16,1,BicubicInterpolation.findValues(invCoeff,func).getMatrix());
            float hasil = BicubicInterpolation.interpolation(x,y,values);
            System.out.print("Berikut hasil interpolasi: ");
            double potongHasil = Double.parseDouble(String.format("%.2f", (double)hasil));
            System.out.println(potongHasil);
            if(IOHandler.fileOutput()){
                try{
                    String path = IOHandler.outputFile();
                    FileWriter fw = new FileWriter(path);
                    fw.write("Berikut hasil interpolasi: ");
                    fw.write(Double.toString(potongHasil));
                    fw.close();
                }
                catch(IOException error){
                    System.out.println("Error!");
                }
            }
        }
        
    }

    protected static void driverRegresi(){
        //Buat Matriks untuk Diregresikan dulu
        DoubleMatrix mat;
        if(IOHandler.inputFile()){ //Input dari file
            mat = IOHandler.fileDoubleMatrix();
        }
        else{
            System.out.print("Masukkan banyak peubah x: ");
            int kolom = sc.nextInt();
            System.out.print("Masukkan banyak persamaan: ");
            int baris = sc.nextInt();
            System.out.printf("Masukkan %d persamaannya\n",baris);
            mat = IOHandler.inputDoubleMatrix(baris, kolom+1);
        }

        //Selesaikan Regresi
        double[] res = RegresiLinier.getSolution(mat);
        double hasil = 0;
        System.out.println();RegresiLinier.cetakFungsi(res);
        System.out.println();
        System.out.println("Apakah ingin mengtaksir suatu nilai?\n1. Ya\n2. Tidak");
        int lanjut = IOHandler.opsi(1,2);
        if(lanjut == 1){
            //Input Nilai Peubah ke List
            double[] peubah = new double[res.length-1];
            System.out.printf("Masukkan nilai dari %d peubahnya\n> ",res.length-1);
            for(int i=0;i<res.length-1;i++){
                peubah[i]=sc.nextDouble();
            }

            //Dapat Estimasi
            hasil = RegresiLinier.getEstimate(res, peubah);
            //Cetak Hasil
            System.out.printf("Hasil estimasi atau taksirannya: %f\n",hasil);
        }
        //Output File
        if(IOHandler.fileOutput()){
            boolean done = false;
            while(!done){
                try{
                    String path = IOHandler.outputFile();
                    FileWriter fw = new FileWriter(path);
                    fw.write("Persamaan yang didapatkan:\ny = ");
                    fw.write(Double.toString(res[0]));
                    for(int i=1;i<res.length;i++){
                        if(res[i]>0){
                            fw.write(" + ");
                        }
                        else{
                            fw.write(" - ");
                        }
                        fw.write(Double.toString(Math.abs(res[i])));
                        fw.write(" x");
                        fw.write(Integer.toString(i));
                    }
                    if(lanjut==1){
                        fw.write("\nHasil estimasi atau taksirannya: ");
                        fw.write(Double.toString(hasil));
                    }
                    fw.close();
                    System.out.println("Berhasil output pada "+path);
                    done = true;
                }
                catch(IOException error){
                    System.out.println("Error!");
                }
            }
        }
    }

    protected static void driverBonus() throws IOException{
        String path = IOHandler.outputFile();
        File file = new File(path);
        File dest = new File("D:\\zutocopy.jpg");
        try {
            //BufferedImage srcImage = ImageIO.read(new File(path));
            BufferedImage srcImage = ImageIO.read(new File("D:\\zutomayo.jpg"));
            int[] data = ImageUpscale.getNewRGB(srcImage);
            BufferedImage destImage= new BufferedImage(srcImage.getWidth()*2,srcImage.getHeight()*2,BufferedImage.TYPE_INT_RGB);
            destImage.setRGB(0, 0, srcImage.getWidth()*2, srcImage.getHeight()*2, data, 0, srcImage.getWidth()*2);
            ImageIO.write(destImage, "jpg", dest);

        } catch (IOException e) {
              System.out.println("Exception occured :" + e.getMessage());
        }
        System.out.println("Citra berhasil diperbesar!");
    }
}
