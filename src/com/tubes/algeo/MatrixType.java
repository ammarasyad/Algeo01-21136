package com.tubes.algeo;

import java.math.BigDecimal;

public enum MatrixType {
    UNIQUE, // unique solutions
    INFINITE, // infinitely many solutions
    NONEXISTENT; // no solutions

    private static final double EPSILON = 1e-9;
    MatrixType() { }

    static MatrixType getMatrixType(BigDecimal[][] m) {
        BigDecimal sum = new BigDecimal("0");
        for (int i = 0; i < m[0].length - 1; i++) {
            sum = sum.add(m[m.length - 1][i]);
        }
        BigDecimal rhs = m[m.length - 1][m.length];

        for (int i = 0; i < m.length; i++) {
            if (Math.abs(m[i][i].doubleValue()) < EPSILON) {
                if (sum.equals(BigDecimal.ZERO) || sum.compareTo(new BigDecimal(EPSILON)) < 0
                        && (rhs.equals(BigDecimal.ZERO) || rhs.compareTo(new BigDecimal(EPSILON)) < 0)) {
                    return INFINITE;
                }
                return NONEXISTENT;
            }
        }
        return UNIQUE;
    }
}
