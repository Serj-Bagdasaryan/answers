package com.example.demo.others;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Nine {
    public static void main(String[] args) {
        System.out.println(find(new int[]{2,2,1,1,1,1,1,1,1,1,1,2,26,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6}));
    }

    public static int find(int[] array) {
        return Arrays.stream(array).boxed()
                .collect(Collectors.groupingBy(Function.identity(),
                        Collectors.counting()))
                .entrySet().stream()
                .filter(integerLongEntry -> integerLongEntry.getValue() > array.length / 2)
                .map(Map.Entry::getKey)
                .findFirst().get();
    }
}
