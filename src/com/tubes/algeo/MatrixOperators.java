package com.tubes.algeo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

final class MatrixOperators {
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

    /**
     * Separates the augmented matrix (A|B) to a matrix of coefficients (A) and an array of constants (B).
     *
     * @param coeff Matrix of coefficients.
     * @param rhs   Matrix of right hand side constants.
     */
    public Matrix<Double> prepareMatrix(Matrix<Double> coeff, Matrix<Double> rhs) {
        List<List<Double>> listCoeff = coeff.getMatrix();
        List<List<Double>> listRHS = rhs.getMatrix();
        return new Matrix<>(coeff.getRow(), rhs.getCol(), Stream.concat(listCoeff.stream(), listRHS.stream()).toList());
    }

    /**
     * Gets the cofactor of a square matrix.
     *
     * @param m The original matrix.
     * @return Cofactor of the matrix.
     */
    public Matrix<Double> cofactor(Matrix<Double> m) {
        Matrix<Double> cofactor = new Matrix<>(m.getRow());
        List<List<Double>> list = cofactor.getMatrix();

        for (int i = 0; i < m.getRow(); i++) {
            for (int j = 0; j < m.getRow(); j++) {
                list.get(i).set(j, (int) Math.pow(-1, i + j) * determinant(removeRowCols(m, i, j)));
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
    public double determinant(Matrix<Double> m) {
        if (m.getRow() != m.getCol()) {
            throw new ArithmeticException("Matrix is non-square.");
        }

        if (m.getRow() == 1) {
            return m.getElement(0, 0);
        } else if (m.getRow() == 2) {
            return m.getElement(0, 0) * m.getElement(1, 1) - m.getElement(0, 1) * m.getElement(1, 0);
        }

        double result = 0.0;
        for (int i = 0; i < m.getRow(); i++) {
            result += Math.pow(-1, i) * m.getElement(0, i) * determinant(removeRowCols(m, 0, i));
        }
        return result;
    }

    /**
     * Returns the adjugate matrix. Cofactor of the matrix will be calculated automatically, no need to pass the cofactor matrix to the params.
     *
     * @param m The original matrix.
     * @return Adjugate (Transpose of the cofactor) of a matrix.
     */
    public Matrix<Double> adjugate(Matrix<Double> m) {
        return transpose(cofactor(m));
    }

    /**
     * Transposes (flips a matrix over its diagonal) the matrix.
     *
     * @param m The original matrix.
     * @return Transposed matrix.
     */
    public Matrix<Double> transpose(Matrix<Double> m) {
        Matrix<Double> temp = new Matrix<>(m.getRow());
        for (int i = 0; i < m.getRow(); i++) {
            for (int j = 0; j < m.getCol(); j++) {
                temp.setElement(j, i, m.getElement(i, j));
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
    public Matrix<Double> inverse(Matrix<Double> m) {
        if (doesInverseExist(m)) {
            Matrix<Double> inverse = new Matrix<>(m.getRow());
            double determinant = determinant(m);
            DecimalFormat df = new DecimalFormat("###.##");
            for (int i = 0; i < m.getRow(); i++) {
                for (int j = 0; j < m.getCol(); j++) {
                    inverse.setElement(i, j, Double.parseDouble(df.format(adjugate(m).getMatrix().get(i).get(j) / determinant)));
                }
            }
            return inverse;
        }
        return null;
    }

    /**
     * Removes the row and column of a matrix.
     *
     * @param m   The original matrix.
     * @param row The row to remove.
     * @param col The column to remove.
     * @return N-1 x N-1 matrix.
     */
    private Matrix<Double> removeRowCols(Matrix<Double> m, int row, int col) {
        Matrix<Double> temp = new Matrix<>(m.getRow() - 1);
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
    public double[] cramer(Matrix<Double> m) {
        if (!doesInverseExist(m)) return null;
        double[] results = new double[m.getRow()];
        double determinant = determinant(m.getLHS());
        double[] rhs = m.getRHS();

        for (int i = 0; i < m.getRow(); i++) {
            Matrix<Double> temp = new Matrix<>(m.getRow(), m.getCol(), m.getMatrix());
            temp = temp.getLHS();
            for (int j = 0; j < m.getCol(); j++) {
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
    private boolean doesInverseExist(Matrix<Double> m) {
        return determinant(m) != 0;
    }

    /**
     * Apply Gaussian Elimination to create an augmented matrix in a row echelon form. Ax = B, the rightmost column is B.
     *
     * @param m Matrix of integer elements.
     * @return Row echelon matrix. null if determinant is 0. Returns null if the matrix has either infinite or no solutions.
     */
    public Matrix<Double> gauss(Matrix<Double> m) {
        return gauss(m.getLHS(), m.getRHS());
    }

    private Matrix<Double> gauss(Matrix<Double> row, double[] constants) {
        int length = row.getRow();
        for (int i = 0; i < length; i++) {
            int max = i;
            for (int j = i + 1; j < length; j++) {
                if (Math.abs(row.getElement(j, i)) > Math.abs(row.getElement(max, i))) {
                    max = j;
                }
            }
            Collections.swap(row.getMatrix(), i, max);
            double t = constants[i];
            constants[i] = constants[max];
            constants[max] = t;

            for (int j = i + 1; j < length; j++) {
                if (Math.abs(row.getElement(i, i)) < EPSILON) continue;
                double x = row.getElement(j, i) / row.getElement(i, i);
                constants[j] -= x * constants[i];
//                row[j] = rowSubtract(row[j], rowMultiply(row[i], x));
                row.setRowElements(j, rowSubtract(row.getRowElements(j), rowMultiply(row.getRowElements(i), x)));
            }
        }
        double[] x = new double[length];
        for (int i = length - 1; i >= 0; i--) {
            double sum = 0.0;
            for (int j = i + 1; j < length; j++) {
                sum += row.getElement(i, j) * x[j];
            }
            x[i] = (constants[i] - sum) / row.getElement(i, i);
        }

        for (int i = 0; i < length; i++) {
            row.getMatrix().get(i).add(constants[i]);
        }

//        for (int i = 0; i < length; i++) {
//            if (finalized[i][i] == 1) continue;
//
//            if (!(Math.abs(finalized[i][i]) < EPSILON)) {
//                if ((finalized[i][i] > 1 || finalized[i][i] < 0)) {
//                    finalized[i] = rowDivide(finalized[i], finalized[i][i]);
//                } else if ((finalized[i][i] < 1 && finalized[i][i] > 0)) {
//                    finalized[i] = rowMultiply(finalized[i], 1 / finalized[i][i]);
//                }
//            }
//        }
        for (int i = 0; i < length; i++) {
            if (row.getElement(i, i) == 1) continue;
            if (!(Math.abs(row.getElement(i, i)) < EPSILON)) {
                if (row.getElement(i, i) > 1 || row.getElement(i, i) < 0) {
                    row.setRowElements(i, rowDivide(row.getRowElements(i), row.getElement(i, i)));
                } else if (row.getElement(i, i) > 0 && row.getElement(i, i) < 1) {
                    row.setRowElements(i, rowMultiply(row.getRowElements(i), 1 / row.getElement(i, i)));
                }
            }

            if (i == length - 1) {
                for (int j = 0; j < length + 1; j++) {
                    if ((row.getElement(i, j)) < EPSILON) {
                        row.setElement(i, j, 0D);
                    }
                }
            }
        }
        return row;
    }

    /**
     * Apply Gauss-Jordan Elimination to create a reduced row echelon matrix. Ax = B, the rightmost column is B.
     *
     * @param m Matrix of integer elements.
     * @return Reduced row echelon matrix. Returns null if the matrix has either infinite or no solutions.
     */
    public Matrix<Double> gaussJordan(Matrix<Double> m) {
        Matrix<Double> finalized = new Matrix<>(m.getRow(), m.getCol(), m.getMatrix());
        int length = m.getRow();
        for (int i = 0; i < length; i++) {
            if (Math.abs(finalized.getElement(i, i)) < EPSILON) {
                finalized = swapRow(finalized, i);
            }
            finalized.setRowElements(i, rowDivide(m.getRowElements(i), m.getElement(i, i)));
            for (int j = 0; j < length; j++) {
                if (j == i) continue;
                finalized.setRowElements(j, rowSubtract(finalized.getRowElements(j), rowMultiply(finalized.getRowElements(i), finalized.getElement(j, i) / finalized.getElement(i, i))));
            }
        }
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length + 1; j++) {
                if (Double.isNaN(finalized.getElement(i, j)) || Double.isInfinite(finalized.getElement(i, j))) {
                    System.out.println("Matrix does not have a unique solution.");
                    return null;
                }
                if (Math.abs(finalized.getElement(i, j)) < EPSILON) {
                    finalized.setElement(i, j, 0D);
                }
            }
        }
        return finalized;
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
        ArrayList<Double> sub = new ArrayList<>(row.length);
        Iterator<Double> iterator = subtract.iterator();
        int i = 0;
        while (iterator.hasNext() && i < row.length) {
            sub.add(i, row[i] - iterator.next());
            i++;
        }
        return sub;
    }

    private Matrix<Double> swapRow(Matrix<Double> m, int row) {
        count++;
        if (count >= m.getRow() - row) {
            count = 0;
            return m;
        }

        Double[] temp = m.getRowElements(row);
        for (int i = row; i < m.getRow() - 1; i++) {
//            m[i] = m[i + 1];
            m.setRowElements(i, Arrays.stream(m.getRowElements(i + 1)).collect(Collectors.toCollection(ArrayList::new)));
        }
//        m[m.length - 1] = temp;
        m.setRowElements(m.getRow() - 1, Arrays.stream(temp).collect(Collectors.toCollection(ArrayList::new)));
        if (m.getElement(row, row) == 0) {
            m = swapRow(m, row);
        }
        count = 0;
        return m;
    }
}
