package com.example.demo.others;

public class T15 {
    public static void main(String[] args) {
        System.out.println(getMaxSum(new int[]{-100, 99, -50, 100, -1000,-2,1,-3,4,-1,2,1,-5,4}));
    }

    public static int getMaxSum(int[] array) {
        int maxSum = 0;
        int currentSum = 0;

        for (int i = 0; i < array.length; i++) {
            currentSum = Math.max(array[i], currentSum + array[i]);
            maxSum = Math.max(maxSum, currentSum);
        }
        return maxSum;
    }
}
