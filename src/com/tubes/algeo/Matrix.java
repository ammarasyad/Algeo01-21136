package com.tubes.algeo;

import java.lang.reflect.Array;
import java.util.*;

@SuppressWarnings("unchecked")
public class Matrix<T> {

    private final int row;
    private final int col;

    private final List<List<T>> matrix;

    /**
     * Non-square non-empty matrix initialization.
     * @param row Number of rows
     * @param col Number of columns
     * @param x List of lists of any Number type (Matrix of Integer/Float/Double)
     */
    public Matrix(int row, int col, List<List<T>> x) {
        this.row = row;
        this.matrix = new ArrayList<>(row);
        for (int i = 0; i < row; i++) {
            if (x != null) {
                this.matrix.add(x.get(i));
            } else {
                this.matrix.add(new ArrayList<>(col));
                for (int j = 0; j < col; j++) { // default matrix initialization is zero
                    this.matrix.get(i).add((T) Double.valueOf(0)); // will always be a number
                }
            }
        }
        if (x != null) {
            if (col != x.get(0).size()) {
                this.col = x.get(0).size();
            } else {
                this.col = col;
            }
        } else {
            this.col = col;
        }
    }

    /**
     * Non-square empty matrix initialization.
     * @param row Number of rows
     * @param col Number of columns
     */
    public Matrix(int row, int col) {
        this(row, col, null);
    }

    /**
     * Square empty matrix initialization.
     * @param size NxN
     */
    public Matrix(int size) {
        this(size, size);
    }

    public void printMatrix() {
        for (List<T> l : getMatrix()) {
            System.out.println(l);
        }
    }

    public List<List<T>> getMatrix() {
        return matrix;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public double[] getRHS() {
        ArrayList<T> list = new ArrayList<>(getCol());
        for (List<T> l : getMatrix()) {
            list.add(l.get(getRow()));
        }
        double[] rhs = new double[list.size()];
        for (int i = 0; i < list.size(); i++) {
            rhs[i] = (double) list.get(i);
        }
         return rhs;
    }

    public Matrix<T> copyMatrixLHS(Matrix<T> in) {
        if (in.getRow() == in.getCol()) return in;
        Matrix<T> temp = new Matrix<>(in.getRow());
        for (int i = 0; i < in.getRow(); i++) {
            for (int j = 0; j < in.getCol() - 1; j++) {
                temp.getMatrix().get(i).set(j, in.getMatrix().get(i).get(j));
            }
        }
        return temp;
    }

    public Matrix<T> getLHS() {
        Matrix<T> temp = new Matrix<>(getRow(), getCol() - 1);
        for (int i = 0; i < getRow(); i++) {
            for (int j = 0; j < getCol() - 1; j++) {
                temp.setElement(i, j, getElement(i, j));
            }
        }
        return temp;
    }

    public T getElement(int i, int j) {
        return getMatrix().get(i).get(j);
    }

    public void setElement(int row, int col, T element) {
        getMatrix().get(row).set(col, element);
    }

    public void setRowElements(int row, ArrayList<T> element) {
        ListIterator<T> iterator = getMatrix().get(row).listIterator();
        Iterator<T> iterator1 = element.iterator();
        while (iterator.hasNext()) {
            iterator.next();
            iterator.set(iterator1.next());
        }
    }

    public T[] getRowElements(int row) {
        T[] copy = (T[]) Array.newInstance(Double.class, getMatrix().get(row).size());
        Iterator<T> iterator = getMatrix().get(row).iterator();

        int i = 0;
        while (iterator.hasNext()) {
            copy[i] = iterator.next();
            i++;
        }

//        return Arrays.copyOf((T[]) Array.newInstance(Double.class, row), getCol());
        return copy;
    }
}
