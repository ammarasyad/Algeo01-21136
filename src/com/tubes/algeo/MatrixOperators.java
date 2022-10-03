package com.tubes.algeo;

import java.text.DecimalFormat;
import java.util.*;
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
            result.setRowElements(i, rowApply(m1.getRowElements(i), m2.getRowElements(i), Double::sum));
        }
        return result;
    }

    public DoubleMatrix subtractMatrix(DoubleMatrix m1, DoubleMatrix m2) {
        DoubleMatrix result = new DoubleMatrix(m1.getRow(), m1.getCol());
        for (int i = 0; i < m1.getRow(); i++) {
            result.setRowElements(i, rowApply(m1.getRowElements(i), m2.getRowElements(i), (x, y) -> x - y));
        }
        return result;
    }

    public DoubleMatrix multiplyMatrixByConst(DoubleMatrix m1, double multiplier) {
        DoubleMatrix result = new DoubleMatrix(m1.getRow(), m1.getCol());
        for (int i = 0; i < m1.getRow(); i++) {
            result.setRowElements(i, rowApply(m1.getRowElements(i), multiplier, (x, y) -> x * y));
        }
        return result;
    }

    public DoubleMatrix multiplyMatrixByMatrix(DoubleMatrix m1, DoubleMatrix m2) {
        if (m1.getCol() != m2.getRow()) {
            System.out.println("Matrix multiplication is not possible.");
            return null;
        }
        DoubleMatrix result = new DoubleMatrix(m1.getRow(), m2.getCol());
        for (int i = 0; i < m1.getRow(); i++) {
            for (int j = 0; j < m2.getCol(); j++) {
                for (int k = 0; k < m1.getRow(); k++) {
                    result.setElement(i, j, result.getElement(i, j) + (m1.getElement(i, k) * m2.getElement(k, j)));
                }
            }
        }
        return result;
    }

    public DoubleMatrix divideMatrix(DoubleMatrix m1, double divisor) {
        DoubleMatrix result = new DoubleMatrix(m1.getRow(), m1.getCol());
        for (int i = 0; i < m1.getRow(); i++) {
            result.setRowElements(i, rowApply(m1.getRowElements(i), divisor, (x, y) -> x / y));
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
                            break;
                        }
                    }
                    if (pivot != i) Collections.swap(copy.getMatrix(), i, pivot);
                    for (int j = i + 1; j < copy.getRow(); j++) {
                        double x = copy.getElement(j, i) / copy.getElement(i, i);
                        copy.setRowElements(j, rowApply(copy.getRowElements(j), rowApply(copy.getRowElements(i), x, (p, q) -> p * q), (a, b) -> a - b));
                    }
                }
                for (int i = 0; i < copy.getRow(); i++) {
                    result *= copy.getElement(i, i);
                }
                result *= Math.pow(-1, switchCount);
            }
            case COFACTOR_EXPANSION -> {
                if (copy.getRow() >= 10) { // God save your soul.
                    return determinant(m, ROW_REDUCTION);
                }
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
                            inverse.setElement(i, j, adjugate(copy).getElement(i, j) / determinant);
                        }
                    }
                    return inverse;
                }
            }
            case GAUSS_JORDAN -> {
                Matrix<Double> identity = Matrix.getIdentityMatrix(m.getRow());
                for (int i = 0; i < copy.getRow(); i++) {
                    for (int j = 0; j < identity.getCol(); j++) {
                        copy.getMatrix().get(i).add(identity.getElement(i, j));
                    }
                }
                return gaussJordan(copy);
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
        if (!doesInverseExist(copy.getLHS())) return null;
        double[] results = new double[copy.getRow()];
        double determinant = determinant(copy.getLHS());
        double[] rhs = copy.getConstants();

        for (int i = 0; i < copy.getRow(); i++) {
            DoubleMatrix temp = new DoubleMatrix(copy.getRow(), copy.getCol(), copy.getMatrix());
            temp = temp.getLHS();
            for (int j = 0; j < copy.getRow(); j++) {
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
        //System.out.println(determinant(m));
        return determinant(m) != 0;
    }

    /**
     * Apply Gaussian Elimination to create an augmented matrix in a row echelon form. Ax = B, the rightmost column is B.
     *
     * @param matrix Matrix of numerical elements.
     * @return Row echelon matrix. Returns null if the matrix is a square.
     */
    public DoubleMatrix gauss(Matrix<? extends Number> matrix) {
        DecimalFormat df = new DecimalFormat("###.##");
        DoubleMatrix copy = Matrix.convertToDouble(matrix);
        final int rows = copy.getRow();
        final int cols = copy.getCol();
        int pivot = 0;
        int iteration = Math.min(rows, cols - 1);
        for (int i = 0; i < iteration; i++) {
            if (pivot == cols - 1) break;
            while (Arrays.stream(copy.getColElements(pivot)).allMatch(a -> a == 0) && pivot < cols) {
                pivot++;
            }

            if (copy.getElement(i, pivot) == 0) {
                for (int j = i + 1; j < rows; j++) {
                    if (copy.getElement(j, pivot) != 0) {
                        Collections.swap(copy.getMatrix(), i, j);
                        break;
                    }
                }
            }

            if (copy.getElement(i, pivot) == 0) continue;
            copy.setRowElements(i, rowApply(copy.getRowElements(i), copy.getElement(i, pivot), (p, q) -> p / q));

            for (int j = i + 1; j < rows; j++) {
                if ((j == i && cols > rows) || copy.getElement(i, pivot) == 0) continue;
                double x = copy.getElement(j, pivot);
                copy.setRowElements(j, rowApply(copy.getRowElements(j), rowApply(copy.getRowElements(i), x, (p, q) -> p * q), (a, b) -> a - b));
            }
            pivot++;
        }

        for (int i = 0; i < copy.getRow(); i++) {
            for (int j = 0; j < copy.getCol(); j++) {
                copy.setElement(i, j, Double.parseDouble(df.format(copy.getElement(i, j))));
            }
        }

        switch (MatrixType.getMatrixType(copy)) {
            case INFINITE -> {
                String[] result = ParametricSolver.solve(copy);
                for (String e : result) {
                    System.out.println(e);
                }
                return null;
            }
            case NO_SOLUTIONS -> {
                System.out.println("Tidak ada solusi.");
                return null;
            }
        }
        return copy;
    }

    /**
     * Apply Gauss-Jordan Elimination to create a reduced row echelon matrix. Ax = B, the rightmost column is B.
     *
     * @param m Matrix of integer elements.
     * @return Reduced row echelon matrix. Returns null if the matrix has either infinite or no solutions.
     */
    public DoubleMatrix gaussJordan(Matrix<? extends Number> m) {
        DoubleMatrix finalized = Matrix.convertToDouble(m);
        int length = m.getRow();
        for (int i = 0; i < length; i++) {
            if (Math.abs(finalized.getElement(i, i)) < EPSILON) {
                finalized = swapRow(finalized, i);
            }
            finalized.setRowElements(i, rowApply(finalized.getRowElements(i), finalized.getElement(i, i), (p, q) -> p / q));
            for (int j = 0; j < length; j++) {
                if (j == i) continue;
                finalized.setRowElements(j, rowApply(finalized.getRowElements(j), rowApply(finalized.getRowElements(i), finalized.getElement(j, i) / finalized.getElement(i, i), (a, b) -> a * b), (p, q) -> p - q));
            }
        }
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length + 1; j++) {
                if (Double.isNaN(finalized.getElement(i, j)) || Double.isInfinite(finalized.getElement(i, j))) {
                    System.out.println("Tidak ada solusi unik.");
                    return null;
                }
                if (Math.abs(finalized.getElement(i, j)) < EPSILON) {
                    finalized.setElement(i, j, 0D);
                }
            }
        }
        return finalized;
    }

    private ArrayList<Double> rowApply(Double[] row1, Double[] row2, IOperators<Double> operator) {
        ArrayList<Double> result = new ArrayList<>(row1.length);
        for (int i = 0; i < row1.length; i++) {
            result.add(operator.apply(row1[i], row2[i]));
        }
        return result;
    }

    private ArrayList<Double> rowApply(Double[] row, ArrayList<Double> list, IOperators<Double> operator) {
        return rowApply(row, list.stream().mapToDouble(Double::valueOf).boxed().toArray(Double[]::new), operator);
    }

    private ArrayList<Double> rowApply(Double[] row, double constant, IOperators<Double> operator) {
        ArrayList<Double> result = new ArrayList<>(row.length);
        for (Double var : row) {
            result.add(operator.apply(var, constant));
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

    private void swapCol(Matrix<Double> matrix, int col, int pivot) {
        ArrayList<Double> array = new ArrayList<>();
        for (int i = 0; i < matrix.getRow(); i++) {
            array.add(matrix.getElement(i, col));
        }

        for (int i = 0; i < matrix.getRow(); i++) {
            matrix.setElement(i, col, matrix.getElement(i, pivot));
            matrix.setElement(i, pivot, array.get(i));
        }
    }
}
