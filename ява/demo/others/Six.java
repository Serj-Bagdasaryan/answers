package com.example.demo.others;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Six {
    public static void main(String[] args) {
        List<Long> list = List.of(1L,2L,3L,4l,54L,5L,4L);
        Stream<Long> stream = list.stream().filter( x -> x > 3L);
        String string2 = stream.map(String::valueOf).collect(Collectors.joining(", "));
        Long[] string = stream.toArray(Long[]::new);
        String string1 = stream.toString();
        }
}
