package com.example.demo.T4.checked;

import java.util.*;
class ProcessingPerformanceLog {
    public List<String> processingInputLines(List<String> inputLines) {
        Map<String, Integer> errors = new HashMap<>();
        Map<String, Integer> highLoad = new HashMap<>();

        for (String line : inputLines) {
            String[] array = line.split(",");
            if (array[2].equalsIgnoreCase("error")
                    && array[0].matches("^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])T([01]\\d|2[0-3]):[0-5]\\d:[0-5]\\d$")
            ) {
                errors.merge(array[1], 1, Integer::sum);
            }
            try {
                if (!array[2].equalsIgnoreCase("error")) {
                    if (Integer.parseInt(array[3]) >= 1000) {
                        highLoad.merge(array[1], 1, Integer::sum);
                    }
                } else {
                    if (Integer.parseInt(array[3]) >= 100 && Integer.parseInt(array[3]) <= 300) {
                        highLoad.merge(array[1], 1, Integer::sum);
                    }
                }
            } catch (NumberFormatException e) {
            }

        }
        Set<String> result = new HashSet<>();
        for (Map.Entry<String, Integer> entry : errors.entrySet()) {
            if (entry.getValue() >= 3) {
                result.add(entry.getKey());
            }
        }
        for (Map.Entry<String, Integer> entry : highLoad.entrySet()) {
            if (entry.getValue() >= 2) {
                result.add(entry.getKey());
            }
        }
        if (result.isEmpty()) {
            result.add("none");
        }
        return result.stream().toList();
    }
}


public class TroubleServices {
    public static void main(String[] args) {
        ProcessingPerformanceLog ppl = new ProcessingPerformanceLog();
        ppl.processingInputLines(List.of(
                /*"2023-05-10T14:30: 00,1001,INFO,120",
                "2023-05-10T14:32:00,1001,ERROR,200",
                "2023-05-10T14:33:00,1001,INFO,1100",
                "2023-05-10T14:34:00,1001,ERROR,201",
                "2023-05-10T14:35:00,1001,ERROR,202",
                "2023-05-10T14:36:00,2002,INFO,2300",
                "2023-05-10T14:37:00,2002,WARN,1200",
                "2023-05-10T14:38:00,3003,INFO,50"*/
                /*"2023-05-10T14:30:00,999,INFO,120",
                "2023-05-10T14:31:00,1001,WARN,1500",
                "2023-05-10T14:32:00,1001,ERROR,500",
                "2023-05-10T14:33:00,1001,INFO,invalid",
                "2023-05-10T14:34:00,1001,ERROR,201",
                "2023-05-10T14:35:00,1001,ERROR,202",
                "2023-05-10T14:36:00,2002,INFO,300",
                "2023-05-10T14:37:00,2002,WARN,1200",
                "2023-05-10T14:38:00,3003,INFO,50"*/
                "2024-01-15T01:25:25,2000,INFO,999",
                "2024-02-25T02:26:26,3000,WARN,999",
                "2024-03-05T03:27:27,4000,ERROR,200",
                "2024-04-15T04:28:28,5000,INFO,500",
                "2024-05-25T05:29:29,6000,WARN,500",
                "2024-06-05T06:30:30,7000,ERROR,200",
                "2024-07-15T07:31:31,8000,INFO,100",
                "2024-08-25T08:32:32,9000,WARN,100"
        )).stream().forEach(System.out::println);
    }
}