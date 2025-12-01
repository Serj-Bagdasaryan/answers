package com.example.demo.others;

public class T14{
    public static void main(String[] args) {
        System.out.println(get("a#cb####fgh", "#b#cb#fgh#h"));
    }

    public static boolean get(String s, String t) {
        return getSub(s).equalsIgnoreCase(getSub(t));
    }

    private static String getSub(String s) {
        StringBuilder sbs = new StringBuilder();
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) != '#' && s.charAt(i - 1) != '#') {
                sbs.append(s.charAt(i - 1));
            }
        }
        if (s.charAt(s.length() - 1) != '#') {
            sbs.append(s.charAt(s.length() - 1));
        }
        return sbs.toString();
    }
}
