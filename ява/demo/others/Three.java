package com.example.demo.others;

public class Three {
    public static void main(String[] args) {
        System.out.println(isPalindrome("А роза упала на лапу Азора"));
    }
    public static boolean isPalindrome(String str) {
        str = str.toLowerCase().chars()
                .filter(Character::isLetter)
                .collect(StringBuilder::new,
                        StringBuilder::appendCodePoint,
                        StringBuilder::append).toString();
        int left = 0;
        int right = str.length() - 1;
        while (left < right) {
            char l = str.charAt(left++);
            char r = str.charAt(right--);
            if (r != l){
                return false;
            }
        }
        return true;
    }
}
