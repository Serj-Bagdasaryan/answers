package com.example.demo.T5.checked;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.DoubleSummaryStatistics;
import java.util.List;

class SeriesAnalyse {
    public List<String> processingInputLines(String intervalValue, List<String> inputLines) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd:MM:yyyy:HH:mm:ss");
        String[] strings = intervalValue.split(";");
        LocalDateTime start = LocalDateTime.parse(strings[0], formatter);
        LocalDateTime end = LocalDateTime.parse(strings[1], formatter);
        ArrayList<Double> list = new ArrayList<>();
        for (String line : inputLines) {
            String[] strArr = line.split(";");
            LocalDateTime ll = LocalDateTime.parse(strArr[0], formatter);
            if ((ll.isAfter(start) || ll.equals(start))
                    && (ll.isBefore(end) || ll.equals(end))) {
                list.add(Double.parseDouble(strArr[1]));
            }
        }
        DoubleSummaryStatistics doubleSummaryStatistics =
                list.stream().mapToDouble(Double::doubleValue).summaryStatistics();

        return list.isEmpty() ? new ArrayList<String>(Collections.singleton("none")) :
                new ArrayList<>(List.of(String.format("%.3f", doubleSummaryStatistics.getMax()),
                        String.format("%.3f", doubleSummaryStatistics.getMin()),
                        String.format("%.3f", doubleSummaryStatistics.getAverage())));
    }
}

public class AverageValueOverInterval {
    public static void main(String[] args) {
        SeriesAnalyse nnn = new SeriesAnalyse();
        List<String> sss = nnn.processingInputLines(
                "01:01:2025:10:00:00;01:01:2025:11:00:00",
                new ArrayList<>(List.of(
                        /*"01:01:2025:09:30:00;-12.5",
                        "01:01:2025:10:00:00;15.5",
                        "01:01:2025:10:30:00;-8.3"*/
                /*"01:01:2025:12:00:00;01:01:2025:12:30:00",
                        "01:01:2023:12:15:00;42.3"*/
                        "01:01:2025:10:00:00;15",
                        "01:01:2025:11:00:00;-5"
                )));

        for (String string : sss) {
            System.out.println(string);
        }
    }
}