package com.example.demo.T5.checked;

import java.util.*;


class SeriesAnalyser {
    public List<String> processingInputLines(List<String> inputLines) {
        // ваш код
        Iterator<String> iterator = inputLines.iterator();
        Integer size = Integer.parseInt(iterator.next());
        List<String> result = new ArrayList<>();
        result.add((size / 2) + "");
        while (iterator.hasNext()) {
            String[] zero = iterator.next().trim() .split(" ");
            String[] one = iterator.next().trim().split(" ");
            StringBuilder sB = new StringBuilder();
            for (int i = 0; i < size; i +=2) {
                sB.append(((Integer.parseInt(zero[i + 1]) + Integer.parseInt(one[i])) / 2) + "");
                sB.append(" ");
            }
            result.add(sB.toString());
        }
        return result;
    }
    static class MatrixValidator {
// ваш код
    }
    static class MedianCalculator {
        private static final MedianCalculator INSTANCE = new MedianCalculator();
// ваш код
    }
}

public class Matrix {
    public static void main(String[] args) {
        SeriesAnalyzer sA = new SeriesAnalyzer();
        sA.processingInputLines(List.of(/*"8",
                "0 50 100 150 200 250 255 255 255",
                "0 50 100 150 200 250 255 255 255",
                "0 50 100 150 200 250 255 255 255",
                "0 50 100 150 200 250 255 255 255",
                "0 50 100 150 200 250 255 255 255",
                "0 50 100 150 200 250 255 255 255",
                "0 50 100 150 200 250 255 255 255",
                "0 50 100 150 200 250 255 255 255"*/
                "8",
                "1 3 5 7 9 11 13 15",
                "2 4 6 8 10 12 14 16",
                "17 19 21 23 25 27 29 31",
                "18 20 22 24 26 28 30 32",
                "33 35 37 39 41 43 45 47",
                "34 36 38 40 42 44 46 48",
                "49 51 53 55 57 59 61 63",
                "50 52 54 56 58 60 62 64"
        )).forEach(System.out::println);
    }
}