package com.tubes.algeo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class MatrixOperators {

    public static final int COFACTOR_EXPANSION = 1;
    public static final int ROW_REDUCTION = 0;

    public static final int CLASSIC = 69;

    public static final int GAUSS_JORDAN = 420;
    private static MatrixOperators INSTANCE = null;
    private static final double EPSILON = 1e-9;
    private static int count = 0;

    private MatrixOperators() {
    }

    /**
     * Initializes the class.
     *
     * @return Instance of MatrixOps.
     */
    public static MatrixOperators getInstance() {
        if (INSTANCE == null) INSTANCE = new MatrixOperators();
        return INSTANCE;
    }

    public DoubleMatrix addMatrix(DoubleMatrix m1, DoubleMatrix m2) {
        DoubleMatrix result = new DoubleMatrix(m1.getRow(), m1.getCol());
        for (int i = 0; i < m1.getRow(); i++) {
            result.setRowElements(i, rowAdd(m1.getRowElements(i), m2.getRowElements(i)));
        }
        return result;
    }

    public DoubleMatrix subtractMatrix(DoubleMatrix m1, DoubleMatrix m2) {
        DoubleMatrix result = new DoubleMatrix(m1.getRow(), m1.getCol());
        for (int i = 0; i < m1.getRow(); i++) {
            result.setRowElements(i, rowSubtract(m1.getRowElements(i), m2.getRowElements(i)));
        }
        return result;
    }

    public DoubleMatrix multiplyMatrixByConst(DoubleMatrix m1, double multiplier) {
        DoubleMatrix result = new DoubleMatrix(m1.getRow(), m1.getCol());
        for (int i = 0; i < m1.getRow(); i++) {
            result.setRowElements(i, rowMultiply(m1.getRowElements(i), multiplier));
        }
        return result;
    }

    public DoubleMatrix multiplyMatrixByMatrix(DoubleMatrix m1, DoubleMatrix m2) {
        DoubleMatrix result = new DoubleMatrix(m1.getRow(), m1.getCol());
        for (int i = 0; i < m1.getRow(); i++) {
            for (int j = 0; j < m1.getCol(); j++) {
                for (int k = 0; k < m1.getCol(); k++) {
                    result.setElement(i, j, result.getElement(i, j) + (m1.getElement(i, j) * m2.getElement(k, j)));
                }
            }
        }
        return result;
    }

    public DoubleMatrix divideMatrix(DoubleMatrix m1, double divisor) {
        DoubleMatrix result = new DoubleMatrix(m1.getRow(), m1.getCol());
        for (int i = 0; i < m1.getRow(); i++) {
            result.setRowElements(i, rowDivide(m1.getRowElements(i), divisor));
        }
        return result;
    }

    /**
     * Gets the cofactor of a square matrix.
     *
     * @param m The original matrix.
     * @return Cofactor of the matrix.
     */
    public DoubleMatrix cofactor(DoubleMatrix m) {
        DoubleMatrix copy = Matrix.convertToDouble(m);
        DoubleMatrix cofactor = new DoubleMatrix(copy.getRow());
        List<List<Double>> list = cofactor.getMatrix();

        for (int i = 0; i < copy.getRow(); i++) {
            for (int j = 0; j < copy.getRow(); j++) {
                list.get(i).set(j, (int) Math.pow(-1, i + j) * determinant(removeRowCols(copy, i, j)));
            }
        }

        return cofactor;
    }

    /**
     * Returns the determinant of a square matrix by using cofactor expansion.
     *
     * @param m The original matrix.
     * @return Determinant of said matrix.
     * @throws ArithmeticException if the matrix is non-square.
     */
    public double determinant(Matrix<? extends Number> m, int mode) {
        double result = 0.0d;
        DoubleMatrix copy = Matrix.convertToDouble(m);
        switch (mode) {
            case ROW_REDUCTION -> {
                result = 1.0d;
                int switchCount = 0;
                for (int i = 0; i < copy.getRow(); i++) {
                    int pivot = i;
                    for (int j = i + 1; j < copy.getRow(); j++) {
                        if (Math.abs(copy.getElement(j, i)) > Math.abs(copy.getElement(pivot, i))) {
                            pivot = j;
                            switchCount++;
                        }
                    }
                    Collections.swap(copy.getMatrix(), i, pivot);
                    for (int j = i + 1; j < copy.getRow(); j++) {
                        double x = copy.getElement(j, i) / copy.getElement(i, i);
                        copy.setRowElements(j, rowSubtract(copy.getRowElements(j), rowMultiply(copy.getRowElements(i), x)));
                    }
                }
                for (int i = 0; i < copy.getRow(); i++) {
                    result *= Math.pow(-1, switchCount) * copy.getElement(i, i);
                }
            }
            case COFACTOR_EXPANSION -> {
                if (copy.getRow() != copy.getCol()) {
                    throw new ArithmeticException("Matrix is non-square.");
                }
                if (copy.getRow() == 1) {
                    return copy.getElement(0, 0);
                } else if (copy.getRow() == 2) {
                    return copy.getElement(0, 0) * copy.getElement(1, 1) - copy.getElement(0, 1) * copy.getElement(1, 0);
                }
                for (int i = 0; i < copy.getRow(); i++) {
                    result += Math.pow(-1, i) * copy.getElement(0, i) * determinant(removeRowCols(copy, 0, i));
                }
            }
        }
        return result;
    }

    public double determinant(Matrix<? extends Number> m) {
        return determinant(m, COFACTOR_EXPANSION);
    }

    /**
     * Returns the adjugate matrix. Cofactor of the matrix will be calculated automatically, no need to pass the cofactor matrix to the params.
     *
     * @param m The original matrix.
     * @return Adjugate (Transpose of the cofactor) of a matrix.
     */
    public DoubleMatrix adjugate(Matrix<? extends Number> m) {
        return transpose(cofactor(Matrix.convertToDouble(m)));
    }

    /**
     * Transposes (flips a matrix over its diagonal) the matrix.
     *
     * @param m The original matrix.
     * @return Transposed matrix.
     */
    public DoubleMatrix transpose(Matrix<? extends Number> m) {
        DoubleMatrix copy = Matrix.convertToDouble(m);
        DoubleMatrix temp = new DoubleMatrix(m.getRow());
        for (int i = 0; i < m.getRow(); i++) {
            for (int j = 0; j < m.getCol(); j++) {
                temp.setElement(j, i, copy.getElement(i, j));
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
    public DoubleMatrix inverse(Matrix<? extends Number> m, int method) {
        DoubleMatrix copy = Matrix.convertToDouble(m);
        switch (method) {
            case CLASSIC -> {
                if (doesInverseExist(copy)) {
                    DoubleMatrix inverse = new DoubleMatrix(copy.getRow());
                    double determinant = determinant(copy);
                    for (int i = 0; i < copy.getRow(); i++) {
                        for (int j = 0; j < copy.getCol(); j++) {
                            inverse.setElement(i, j, BigDecimal.valueOf(adjugate(copy).getElement(i, j) / determinant).doubleValue());
                        }
                    }
                    return inverse;
                }
            }
            case GAUSS_JORDAN -> {
                Matrix<Double> identity = Matrix.getIdentityMatrix(m.getRow());

                return gaussJordan(m);
            }
        }
        return null;
    }

    public DoubleMatrix inverse(Matrix<? extends Number> m) {
        return inverse(m, CLASSIC);
    }

    /**
     * Removes the row and column of a matrix.
     *
     * @param m   The original matrix.
     * @param row The row to remove.
     * @param col The column to remove.
     * @return N-1 x N-1 matrix.
     */
    private DoubleMatrix removeRowCols(DoubleMatrix m, int row, int col) {
        DoubleMatrix temp = new DoubleMatrix(m.getRow() - 1);
        int k = 0, l = 0;
        for (int i = 0; i < m.getRow(); i++) {
            if (i == row) continue;
            for (int j = 0; j < m.getRow(); j++) {
                if (j == col) continue;
                temp.setElement(l, k, m.getElement(i, j));

                k = (k + 1) % (m.getRow() - 1);
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
    public double[] cramer(Matrix<? extends Number> m) {
        DoubleMatrix copy = Matrix.convertToDouble(m);
        if (!doesInverseExist(copy)) return null;
        double[] results = new double[copy.getRow()];
        double determinant = determinant(copy.getLHS());
        double[] rhs = copy.getConstants();

        for (int i = 0; i < copy.getRow(); i++) {
            DoubleMatrix temp = new DoubleMatrix(copy.getRow(), copy.getCol(), copy.getMatrix());
            temp = (DoubleMatrix) temp.getLHS();
            for (int j = 0; j < copy.getCol(); j++) {
                temp.setElement(j, i, rhs[j]);
            }
            results[i] = determinant(temp) / determinant;
        }
        return results;
    }

    /**
     * If the determinant is non-zero, the inverse of a matrix exists.
     *
     * @param m The original matrix
     * @return TRUE - determinant is non-zero. FALSE - determinant is zero.
     */
    private boolean doesInverseExist(DoubleMatrix m) {
        return determinant(m) != 0;
    }

    /**
     * Apply Gaussian Elimination to create an augmented matrix in a row echelon form. Ax = B, the rightmost column is B.
     *
     * @param m Matrix of integer elements.
     * @return Row echelon matrix. Returns the matrix itself if the matrix is a square.
     */
    public DoubleMatrix gauss(Matrix<? extends Number> m) {
        DoubleMatrix copy = Matrix.convertToDouble(m);
        if (m.getRow() == m.getCol()) {
            System.out.println("Matrix is a square. Cannot continue.");
            return null;
        }
        return gauss(copy.getLHS(), copy.getConstants(), (copy.getRow() > copy.getCol()) ? copy.getRow() - copy.getCol() : 0);
    }

    private DoubleMatrix gauss(Matrix<Double> result, double[] constants, int offset) {
        int length = result.getRow();
        for (int i = 0; i < length - offset; i++) {
            int max = i;
            for (int j = i + 1; j < length - offset; j++) {
                if (Math.abs(result.getElement(j, i)) > Math.abs(result.getElement(max, i))) {
                    max = j;
                }
            }
            Collections.swap(result.getMatrix(), i, max);
            double t = constants[i];
            constants[i] = constants[max];
            constants[max] = t;

            for (int j = i + 1; j < length - offset; j++) {
                if (Math.abs(result.getElement(i, i)) < EPSILON) continue;
                double x = result.getElement(j, i) / result.getElement(i, i);
                constants[j] -= x * constants[i];
                result.setRowElements(j, rowSubtract(result.getRowElements(j), rowMultiply(result.getRowElements(i), x)));
            }
        }

        for (int i = 0; i < length; i++) {
            result.getMatrix().get(i).add(constants[i]);
        }

        for (int i = 0; i < length - offset; i++) {
            if (result.getElement(i, i) == 1) continue;
            if (!(Math.abs(result.getElement(i, i)) < EPSILON)) {
                if (result.getElement(i, i) > 1 || result.getElement(i, i) < 0) {
                    result.setRowElements(i, rowDivide(result.getRowElements(i), result.getElement(i, i)));
                } else if (result.getElement(i, i) > 0 && result.getElement(i, i) < 1) {
                    result.setRowElements(i, rowMultiply(result.getRowElements(i), 1 / result.getElement(i, i)));
                }
            }
        }
        for (int j = 0; j < result.getCol() - offset; j++) {
            if (Math.abs(result.getElement(length - 1, j)) < EPSILON) {
                result.setElement(length - 1, j, 0D);
            }
        }

        switch (MatrixType.getMatrixType(result)) {
            case INFINITE -> {
                String[] res = ParametricSolver.getInstance().solve(result);
                for (String re : res) {
                    System.out.println(re);
                }
                return null;
            }
            case NO_SOLUTIONS -> {
                System.out.println("Matrix has no solutions.");
                return null;
            }
        }

        return new DoubleMatrix(result.getMatrix());
    }

    /**
     * Apply Gauss-Jordan Elimination to create a reduced row echelon matrix. Ax = B, the rightmost column is B.
     *
     * @param m Matrix of integer elements.
     * @return Reduced row echelon matrix. Returns null if the matrix has either infinite or no solutions.
     */
    public DoubleMatrix gaussJordan(Matrix<? extends Number> m) {
        DoubleMatrix finalized = gauss(m);
        int length = m.getRow();
        for (int i = 0; i < length; i++) {
            if (Math.abs(finalized.getElement(i, i)) < EPSILON) {
                finalized = swapRow(finalized, i);
            }
            finalized.setRowElements(i, rowDivide(finalized.getRowElements(i), finalized.getElement(i, i)));
            for (int j = 0; j < length; j++) {
                if (j == i) continue;
                finalized.setRowElements(j, rowSubtract(finalized.getRowElements(j), rowMultiply(finalized.getRowElements(i), finalized.getElement(j, i) / finalized.getElement(i, i))));
            }
        }
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length + 1; j++) {
                if (Double.isNaN(finalized.getElement(i, j)) || Double.isInfinite(finalized.getElement(i, j))) {
                    System.out.println("Matrix does not have a unique solution.");
                    return (DoubleMatrix) m;
                }
                if (Math.abs(finalized.getElement(i, j)) < EPSILON) {
                    finalized.setElement(i, j, 0D);
                }
            }
        }
        return finalized;
    }

    private ArrayList<Double> rowAdd(Double[] row, Double[] add) {
        ArrayList<Double> result = new ArrayList<>(row.length);
        for (int i = 0; i < row.length; i++) {
            result.add(row[i] + add[i]);
        }
        return result;
    }

    private ArrayList<Double> rowMultiply(Double[] row, double multiplier) {
        ArrayList<Double> multiplied = new ArrayList<>(row.length);
        for (int i = 0; i < row.length; i++) {
            multiplied.add(i, row[i] * multiplier);
        }
        return multiplied;
    }

    private ArrayList<Double> rowDivide(Double[] row, double divisor) {
        ArrayList<Double> divided = new ArrayList<>(row.length);
        for (int i = 0; i < row.length; i++) {
            divided.add(i, row[i] / divisor);
        }
        return divided;
    }

    private ArrayList<Double> rowSubtract(Double[] row, ArrayList<Double> subtract) {
        return rowSubtract(row, subtract.stream().mapToDouble(Double::valueOf).boxed().toArray(Double[]::new));
    }

    private ArrayList<Double> rowSubtract(Double[] row, Double[] subtract) {
        ArrayList<Double> result = new ArrayList<>(row.length);
        for (int i = 0; i < row.length; i++) {
            result.add(row[i] - subtract[i]);
        }
        return result;
    }

    private DoubleMatrix swapRow(DoubleMatrix m, int row) {
        count++;
        if (count >= m.getRow() - row) {
            count = 0;
            return m;
        }

        Double[] temp = m.getRowElements(row);
        for (int i = row; i < m.getRow() - 1; i++) {
            m.setRowElements(i, Arrays.stream(m.getRowElements(i + 1)).collect(Collectors.toCollection(ArrayList::new)));
        }
        m.setRowElements(m.getRow() - 1, Arrays.stream(temp).collect(Collectors.toCollection(ArrayList::new)));
        if (m.getElement(row, row) == 0) {
            m = swapRow(m, row);
        }
        count = 0;
        return m;
    }
}
