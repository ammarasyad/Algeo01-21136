package com.tubes.persoalan;
import java.awt.image.BufferedImage;
import com.tubes.algeo.DoubleMatrix;
import com.tubes.algeo.MatrixOperators;

public class ImageUpscale {
    static MatrixOperators mOps = MatrixOperators.getInstance();

    /**
     * Mendapatkan nilai data RGB baru hasil interpolasi
     * @param src sumber citra yang akan diperbesar
     * @return data RGB baru
     */
    public static int[] getNewRGB(BufferedImage src){
        int destHeight = src.getHeight()*2;
        int destWidth = src.getWidth()*2;
        int[] arrayRGB = new int[destHeight*destWidth];
        DoubleMatrix invCoeff = new DoubleMatrix(16,16,mOps.inverse(BicubicInterpolation.getCoeffMatrix()).getMatrix());
        for(int i = 0; i < destWidth; i++){
            for(int j = 0; j < destHeight; j++){
                DoubleMatrix func = new DoubleMatrix(getRGBFunc((int)(j*0.5), (int)(i*0.5),src).getMatrix());
                DoubleMatrix values = new DoubleMatrix(BicubicInterpolation.findValues(invCoeff,func).getMatrix());
                arrayRGB[i*destWidth + j] = (int)BicubicInterpolation.interpolation(2, 2, values);
                
            }
        }
        return arrayRGB;
    }

    /**
     * Mengambil dan membuat matriks fungsi dari data RGB citra
     * @param X titik horizontal mulai diambilnya data
     * @param Y titik vertikal mulai diambilnya data
     * @param src sumber citra
     * @return matriks fungsi data RGB
     */
    private static DoubleMatrix getRGBFunc(int X, int Y, BufferedImage src){
        DoubleMatrix func = new DoubleMatrix(16, 1);
        int[] rgbData = src.getRGB(0, 0, src.getWidth(), src.getHeight(), null, 0, src.getWidth());
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                func.setElement(4*i+j, 0, (double)rgbData[src.getWidth()*(clamp(Y + i - 2, 0, src.getHeight()-1)) + (clamp(X + j -2, 0, src.getHeight()-1))]);
            }
        }
        return func;
    }
    
    /**
     * Memastikan nilai rgb tidak keluar batas min dan max
     * @param rgb data RGB int
     * @param min batas minimal
     * @param max batas maksimal
     * @return data RGB int yang sesuai batas
     */
    private static int clamp(int rgb, int min, int max){
        if(rgb < min){
            rgb = min;
        } else if(rgb > max){
            rgb = max;
        }
        return rgb;
    }
}
