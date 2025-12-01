package com.example.demo.others;

public class Seven {
    public static void main(String[] args) {
        String str = "Привет, Кккккатовццццы!";
        System.out.println(trimmer(str));
    }

    public static String trimmer(String input) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(input.charAt(0));
        for (int j = 1; j < input.length(); j++) {
            if (Character.toLowerCase(input.charAt(j)) != Character.toLowerCase(input.charAt(j - 1))) {
                stringBuilder.append(input.charAt(j));
            }
        }
        return stringBuilder.toString();
    }
}
