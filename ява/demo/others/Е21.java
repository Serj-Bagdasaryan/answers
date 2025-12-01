package com.example.demo.others;

public class Е21 {
        public static String countAndIncrement(String digits) {
            java.util.LinkedHashMap<String, Integer> map = new java.util.LinkedHashMap<>();
            for ( char ch : digits.toCharArray()) {
                map.put(ch+"", (map.getOrDefault(ch+"", 0) + 1));
            }
            StringBuilder stringBuilder = new StringBuilder();
            for (java.util.Map.Entry<String, Integer> entry : map.entrySet()) {
                stringBuilder.append(entry.getKey());
                stringBuilder.append(":");
                stringBuilder.append(entry.getValue() + 1);
                stringBuilder.append(",");
            }
            return stringBuilder.toString().substring(0,stringBuilder.toString().length()-1);
        }

        public static void main(String[] args) {
            System.out.println(countAndIncrement("13133")); // -> 1:3;3:4
            System.out.println(countAndIncrement("99999")); // -> 9:6
            System.out.println(countAndIncrement("99"));    // -> 9:3
            System.out.println(countAndIncrement("13123")); // -> 1:3;3:3;2:2
        }
    }

