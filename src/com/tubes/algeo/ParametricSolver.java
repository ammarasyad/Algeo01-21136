package com.tubes.algeo;

import java.text.DecimalFormat;

public class ParametricSolver {
    public static String[] solve(Matrix<? extends Number> matrix) {
        DecimalFormat df = new DecimalFormat("###.##");
        String[] result = new String[matrix.getCol() - 1];
        int count = 0;
        int[] idx = new int[result.length];
        for (int i = 0; i < matrix.getCol() - 1 && i < matrix.getRow() - 1; i++) {
            if ((Double) matrix.getElement(i, i) == 1D) {
                idx[count] = i;
                count++;
            }
        }
        for (int i = matrix.getLHS().getCol() - 1; i >= 0; i--) {
            StringBuilder temp = new StringBuilder();
            int index = Math.min(i, matrix.getRow() - 1);
            if ((Double) matrix.getElement(index, index) == 1D) {
                temp.append("x").append(i + 1).append(" = ").append(df.format(matrix.getElement(index, matrix.getCol() - 1)));
                for (int j = i + 1; j < result.length; j++) {
                    double x = (double) matrix.getElement(i, j);
                    if (x < 0) {
                        temp.append(" + ").append(df.format(-x)).append(makeVariable(j, idx, count));
                    } else if (x > 0) {
                        if (x == 1) {
                            temp.append(" - t");
                        } else {
                            temp.append(" - ").append(df.format(x)).append("(").append(result[i + 1].split("x[0-9] = ")[1]).append(")");
                        }
                    }
                }
            } else {
                temp.append("x").append(i + 1).append(" = ").append(makeVariable(result.length - i, idx, count));
            }
            result[i] = String.valueOf(temp);
        }
        return result;
    }

    private static char makeVariable(int j, int[] idx, int count) {
        char res = 'a';
        for (int i = 0; i < count; i++) {
            if (idx[i] == j) {
                res = (char) (((int) res) + i);
                return res;
            }
        }
        return 't';
    }
}
