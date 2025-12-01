package com.example.demo.others;

import java.util.Arrays;

public class DemoApplication {
    public static void main(String[] args) {

    }

    public static int[] sort(int[] nums1, int m, int[] nums2, int n) {
        int[] result = new int[m + n];
        System.arraycopy(nums1, 0, result, 0, m);
        System.arraycopy(nums2, 0, result, m, n);
        Arrays.sort(result);
        return result;
    }
}
