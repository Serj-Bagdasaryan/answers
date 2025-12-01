package com.example.demo.others;

import java.util.LinkedHashMap;
import java.util.*;
import java.util.Map.*;

public class Two {
    public static void main(String[] args) {
        System.out.println(findMax(new int[]{1,2,3,9,4,5, 3,1,2,4,5}));
    }
    public static Integer findMax(int[] nums) {
        Map<Integer, Integer> map = new LinkedHashMap<>();
        for (Integer num : nums) {
            map.put(num, (map.getOrDefault(num, 0) + 1));
        }
        for (Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue().equals(1)) {
                return entry.getKey();
            }
        }
        return -1;
    }
}
