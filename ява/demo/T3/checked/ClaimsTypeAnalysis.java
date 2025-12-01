package com.example.demo.T3.checked;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;

class ProcessingInsurancePayments {

    public List<String> processingInputLines(List<String> inputLines) {
        if (inputLines == null || inputLines.isEmpty()) {
            return new ArrayList<>();
        }

        ConcurrentHashMap<String, LongAdder> totals = new ConcurrentHashMap<>();

        inputLines.parallelStream().forEach(rawLine -> {
            if (rawLine == null) return;
            String line = rawLine.trim();
            if (line.isEmpty()) return;

            int len = line.length();
            int idx = 0;

            int comma1 = line.indexOf(',', idx);
            if (comma1 == -1) return;
            String type = line.substring(idx, comma1);

            idx = comma1 + 1;
            int comma2 = line.indexOf(',', idx);
            if (comma2 == -1) {
                return;
            }
            idx = comma2 + 1;

            long lineSum = 0L;
            while (idx < len) {
                int nextComma = line.indexOf(',', idx);
                String segment;
                if (nextComma == -1) {
                    segment = line.substring(idx);
                } else {
                    segment = line.substring(idx, nextComma);
                }
                int colon = segment.indexOf(':');
                if (colon > 0 && colon < segment.length() - 1) {
                    String paymentStr = segment.substring(colon + 1);
                    try {
                        long payment = Long.parseLong(paymentStr);
                        lineSum += payment;
                    } catch (NumberFormatException ignored) {
                    }
                }
                if (nextComma == -1) break;
                idx = nextComma + 1;
            }

            if (lineSum > 0) {
                totals.computeIfAbsent(type, k -> new LongAdder()).add(lineSum);
            } else {
            }
        });

        List<Map.Entry<String, Long>> aggregated = totals.entrySet()
                .stream()
                .map(e -> Map.entry(e.getKey(), e.getValue().sum()))
                .collect(Collectors.toList());

        Comparator<Map.Entry<String, Long>> cmp = Comparator
                .<Map.Entry<String, Long>>comparingLong(Map.Entry::getValue).reversed()
                .thenComparing(e -> e.getKey(), String.CASE_INSENSITIVE_ORDER);

        List<String> result = aggregated.stream()
                .sorted(cmp)
                .limit(3)
                .map(e -> e.getKey() + "," + e.getValue())
                .collect(Collectors.toList());

        return result;
    }
}

public class ClaimsTypeAnalysis {
    // Простой main для локальной проверки
    public static void main(String[] args) {
        ProcessingInsurancePayments proc = new ProcessingInsurancePayments();

        List<String> input1 = List.of(
                /*"Car,10000,ABCD:5000,EFGH:3000",
                "Home,10001,IJKL:10000",
                "Car,10002,MNOP:7000,QRST:2000",
                "Life,10003,UVWX:15000",
                "Home,10004,YZAB:8000,CDEF:4000",
                "Life,10005,GHIJ:12000"*/

                /*"ShortTermCare,120001,QRST:10000",
                "LongTermCare,120002,UVWX:20000,YZAB:30000",
                "Hospice,120003,CDEF:40000",
                "HomeHealth,120004,GHIJ:50000,KLMN:60000,OPQR:70000",
                "DMEPOS,120005,STUV:80000",
                "AdvancedImaging,120006,WXYZ:90000,ABCD:10000,EFGH:20000,IJKL:30000",
                "MRIs,120007,MNOP:40000",
                "CTScans,120008,QRST:50000,UVWX:60000",
                "PETScans,120009,YZAB:70000",
                "PreventiveCare,120010,CDEF:80000,GHIJ:90000,KLMN:10000,OPQR:20000",
                "Screening,120011,STUV:30000",
                "Immunization,120012,WXYZ:40000,ABCD:50000",
                "Rehabilitation,120013,EFGH:60000",
                "Home,120014,IJKL:70000,MNOP:80000,QRST:90000,UVWX:10000"*/

                /*"Car,99999,ZZZZ:1,AAAA:50000"*/

                "DMEPOS,140001,KLMN:10000",
                "AdvancedImaging,140002,OPQR:20000,STUV:30000",
                "MRIs,140003,WXYZ:40000",
                "CTScans,140004,ABCD:50000,EFGH:60000,IJKL:70000",
                "PETScans,140005,MNOP:80000",
                "PreventiveCare,140006,QRST:90000,UVWX:10000,YZAB:20000,CDEF:30000",
                "Screening,140007,GHIJ:40000",
                "Immunization,140008,KLMN:50000,OPQR:60000",
                "Rehabilitation,140009,STUV:70000,WXYZ:80000",
                "Home,140010,ABCD:90000",
                "Car,140011,EFGH:10000,IJKL:20000,MNOP:30000,QRST:40000",
                "Life,140012,UVWX:50000",
                "Pet,140013,YZAB:60000,CDEF:70000,GHIJ:80000",
                "Fire,140014,KLMN:90000,OPQR:10000,STUV:20000,WXYZ:30000"
        );

        List<String> out1 = proc.processingInputLines(input1);
        out1.forEach(System.out::println);
        // Ожидаемый:
        // Life,27000
        // Home,22000
        // Car,17000


    }
}



