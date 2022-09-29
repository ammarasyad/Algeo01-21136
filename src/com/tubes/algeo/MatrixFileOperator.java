package com.tubes.algeo;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class MatrixFileOperator {

    public static DoubleMatrix createDMFromFile(String filename) {
        File file = new File(filename);
        DoubleMatrix matrix = null;
        long row;
        int col;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            try (Stream<String> stream = Files.readAllLines(file.toPath()).stream()) {
                row = stream.count();
            }

            String line = br.readLine();
            col = line.trim().replaceAll("-?[0-9 ]\\d*(\\.\\d+)?", "1").length();
            double[] l = new double[col];

            List<List<Double>> list = new ArrayList<>((int) row);
            while (line != null) {
                l = Arrays.stream(line.trim().split("\\s+")).mapToDouble(Double::parseDouble).toArray();
                list.add(Arrays.stream(l).boxed().toList());
                line = br.readLine();
            }
            matrix = new DoubleMatrix((int) row, col, list);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return matrix;
    }

    public static IntegerMatrix createIMFromFile(String filename) {
        return Matrix.convertToInteger(createDMFromFile(filename));
    }

    public static void writeMatrixToFile(String filename, DoubleMatrix matrix) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (int i = 0; i < matrix.getRow(); i++) {
                for (int j = 0; j < matrix.getCol(); j++) {
                    writer.write(matrix.getElement(i, j) + (j < matrix.getCol() - 1 ? " " : ""));
                }
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
