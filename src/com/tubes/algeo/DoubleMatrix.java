package com.tubes.algeo;

import java.util.List;

public class DoubleMatrix extends Matrix<Double> {
    public DoubleMatrix(List<List<Double>> x) {
        super(x);
    }

    public DoubleMatrix(int row, int col) {
        super(row, col);
    }

    public DoubleMatrix(int row) {
        super(row);
    }

    public DoubleMatrix(int row, int col, List<List<Double>> copyList) {
        super(row, col, copyList);
    }

    @Override
    public DoubleMatrix getLHS() {
        DoubleMatrix temp = new DoubleMatrix(getRow(), getCol() - 1);
        for (int i = 0; i < getRow(); i++) {
            for (int j = 0; j < getCol() - 1; j++) {
                temp.setElement(i, j, getElement(i, j));
            }
        }
        return temp;
    }
}
