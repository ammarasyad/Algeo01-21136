package com.tubes.algeo;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Arrays;

final class MatrixOps {
    private static MatrixOps INSTANCE = null;
    public static final GaussianElimination gaussian = GaussianElimination.getInstance();

    private MatrixOps() { }

    /**
     * Initializes the class.
     * @return Instance of MatrixOps.
     */
    public static MatrixOps getInstance() {
        if (INSTANCE == null) INSTANCE = new MatrixOps();
        return INSTANCE;
    }

    /**
     * Separates the augmented matrix (A|B) to a matrix of coefficients (A) and an array of constants (B).
     *
     * @param m            The augmented matrix.
     * @param coefficients Matrix of coefficients.
     * @param constants    Array of constants.
     */
    public void prepareMatrix(double[][] m, double[][] coefficients, double[] constants) {
        int length = m[0].length - 1;
        for (int i = 0; i < m.length; i++) {
            System.arraycopy(m[i], 0, coefficients[i], 0, length);
            constants[i] = m[i][length];
        }
    }

    public double[][] combineMatrix(double[][] coefficients, double[] constants) {
        int length = coefficients.length;
        int row = coefficients[0].length;
        double[][] result = new double[length][row + 1];
        for (int i = 0; i < length; i++) {
            System.arraycopy(coefficients[i], 0, result[i], 0, row);
            result[i][row] = constants[i];
        }
        return result;
    }

    /**
     * Apply Cramer's rule to retrieve a solution for solving a system of linear equations.
     *
     * @param m An (N + 1) x N matrix, the rightmost being the constants.
     * @return Array of solutions.
     */
    public double[] cramer(int[][] m) {
        return cramer(convertToDouble(m));
    }

    /**
     * Creates a copy of the passed matrix.
     *
     * @param m The original matrix.
     * @return Copy of the original matrix.
     */
    public double[][] deepCopy(double[][] m) {
        double[][] copy = new double[m.length][m.length];
        for (int i = 0; i < m.length; i++) {
            copy[i] = Arrays.stream(m[i]).toArray();
        }
        return copy;
    }

    /**
     * Gets the cofactor of a square matrix.
     *
     * @param m The original matrix.
     * @return Cofactor of the matrix.
     */
    public int[][] cofactor(int[][] m) {
        int[][] temp = new int[m.length][m.length];
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m.length; j++) {
                temp[i][j] = (int) (Math.pow(-1, i + j)) * determinant(removeRowCols(m, i, j));
            }
        }
        return temp;
    }

    /**
     * Returns the determinant of a square matrix by using cofactor expansion.
     *
     * @param m The matrix.
     * @return Determinant of the matrix.
     * @throws ArithmeticException if the matrix is non-square.
     */
    public int determinant(double[][] m) {
        int[][] transferred = new int[m.length][m.length];
        for (int i = 0; i < m.length; i++) {
            transferred[i] = Arrays.stream(m[i]).mapToInt(j -> (int) j).toArray();
        }

        return determinant(transferred);
    }

    /**
     * Returns the determinant of a square matrix by using cofactor expansion.
     *
     * @param m The original matrix.
     * @return Determinant of said matrix.
     * @throws ArithmeticException if the matrix is non-square.
     */
    public int determinant(int[][] m) {
        if (m.length == 1) {
            return m[0][0];
        } else if (m.length == 2) {
            return m[0][0] * m[1][1] - m[0][1] * m[1][0];
        }

        if (m[0].length != m.length) {
            throw new ArithmeticException("Matrix is non-square.");
        }

        int result = 0;
        for (int i = 0; i < m.length; i++) {
            result += Math.pow(-1, i) * m[0][i] * determinant(removeRowCols(m, 0, i));
        }
        return result;
    }

    /**
     * Returns the adjugate matrix. Cofactor of the matrix will be calculated automatically, no need to pass the cofactor matrix to the params.
     *
     * @param m The original matrix.
     * @return Adjugate (Transpose of the cofactor) of a matrix.
     */
    public int[][] adjugate(int[][] m) {
        return transpose(cofactor(m));
    }

    /**
     * Transposes (flips a matrix over its diagonal) the matrix.
     *
     * @param m The original matrix.
     * @return Transposed matrix.
     */
    public int[][] transpose(int[][] m) {
        int[][] temp = new int[m.length][m.length];
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m.length; j++) {
                temp[j][i] = m[i][j];
            }
        }
        return temp;
    }

    /**
     * Returns the inverse of a matrix.
     *
     * @param m The original matrix.
     * @return Inverse of the matrix. NULL if inverse does not exist.
     */
    public double[][] inverse(int[][] m) {
        if (doesInverseExist(m)) {
            double[][] inverse = new double[m.length][m.length];
            int determinant = determinant(m);
            DecimalFormat df = new DecimalFormat("###.##");
            for (int i = 0; i < m.length; i++) {
                for (int j = 0; j < m.length; j++) {
                    inverse[i][j] = Double.parseDouble(df.format((double) adjugate(m)[i][j] / determinant));
                }
            }
            return inverse;
        }
        return null;
    }

    /**
     * Converts the matrix of integer elements to a matrix of double elements.
     * @param m Matrix of integer elements.
     * @return Copy of m with double elements.
     */
    public double[][] convertToDouble(int[][] m) {
        double[][] conv = new double[m.length][];
        for (int i = 0; i < m.length; i++) {
            conv[i] = Arrays.stream(m[i]).asDoubleStream().toArray();
        }
        return conv;
    }

    /**
     * Removes the row and column of a matrix.
     *
     * @param m   The original matrix.
     * @param row The row to remove.
     * @param col The column to remove.
     * @return N-1 x N-1 matrix.
     */
    private int[][] removeRowCols(int[][] m, int row, int col) {
        int[][] temp = new int[m.length - 1][m.length - 1];
        int k = 0, l = 0;
        for (int i = 0; i < m.length; i++) {
            if (i == row) continue;
            for (int j = 0; j < m.length; j++) {
                if (j == col) continue;
                temp[l][k] = m[i][j];

                k = (k + 1) % (m.length - 1);
                if (k == 0) l++;
            }
        }
        return temp;
    }

    /**
     * Apply Cramer's rule to retrieve a solution for solving a system of linear equations.
     *
     * @param m An (N + 1) x N matrix, the rightmost being the constants.
     * @return Array of solutions.
     */
    private double[] cramer(double[][] m) {
        double[] solutions = new double[m.length], constants = new double[m.length];
        double[][] row = new double[m.length][m.length];

        int determinant = determinant(row);
        if (determinant == 0) return null;

        prepareMatrix(m, row, constants);

        for (int i = 0; i < m.length; i++) {
            double[][] copy = deepCopy(row);
            for (int j = 0; j < m.length; j++) {
                copy[j][i] = constants[j];
            }
            solutions[i] = (double) determinant(copy) / determinant;
        }
        return solutions;
    }

    /**
     * If the determinant is non-zero, the inverse of a matrix exists.
     *
     * @param m The original matrix
     * @return TRUE - determinant is non-zero. FALSE - determinant is zero.
     */
    private boolean doesInverseExist(int[][] m) {
        return determinant(m) != 0;
    }
}
