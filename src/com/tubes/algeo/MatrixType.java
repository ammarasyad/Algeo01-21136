package com.tubes.algeo;

public enum MatrixType {
    UNIQUE, // unique solutions
    INFINITE, // infinitely many solutions
    NONEXISTENT; // no solutions

    private static final double EPSILON = 1e-9;
    MatrixType() { }

    static MatrixType getMatrixType(Matrix<Double> m) {
        double sum = 0.0D;
        for (int i = 0; i < m.getCol() - 1; i++) {
            sum += m.getElement(m.getRow() - 1, i);
        }
        double rhs = m.getElement(m.getRow() - 1, m.getCol() - 1);

        for (int i = 0; i < m.getRow(); i++) {
            if (Math.abs(m.getElement(i, i)) < EPSILON) {
                if (sum == 0.0D || Math.abs(sum) < EPSILON
                        && (rhs == 0.0D || Math.abs(rhs) < EPSILON)) {
                    return INFINITE;
                }
                return NONEXISTENT;
            }
        }
        return UNIQUE;
    }
}
