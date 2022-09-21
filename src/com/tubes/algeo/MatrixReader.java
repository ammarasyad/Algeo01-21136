package com.tubes.algeo;

import javax.xml.stream.events.Characters;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MatrixReader {

    private static MatrixReader INSTANCE = null;

    private MatrixReader() {
    }

    public static MatrixReader getInstance() {
        if (INSTANCE == null) INSTANCE = new MatrixReader();
        return INSTANCE;
    }

    public DoubleMatrix createDMFromFile(String filename) {
        File file = new File(filename);
        DoubleMatrix matrix = null;
        long row;
        int col;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            try (Stream<String> stream = Files.readAllLines(file.toPath()).stream()) {
                row = stream.count();
            }

            String line = br.readLine();
            col = line.trim().replaceAll("\\s+", "").length();
            double[] l;

            List<List<Double>> list = new ArrayList<>((int) row);
            while (line != null) {
                l = line.trim().replaceAll("\\s+", "").chars().mapToDouble(i -> i - '0').toArray();
                list.add(Arrays.stream(l).boxed().toList());
                line = br.readLine();
            }
            matrix = new DoubleMatrix((int) row, col, list);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return matrix;
    }

    public IntegerMatrix createIMFromFile(String filename) {
        return Matrix.convertToInteger(createDMFromFile(filename));
    }
}
