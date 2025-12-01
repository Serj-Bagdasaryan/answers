package com.example.demo.T5.checked;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

class Transaction {
    final String timeStr;
    final int minutes; // minutes since 00:00
    final int payment;

    Transaction(String timeStr, int minutes, int payment) {
        this.timeStr = timeStr;
        this.minutes = minutes;
        this.payment = payment;
    }
}

interface LineValidator {
    Optional<Transaction> validate(String line);
}

class TransactionValidator implements LineValidator {
    private static final int MIN_PAYMENT = 50;
    private static final int MAX_PAYMENT = 50000;
    private static final int MAX_MINUTES = 23 * 60 + 30; // 23:30

    @Override
    public Optional<Transaction> validate(String rawLine) {
        if (rawLine == null) return Optional.empty();
        String line = rawLine.trim();
        if (line.isEmpty()) return Optional.empty();
        String[] parts = line.split(";", -1);
        if (parts.length != 2) return Optional.empty();

        String timePart = parts[0].trim();
        String paymentPart = parts[1].trim();

        String[] hhmm = timePart.split(":", -1);
        if (hhmm.length != 2) return Optional.empty();
        int hh, mm;
        try {
            hh = Integer.parseInt(hhmm[0]);
            mm = Integer.parseInt(hhmm[1]);
        } catch (NumberFormatException ex) {
            return Optional.empty();
        }
        if (hh < 0 || hh > 23 || mm < 0 || mm > 59) return Optional.empty();
        int minutes = hh * 60 + mm;
        if (minutes < 0 || minutes > MAX_MINUTES) return Optional.empty();

        int payment;
        try {
            payment = Integer.parseInt(paymentPart);
        } catch (NumberFormatException ex) {
            return Optional.empty();
        }
        if (payment < MIN_PAYMENT || payment > MAX_PAYMENT) return Optional.empty();

        return Optional.of(new Transaction(String.format("%02d:%02d", hh, mm), minutes, payment));
    }
}

class AnomalyDetector {
    private static final AnomalyDetector INSTANCE = new AnomalyDetector();

    private AnomalyDetector() {}

    public static AnomalyDetector getInstance() {
        return INSTANCE;
    }

    public Set<String> detect(List<Transaction> transactions) {
        Set<String> result = new HashSet<>();
        if (transactions == null || transactions.isEmpty()) {
            return result;
        }

        boolean hasAP = transactions.stream().anyMatch(t -> t.payment >= 10000);
        if (hasAP) result.add("AP");

        int cnt = 1;
        for (int i = 1; i < transactions.size(); i++) {
            if (transactions.get(i).payment == transactions.get(i - 1).payment) {
                cnt++;
            } else {
                if (cnt >= 3) {
                    result.add("AS");
                    break;
                }
                cnt = 1;
            }
        }
        if (cnt >= 3) result.add("AS");

        return result;
    }
}

class TransactionAnalyser {
    public List<String> processingInputLines(List<String> inputLines) {
        LineValidator validator = new TransactionValidator();

        List<Transaction> valid = new ArrayList<>();
        for (String line : inputLines) {
            Optional<Transaction> t = validator.validate(line);
            t.ifPresent(valid::add);
        }

        List<String> output = new ArrayList<>();
        if (valid.isEmpty()) return output;

        AnomalyDetector detector = AnomalyDetector.getInstance();

        int n = valid.size();
        int idx = 0;
        while (idx < n) {
            Transaction startTx = valid.get(idx);
            int startMinutes = startTx.minutes;
            int endMinutes = startMinutes + 20;
            int j = idx;
            List<Transaction> bucket = new ArrayList<>();
            while (j < n && valid.get(j).minutes <= endMinutes) {
                bucket.add(valid.get(j));
                j++;
            }

            String time1 = formatMinutes(startMinutes);
            String time2 = formatMinutes(endMinutes);
            Set<String> anomalies = detector.detect(bucket);

            String message;
            if (anomalies.isEmpty()) {
                message = "none";
            } else {
                List<String> sorted = new ArrayList<>(anomalies);
                Collections.sort(sorted);
                message = String.join(" ", sorted);
            }

            output.add(String.format("%s - %s - %s", time1, time2, message));

            idx = j;
        }

        return output;
    }

    private static String formatMinutes(int minutesTotal) {
        int hh = (minutesTotal / 60) % 24;
        int mm = minutesTotal % 60;
        return String.format("%02d:%02d", hh, mm);
    }
}

public class AnomalyTransactional {
}
