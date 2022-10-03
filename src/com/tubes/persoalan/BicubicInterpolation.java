package com.tubes.persoalan;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import com.tubes.algeo.*;
import com.tubes.main.IOHandler;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.*;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;

import java.lang.reflect.Array;

// Motivasi dari menggunakan DoubleMatrix disebabkan oleh hasilnya yang sangat memungkinkan mendapatkan koefisien desimal

public class BicubicInterpolation{
    static int process = 1;
    static MatrixOperators mOps = MatrixOperators.getInstance();
    static Scanner inp = new Scanner(System.in);
    // BufferedImage bimage;
    // int targetWidth;
    // int targetHeight;

    public static void START()throws IOException{
        //DoubleMatrix destImage = new DoubleMatrix(height*2, width*2);
        // DoubleMatrix paddedMatrix = new DoubleMatrix(nearestNeighbour(m).getMatrix());
        // DoubleMatrix.printMatrix(paddedMatrix);
        // DoubleMatrix invCoeff = new DoubleMatrix(16,16,mOps.inverse(getCoeffMatrix()).getMatrix());
        // DoubleMatrix sampleMatrix = new DoubleMatrix(getSampleMatrix(paddedMatrix, 0, 1).getMatrix());
        // DoubleMatrix func = new DoubleMatrix(getFunctionMatrix(sampleMatrix).getMatrix());
        // DoubleMatrix.printMatrix(sampleMatrix);
        // DoubleMatrix.printMatrix(func);
        // DoubleMatrix values = new DoubleMatrix(findValues(invCoeff,func).getMatrix());
        // float hasil = interpolation(weightMatrix(1),weightMatrix(0),values);
        // System.out.println("INI HASIL INTERPOLASI");
        // double potongHasil = Double.parseDouble(String.format("%.2f", (double)hasil));
        // System.out.println(potongHasil);
        
        // DoubleMatrix testhasil = new DoubleMatrix(nearestNeighbour(m).getMatrix());
        // DoubleMatrix.printMatrix(testhasil);
    }
    
    public static double matrixInterpolation(double x,double y,DoubleMatrix m){
        DoubleMatrix func = new DoubleMatrix(16,1,getFunctionMatrix(m).getMatrix());
        DoubleMatrix coeff = new DoubleMatrix(16,16,getCoeffMatrix().getMatrix());
        DoubleMatrix invCoeff = new DoubleMatrix(16,16,mOps.inverse(coeff).getMatrix());
        DoubleMatrix values = new DoubleMatrix(16,1,findValues(invCoeff,func).getMatrix());
        float hasil = interpolation(x,y,values);
        double potongHasil = Double.parseDouble(String.format("%.2f", (double)hasil));
        return potongHasil;
    }

