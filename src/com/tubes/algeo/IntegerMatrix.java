package com.tubes.algeo;

import java.util.List;

public class IntegerMatrix extends Matrix<Integer> {
    public IntegerMatrix(List<List<Integer>> x) {
        super(x);
    }

    public IntegerMatrix(int row, int col, List<List<Integer>> copyList) {
        super(row, col, copyList);
    }
}
