package com.example.demo.T1.checked;

import java.util.*;

class LotteryTicket {

    public String countDigits(String digits) {
        // Ваш код
        HashMap<Character, Integer> map = new LinkedHashMap<>();
        for (char ch : digits.toCharArray()) {
            map.put(ch, map.getOrDefault(ch, 0) + 1);
        }
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            sb.append(entry.getKey());
            sb.append(":");
            sb.append(entry.getValue() + 1);
            sb.append(",");
        }
        return sb.substring(0, sb.toString().length() - 1);
    }
}