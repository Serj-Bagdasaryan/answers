package com.example.demo.T3.checked;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;

class ProcessingInsurancePayments {
    public List<String> processingInputLines(List<String> inputLines) {
        ConcurrentHashMap<String, LongAdder> totals = new ConcurrentHashMap<>();

        inputLines.parallelStream().forEach(rawLine -> {
            if (rawLine == null) return;
            String line = rawLine.trim();
            if (line.isEmpty()) return;

            String[] parts = line.split(",");
            if (parts.length < 3) return; // нет полисов — по условию не бывает, но на всякий случай

            String type = parts[0];

            long lineSum = 0L;
            for (int i = 2; i < parts.length; i++) {
                String p = parts[i];
                if (p == null || p.isEmpty()) continue;
                String[] pp = p.split(":");
                if (pp.length != 2) continue;
                try {
                    long payment = Long.parseLong(pp[1]);
                    lineSum += payment;
                } catch (NumberFormatException ignored) {
                }
            }

            if (lineSum > 0) {
                totals.computeIfAbsent(type, k -> new LongAdder()).add(lineSum);
            }
        });

        return totals.entrySet()
                .stream()
                .map(e -> new AbstractMap.SimpleEntry<>(e.getKey(), e.getValue().sum()))
                .sorted((a, b) -> {
                    int cmp = Long.compare(b.getValue(), a.getValue()); // по убыванию суммы
                    if (cmp != 0) return cmp;
                    return a.getKey().compareToIgnoreCase(b.getKey()); // при равенстве — по Type по алфавиту без учёта регистра
                })
                .limit(3)
                .map(e -> e.getKey() + "," + e.getValue())
                .collect(Collectors.toList());
    }
}


public class AnalysisInsurancePayment {
}
