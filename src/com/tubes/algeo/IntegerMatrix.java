package com.tubes.algeo;

import java.util.List;

public class IntegerMatrix extends Matrix<Integer> implements Operators<Integer> {
    public IntegerMatrix(List<List<Integer>> x) {
        super(x);
    }

    public IntegerMatrix(int row, int col, List<List<Integer>> copyList) {
        super(row, col, copyList);
    }

    @Override
    public Integer add(Integer value1, Integer value2) {
        return value1 + value2;
    }

    @Override
    public Integer subtract(Integer value1, Integer value2) {
        return value1 - value2;
    }

    @Override
    public Integer multiply(Integer value1, Integer value2) {
        return value1 * value2;
    }

    @Override
    public Integer divide(Integer value1, Integer value2) {
        return value1 / value2;
    }
}