    /**
     * Untuk mendapatkan matriks koefisien 16x16
     * @return matriks koefisien X
     */
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
        return mX;
    }

    /**
     * Mengekstrak matriks fungsi 16x1
     * @param masukan sumber matriks 4x4
     * @return matriks fungsi
     */
    public static DoubleMatrix getFunctionMatrix(DoubleMatrix masukan){
        DoubleMatrix mF = new DoubleMatrix(16, 1);
        int k=0;
        int l=0;
        for(int i=0;i<16;i++){
            if(k==4){
                l++;
                k=0;
            }
            mF.setElement(i, 0, masukan.getElement(k,l));
            k++;
        }
        return mF;
    }

    /**
     * Mendapatkan nilai variabel-variabel a dengan mengalikan matriks invers X dengan matriks fungsi
     * @param m matriks invers koefisien
     * @param n matriks fungsi
     * @return matriks nilai variabel a 16x1
     */
    public static DoubleMatrix findValues(DoubleMatrix m, DoubleMatrix n){
        DoubleMatrix copy = new DoubleMatrix(16,1,mOps.multiplyMatrixByMatrix(m,n).getMatrix());
        return copy;
    }

    /**
     * Menghitung hasil interpolasi pada titik X dan Y
     * @param x titik x
     * @param y titik y
     * @param m matriks nilai variabel a
     * @return hasil interpolasi yang dipotong menjadi 2 angka dibelakang koma
     */
    public static float interpolation(double x, double y, DoubleMatrix m){
        float sum=0;
        int k=0;
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                //System.out.println(process);
                sum+= m.getElement(k,0)*Math.pow(x,i)*Math.pow(y,j);
                k++;
                //process++;
            }
        }
        return sum;
    }

    public static void ImageResize() throws IOException{
        BufferedImage image = ImageIO.read(new File("D:\\test.jpg"));
        BufferedImage afterImage = new BufferedImage(image.getWidth(),image.getHeight(),BufferedImage.TYPE_INT_RGB);
        int width = image.getWidth();
        int height = image.getHeight();
        System.out.println(image.getWidth());
        DoubleMatrix srcImage = new DoubleMatrix(height, width);
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                srcImage.setElement(row, col, (double)image.getRGB(col, row));
            }
        }
        DoubleMatrix destImage = new DoubleMatrix(height*2, width*2);
        DoubleMatrix paddedMatrix = new DoubleMatrix(nearestNeighbour(srcImage).getMatrix());
        DoubleMatrix invCoeff = new DoubleMatrix(16,16,mOps.inverse(getCoeffMatrix()).getMatrix());
        // DoubleMatrix sampleMatrix = new DoubleMatrix(getSampleMatrix(paddedMatrix, 0, 1).getMatrix());
        // DoubleMatrix func = new DoubleMatrix(getFunctionMatrix(sampleMatrix).getMatrix());
        // DoubleMatrix values = new DoubleMatrix(findValues(invCoeff,func).getMatrix());
        // float hasil = interpolation(weightMatrix(1),weightMatrix(0),values);
        // System.out.println("INI HASIL INTERPOLASI");
        // double potongHasil = Double.parseDouble(String.format("%.2f", (double)hasil));
        // System.out.println(potongHasil);
        int k=0;
        int l=0;
        for(int i=0;i<destImage.getRow();i++){
            for(int j=0;j<destImage.getCol();j++){
                DoubleMatrix sampleMatrix = new DoubleMatrix(getSampleMatrix(paddedMatrix, k, l).getMatrix());
                DoubleMatrix func = new DoubleMatrix(getFunctionMatrix(sampleMatrix).getMatrix());
                DoubleMatrix values = new DoubleMatrix(findValues(invCoeff,func).getMatrix());
                float hasil = interpolation(weightMatrix(i),weightMatrix(j),values);
                afterImage.setRGB(i, j, (int)hasil);
                hasil = interpolation(weightMatrix(i+1),weightMatrix(j),values);
                afterImage.setRGB(i+1, j, (int)hasil);
                hasil = interpolation(weightMatrix(i),weightMatrix(j+1),values);
                afterImage.setRGB(i, j+1, (int)hasil);
                hasil = interpolation(weightMatrix(i+1),weightMatrix(j+1),values);
                afterImage.setRGB(i+1, j+1, (int)hasil);
                l++;
                if(l==paddedMatrix.getCol()-3){
                    k++;
                    l=0;
                }
            }
        }
        ImageIO.write(afterImage, "jpg", new File("D://copy.jpg"));
                
                
                

        //     }
        // }

        //  try {
        //     ImageIO.write(image, "jpg", new File("D://imagecopy.jpg"));

        // } catch (IOException e) {
        //       System.out.println("Exception occured :" + e.getMessage());
        // }
        // ByteArrayOutputStream bArray = new ByteArrayOutputStream();
        // ImageIO.write(image, "jpg", bArray);
        // byte[] data = bArray.toByteArray();
        // for(int i=0;i<data.length/2;i++){
        //     data[i]= 0;
        // }
        // data[0]=0;
        // try(FileOutputStream fos = new FileOutputStream("D://imagecopy.jpg")){
        //     fos.write(data);
        //}
        //System.out.println(data.length);
    }

    /**
     * Membuat matriks sampel 4x4 untuk mendapatkan 16 titik
     * @param paddedmatrix matriks yang sudah dipadding
     * @param x titik horizontal awal untuk mulai sampel
     * @param y titik vertikal awal untuk mulai sampel
     * @return matriks sampel
     */
    private static DoubleMatrix getSampleMatrix(DoubleMatrix paddedMatrix, int x, int y){
        DoubleMatrix sample = new DoubleMatrix(4, 4);
        int k=0;
        for(int i=x;i<sample.getRow()+x;i++){
            int l=0;
            for(int j=y;j<sample.getCol()+y;j++){
                sample.setElement(k, l, paddedMatrix.getElement(i, j));
                l++;
            }
            k++;
        }
        return sample;
    }

    /**
     * Melakukan padding dengan nearest neigbour untuk matriks agar dapat diinterpolasi
     * @param src matriks yang akan dipadding
     * @return matriks yang sudah dipadding
     */
    private static DoubleMatrix nearestNeighbour(DoubleMatrix src){
        DoubleMatrix dest = new DoubleMatrix(src.getRow()*2+2, src.getCol()*2+2);
        double k = 0.75;
        double l = 0.75;
        int x = 1;
        int y = 1;
        for(int i=1;i<dest.getRow()-1;i++){
            k=0.75;
            x=1;
            for(int j=1;j<dest.getCol()-1;j++){
                if(Math.abs(x-k)>0.25){
                    x++;
                }
                if(Math.abs(y-l)>0.25){
                    y++;
                }
                dest.setElement(i, j, src.getElement(y-1, x-1));
                k+=0.5;
            }
            l+=0.5;
        }
        for(int i=0;i<dest.getRow();i++){
            for(int j=0;j<dest.getCol();j++){
                if((i==0 && j==0) || (i==0 && j==dest.getCol()-1) || (i==dest.getRow()-1 && j==0) || (i==dest.getRow()-1 && j==dest.getCol()-1)){
                    if(i==0 && j==0){
                        dest.setElement(i, j, dest.getElement(i+1, j+1));
                    }else if(i==0 && j==dest.getCol()-1){
                        dest.setElement(i, j, dest.getElement(i+1, j-1));
                    }else if(i==dest.getRow()-1 && j==0){
                        dest.setElement(i, j, dest.getElement(i-1, j+1));
                    }else{
                        dest.setElement(i, j, dest.getElement(i-1, j-1));
                    }
                }else if(i==0 && j!=0 && j!=dest.getCol()-1){
                    dest.setElement(i, j, dest.getElement(i+1, j));
                }else if(i==dest.getRow()-1 && j!=0 && j!=dest.getCol()-1){
                    dest.setElement(i, j, dest.getElement(i-1, j));
                }else if(j==0 && i!=0 && i!=dest.getRow()-1){
                    dest.setElement(i, j, dest.getElement(i, j+1));
                }else if(j==dest.getCol()-1 && i!=0 && i!=dest.getRow()-1){
                    dest.setElement(i, j, dest.getElement(i, j-1));
                }
            }
        }
        return dest;
    }

    public static double weightMatrix(int d){
        double f = (d+0.5)*2 -0.5;
        f-=Math.floor(f);
        return f;
    }

    public static int Clamp(int val, int a, int b){
        if(val < a) val = a;
        else if(val > b) val = b;

        return val;
    }

    private static DoubleMatrix getFValue(int xStart, int yStart, BufferedImage bimage){
        // System.out.printf("%d, %d", xStart, yStart);
        DoubleMatrix res = new DoubleMatrix(16, 1);
        int[] img = bimage.getRGB(0, 0, bimage.getWidth(), bimage.getHeight(), null, 0, bimage.getWidth());

        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                res.setElement(4*i+j, 0, (double)img[bimage.getWidth()*(Clamp(yStart + i - 2, 0, bimage.getHeight()-1)) + (Clamp(xStart + j -2, 0, bimage.getHeight()-1))]);
            }
        }

        return res;
    }

    public static void solve() throws IOException {
        // int[] data = this.image.getColorData();
        Scanner inp = new Scanner(System.in);
        // System.out.println("Masukkan file gambar!");
        // String path = inp.nextLine();
        //BufferedImage test = ImageIO.read(new File(path));
        BufferedImage bimage = ImageIO.read(new File("D:\\test.jpg"));
        int targetHeight = bimage.getHeight();
        int targetWidth = bimage.getWidth();
        int[] newData = new int[targetHeight*targetWidth];

        double xScale = (double)bimage.getWidth()/targetWidth;
        double yScale = (double)bimage.getHeight()/targetHeight;
        DoubleMatrix invCoeff = new DoubleMatrix(16,16,mOps.inverse(getCoeffMatrix()).getMatrix());

        // System.out.printf("%d x %d = %d", this.targetWidth, this.targetHeight, data.length);
        for(int i = 0; i < targetWidth; i++){
            for(int j = 0; j < targetHeight; j++){
                DoubleMatrix func = new DoubleMatrix(getFValue((int)(j*xScale), (int)(i*yScale),bimage).getMatrix());
                DoubleMatrix values = new DoubleMatrix(findValues(invCoeff,func).getMatrix());
                // bi.solve(); //deprecated

                newData[i*targetWidth + j] = (int)interpolation(1/xScale, 1/yScale, values);
                
            }
        }

        BufferedImage result = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
        result.setRGB(0, 0, targetWidth, targetHeight, newData, 0, targetWidth);

        bimage.setRGB(0, 0, targetWidth, targetHeight, newData, 0, targetWidth);
        ImageIO.write(bimage, "jpg", new File("D://copy.jpg"));
        // System.out.printf("%d <<", newData[this.targetWidth*targetHeight-1000]);
    }

}
