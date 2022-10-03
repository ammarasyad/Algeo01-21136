package com.tubes.algeo;

public enum MatrixType {
    UNIQUE, // unique solutions
    INFINITE, // infinitely many solutions
    NO_SOLUTIONS; // no solutions

    private static final double EPSILON = 1e-15;
    MatrixType() { }

    static MatrixType getMatrixType(Matrix<Double> m) {
        m.getMatrix().removeIf(list -> list.stream().allMatch(a -> a == 0));
        if (m.getRow() < m.getCol() - 1) {
            return INFINITE;
        } else if (m.getRow() > m.getCol() - 1) {
            return NO_SOLUTIONS;
        }
        return UNIQUE;
    }
}
