package com.example.demo.others;

import java.util.Arrays;

public class T16 {
    public static void main(String[] args) {
        System.out.println(Arrays.deepToString(
                find(new int[][]{{1, 3}, {5, 7}, {9, 11}, {13, 15}}, new int[]{5, 10})));
    }

    public static int[][] find(int[][] array, int[] interval) {
        int beginIndex = 0;
        int endIndex = 0;
        int[][] result;
        for (int i = 1; i < array.length; i++) {
            if (array[i][0] >= interval[0]) {
                if (array[i - 1][1] >= interval[0]) {
                    beginIndex = i - 1;
                } else {
                    beginIndex = i;
                }
                break;
            }
        }
        if (beginIndex == 0 && array[0][1] < interval[0]) {
            beginIndex = array.length - 1;
        }
        for (int i = beginIndex + 1; i < array.length; i++) {
            if (array[i][0] >= interval[1]) {
                if (array[i][0] == interval[1]) {
                    endIndex = i;
                } else {
                    endIndex = i - 1;
                }
                break;
            }
        }
        if (endIndex == 0 && array[0][1] < interval[1]) {
            endIndex = array.length - 1;
        }

        result = new int[array.length - endIndex + beginIndex][2];
        System.arraycopy(array, 0, result, 0, beginIndex + 1);
        result[beginIndex][0] = Math.min(array[beginIndex][0], interval[0]);
        result[beginIndex][1] = Math.max(array[endIndex][1], interval[1]);
        System.arraycopy(array, endIndex + 1, result, beginIndex + 1, result.length - beginIndex - 1);
        return result;
    }
}
