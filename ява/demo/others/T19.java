package com.example.demo.others;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class T19 {
    public static void main(String[] args) {
        System.out.println(find(new int[]{1,2,3,4,5,6,7,8,9,1,2,3,4,5,9,6,7,8,10}));
    }

    public static long find(int[] array) {
        return Arrays.stream(array).boxed().
                collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                )).entrySet().stream().
                filter(x -> x.getValue() == 1).findFirst().map(Map.Entry::getValue).orElse(-1L);
    }
}
