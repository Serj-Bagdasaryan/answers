package com.example.demo.others;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Five {
    public static void main(String[] args) throws InterruptedException {
        find(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 8, 4, 3, 3, 3, 3,});
    }

    public static void find(int[] array) {
        Map<Integer, Integer> map = new LinkedHashMap<>();
        for (int number : array) {
            map.put(number, map.getOrDefault(number, 0) + 1);
        }

        for (Map.Entry<Integer, Integer> res : map.entrySet()) {
            if (res.getValue() > 1) {
                System.out.println(res.getKey() + " " + res.getValue());
            }
        }
    }

    public static void findWithStream(int[] array) {
        Arrays.stream(array).boxed()
                .collect(Collectors.groupingBy(Function.identity(),
                        Collectors.counting()))
                .entrySet().stream()
                .filter(integerLongEntry -> integerLongEntry.getValue() > 1)
                .forEach(System.out::println);
    }
}