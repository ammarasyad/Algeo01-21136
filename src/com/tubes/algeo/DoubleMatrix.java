package com.tubes.algeo;

import java.util.List;

public class DoubleMatrix extends Matrix<Double> implements Operators<Double> {
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
    public Double add(Double value1, Double value2) {
        return value1 + value2;
    }

    @Override
    public Double subtract(Double value1, Double value2) {
        return value1 - value2;
    }

    @Override
    public Double multiply(Double value1, Double value2) {
        return value1 * value2;
    }

    @Override
    public Double divide(Double value1, Double value2) {
        return value1 / value2;
    }
}
