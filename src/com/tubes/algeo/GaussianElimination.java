package com.tubes.algeo;

import java.math.BigDecimal;
import java.math.RoundingMode;

final class GaussianElimination {
    public static final double EPSILON = 1e-10;
    private static int count = 0;
    private static final MatrixOps matrixOps = MatrixOps.getInstance();

    private static GaussianElimination INSTANCE = null;

    private GaussianElimination() { }

    /**
     * Initializes the class.
     * @return Instance of GaussianElimination.
     */
    public static GaussianElimination getInstance() {
        if (INSTANCE == null) INSTANCE = new GaussianElimination();
        return INSTANCE;
    }

    /**
     * Apply Gaussian Elimination to create an augmented matrix in a row echelon form. Ax = B, the rightmost column is B.
     *
     * @param m Matrix of integer elements.
     * @return Row echelon matrix. null if determinant is 0. Returns null if the matrix has either infinite or no solutions.
     */
    public BigDecimal[][] gauss(int[][] m) {
        double[][] row = new double[m.length][m.length];
        double[] constants = new double[m.length];
        matrixOps.prepareMatrix(matrixOps.convertToDouble(m), row, constants);
        return gauss(row, constants);
    }

    /**
     * Apply Gauss-Jordan Elimination to create a reduced row echelon matrix. Ax = B, the rightmost column is B.
     *
     * @param m Matrix of integer elements.
     * @return Reduced row echelon matrix. Returns null if the matrix has either infinite or no solutions.
     */
    public BigDecimal[][] gaussJordan(int[][] m) {
        return gaussJordan(matrixOps.convertToDouble(m));
    }

    private BigDecimal[][] gauss(double[][] row, double[] constants) {
        int length = row.length;

        if (matrixOps.determinant(row) == 0) {
            return null;
        }
        for (int i = 0; i < length; i++) {
            int max = i;
            for (int j = i + 1; j < length; j++) {
                if (Math.abs(row[j][i]) > Math.abs(row[max][i])) {
                    max = j;
                }
            }
            double[] temp = row[i];
            row[i] = row[max];
            row[max] = temp;

            double t = constants[i];
            constants[i] = constants[max];
            constants[max] = t;

            for (int j = i + 1; j < length; j++) {
                double x = row[j][i] / row[i][i];
                constants[j] -= x * constants[i];
                row[j] = rowSubtract(row[j], rowMultiply(row[i], x));
            }
        }
        double[] x = new double[length];
        for (int i = length - 1; i >= 0; i--) {
            double sum = 0.0;
            for (int j = i + 1; j < length; j++) {
                sum += row[i][j] * x[j];
            }
            x[i] = (constants[i] - sum) / row[i][i];
        }

        double[][] finalized = matrixOps.combineMatrix(row, constants);

        for (int i = 0; i < length; i++) {
            if (finalized[i][i] == 1) continue;

            if (finalized[i][i] > 1 || finalized[i][i] < 0) {
                finalized[i] = rowDivide(finalized[i], finalized[i][i]);
            } else if (row[i][i] < 1 && row[i][i] > 0) {
                finalized[i] = rowMultiply(finalized[i], 1 / finalized[i][i]);
            }
        }
        return convertToBD(finalized);
    }

    private BigDecimal[][] gaussJordan(double[][] m) {
        double[][] finalized = m;
        int length = m.length;
        for (int i = 0; i < length; i++) {
            if (Math.abs(finalized[i][i]) < EPSILON) {
                finalized = swapRow(finalized, i);
            }
            finalized[i] = rowDivide(m[i], m[i][i]);
            for (int j = 0; j < length; j++) {
                if (j == i) continue;
                finalized[j] = rowSubtract(finalized[j], rowMultiply(finalized[i], finalized[j][i] / finalized[i][i]));
            }
        }
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if (Double.isNaN(finalized[i][j]) || Double.isInfinite(finalized[i][j])) {
                    return null;
                }
            }
        }
        return convertToBD(finalized);
    }

    private BigDecimal[][] convertToBD(double[][] finalized) {
        BigDecimal[][] result = new BigDecimal[finalized.length][finalized.length + 1];
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j <= result.length; j++) {
                result[i][j] = BigDecimal.valueOf(finalized[i][j]);
                result[i][j] = result[i][j].setScale(3, RoundingMode.HALF_UP);
            }
        }
        return result;
    }

    private double[] rowMultiply(double[] row, double multiplier) {
        double[] multiplied = new double[row.length];
        for (int i = 0; i < row.length; i++) {
            multiplied[i] = row[i] * multiplier;
        }
        return multiplied;
    }

    private double[] rowDivide(double[] row, double multiplier) {
        double[] div = new double[row.length];
        for (int i = 0; i < row.length; i++) {
            div[i] = row[i] / multiplier;
        }
        return div;
    }

    private double[] rowSubtract(double[] row1, double[] row2) {
        double[] subtract = new double[row1.length];
        for (int i = 0; i < row1.length; i++) {
            subtract[i] = row1[i] - row2[i];
        }
        return subtract;
    }

    private double[][] swapRow(double[][] m, int row) {
        count++;
        if (count >= m.length - row) {
            count = 0;
            return m;
        }

        double[] temp = m[row];
        for (int i = row; i < m.length - 1; i++) {
            m[i] = m[i + 1];
        }
        m[m.length - 1] = temp;
        if (m[row][row] == 0) {
            m = swapRow(m, row);
        }
        count = 0;
        return m;
    }
}
