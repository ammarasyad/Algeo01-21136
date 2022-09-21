package com.tubes.algeo;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

public final class MatrixOperators {
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

    public DoubleMatrix multiplyMatrix(DoubleMatrix m1, double multiplier) {
        DoubleMatrix result = new DoubleMatrix(m1.getRow(), m1.getCol());
        for (int i = 0; i < m1.getRow(); i++) {
            result.setRowElements(i, rowMultiply(m1.getRowElements(i), multiplier));
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
//    /**
//     * Separates the augmented matrix (A|B) to a matrix of coefficients (A) and an array of constants (B).
//     *
//     * @param coeff Matrix of coefficients.
//     * @param rhs   Matrix of right hand side constants.
//     */
//    public DoubleMatrix prepareMatrix(DoubleMatrix coeff, DoubleMatrix rhs) {
//        List<List<Double>> listCoeff = coeff.getMatrix();
//        List<List<Double>> listRHS = rhs.getMatrix();
//        return new DoubleMatrix(coeff.getRow(), rhs.getCol(), Stream.concat(listCoeff.stream(), listRHS.stream()).toList());
//    }

    /**
     * Gets the cofactor of a square matrix.
     *
     * @param m The original matrix.
     * @return Cofactor of the matrix.
     */
    public DoubleMatrix cofactor(DoubleMatrix m) {
        DoubleMatrix copy = checkMatrix(m);
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
    public double determinant(Matrix<? extends Number> m) {
        DoubleMatrix copy = checkMatrix(m);
        if (copy.getRow() != copy.getCol()) {
            throw new ArithmeticException("Matrix is non-square.");
        }

        if (copy.getRow() == 1) {
            return copy.getElement(0, 0);
        } else if (copy.getRow() == 2) {
            return copy.getElement(0, 0) * copy.getElement(1, 1) - copy.getElement(0, 1) * copy.getElement(1, 0);
        }

        double result = 0.0;
        for (int i = 0; i < copy.getRow(); i++) {
            result += Math.pow(-1, i) * copy.getElement(0, i) * determinant(removeRowCols(copy, 0, i));
        }
        return result;
    }

    /**
     * Returns the adjugate matrix. Cofactor of the matrix will be calculated automatically, no need to pass the cofactor matrix to the params.
     *
     * @param m The original matrix.
     * @return Adjugate (Transpose of the cofactor) of a matrix.
     */
    public DoubleMatrix adjugate(Matrix<? extends Number> m) {
        return transpose(cofactor(checkMatrix(m)));
    }

    /**
     * Transposes (flips a matrix over its diagonal) the matrix.
     *
     * @param m The original matrix.
     * @return Transposed matrix.
     */
    public DoubleMatrix transpose(Matrix<? extends Number> m) {
        DoubleMatrix copy = checkMatrix(m);
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
    public DoubleMatrix inverse(Matrix<? extends Number> m) {
        DoubleMatrix copy = checkMatrix(m);
        if (doesInverseExist(copy)) {
            DoubleMatrix inverse = new DoubleMatrix(copy.getRow());
            double determinant = determinant(copy);
            DecimalFormat df = new DecimalFormat("###.##");
            for (int i = 0; i < copy.getRow(); i++) {
                for (int j = 0; j < copy.getCol(); j++) {
                    inverse.setElement(i, j, Double.parseDouble(df.format(adjugate(copy).getMatrix().get(i).get(j) / determinant)));
                }
            }
            return inverse;
        }
        return null;
    }

    private DoubleMatrix checkMatrix(Matrix<? extends Number> m) {
        DoubleMatrix copy;
        if (m instanceof IntegerMatrix) {
            copy = Matrix.convertToDouble(m);
        } else {
            copy = (DoubleMatrix) m;
        }
        return copy;
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
        DoubleMatrix copy = checkMatrix(m);
        if (!doesInverseExist(copy)) return null;
        double[] results = new double[copy.getRow()];
        double determinant = determinant(copy.getLHS());
        double[] rhs = copy.getRHS();

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
     * @return Row echelon matrix. null if determinant is 0. Returns null if the matrix has either infinite or no solutions.
     */
    public DoubleMatrix gauss(Matrix<? extends Number> m) {
        Matrix<Double> copy = checkMatrix(m);
        return gauss(copy.getLHS(), copy.getRHS());
    }

    private DoubleMatrix gauss(Matrix<Double> result, double[] constants) {
        int length = result.getRow();
        for (int i = 0; i < length; i++) {
            int max = i;
            for (int j = i + 1; j < length; j++) {
                if (Math.abs(result.getElement(j, i)) > Math.abs(result.getElement(max, i))) {
                    max = j;
                }
            }
            Collections.swap(result.getMatrix(), i, max);
            double t = constants[i];
            constants[i] = constants[max];
            constants[max] = t;

            for (int j = i + 1; j < length; j++) {
                if (Math.abs(result.getElement(i, i)) < EPSILON) continue;
                double x = result.getElement(j, i) / result.getElement(i, i);
                constants[j] -= x * constants[i];
                result.setRowElements(j, rowSubtract(result.getRowElements(j), rowMultiply(result.getRowElements(i), x)));
            }
        }
        double[] x = new double[length];
        for (int i = length - 1; i >= 0; i--) {
            double sum = 0.0;
            for (int j = i + 1; j < length; j++) {
                sum += result.getElement(i, j) * x[j];
            }
            x[i] = (constants[i] - sum) / result.getElement(i, i);
        }

        for (int i = 0; i < length; i++) {
            result.getMatrix().get(i).add(constants[i]);
        }

        for (int i = 0; i < length; i++) {
            if (result.getElement(i, i) == 1) continue;
            if (!(Math.abs(result.getElement(i, i)) < EPSILON)) {
                if (result.getElement(i, i) > 1 || result.getElement(i, i) < 0) {
                    result.setRowElements(i, rowDivide(result.getRowElements(i), result.getElement(i, i)));
                } else if (result.getElement(i, i) > 0 && result.getElement(i, i) < 1) {
                    result.setRowElements(i, rowMultiply(result.getRowElements(i), 1 / result.getElement(i, i)));
                }
            }

            if (i == length - 1) {
                for (int j = 0; j < length + 1; j++) {
                    if ((result.getElement(i, j)) < EPSILON) {
                        result.setElement(i, j, 0D);
                    }
                }
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
    public DoubleMatrix gaussJordan(DoubleMatrix m) {
        DoubleMatrix finalized = new DoubleMatrix(m.getRow(), m.getCol(), m.getMatrix());
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
        ArrayList<Double> sub = new ArrayList<>(row.length);
        Iterator<Double> iterator = subtract.iterator();
        int i = 0;
        while (iterator.hasNext() && i < row.length) {
            sub.add(i, row[i] - iterator.next());
            i++;
        }
        return sub;
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
