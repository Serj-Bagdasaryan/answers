package com.example.demo.T2.checked;

import java.util.*;

class StatusData {
}

class ProcessingLogData {
    public List<String> processInputLines (List<String> inputLines) {
        Map<String, Integer> map = new LinkedHashMap<>();
        for (String str : inputLines) {
            String[] strArr = str.split(" ");
            map.merge(strArr[1].toUpperCase().substring(0, strArr[1].length() - 1), 1, Integer::sum);
        }
        List<String> result = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            result.add(entry.getKey() + ";" + entry.getValue());
        }
        return result.stream().sorted().toList();
    }
}

public class StatisticsLogServerCHECKED {

}
