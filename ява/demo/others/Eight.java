package com.example.demo.others;

import java.util.Arrays;

public class Eight {
    public static void main(String[] args) {
        int[] array = {0,1,2,3,4,5,6,7,8,9,10};
        //                  0,1,2,3,4,5,6,7,8,9,10
        //                            5   7
        System.out.println(findFirst(array));
    }

    public static int findFirst(int[] array) {
        if (isBadVersion(array[array.length / 2])) {
            if (!isBadVersion(array[array.length / 2 - 1])) {
                return array[array.length / 2];
            } else {
                return findFirst(Arrays.copyOfRange(array, 0, array.length / 2));
            }
        } else {
            return findFirst(Arrays.copyOfRange(array, array.length / 2, array.length + 1));
        }
    }

    public static boolean isBadVersion(int ver) {
        return ver >= 9;
    }

}
