package com.example.demo.T4.checked;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Payment {
    private final int id;
    private final List<String> services;

    Payment(int id, List<String> services) {
        this.id = id;
        this.services = services;
    }

    int getId() {
        return id;
    }

    List<String> getServices() {
        return services;
    }

    boolean usesService(String svc) {
        for (String s : services) {
            if (s.equals(svc)) return true;
        }
        return false;
    }
}

class PaymentSystemModelling {

    public int processingInputLines(List<String> inputLines) {
        if (inputLines == null || inputLines.isEmpty()) return 0;

        int idx = 0;
        int found = 0;
        Map<Integer, Payment> customers = new HashMap<>();
        int[] customerIds = new int[]{100, 101, 102};

        while (idx < inputLines.size() && found < 3) {
            String line = inputLines.get(idx);
            idx++;
            if (line == null) continue;
            String trimmed = line.trim();
            if (trimmed.isEmpty()) continue;
            String[] svcParts = trimmed.split(",");
            List<String> svcs = new ArrayList<>();
            for (String s : svcParts) {
                String ss = s.trim();
                if (!ss.isEmpty()) svcs.add(ss);
            }
            customers.put(customerIds[found], new Payment(customerIds[found], svcs));
            found++;
        }

        if (found < 3) return 0;

        int failedSum = 0;

        while (idx < inputLines.size()) {
            String line = inputLines.get(idx++);
            if (line == null) continue;
            String trimmed = line.trim();
            if (trimmed.isEmpty()) continue;

            String[] parts = trimmed.split(",");
            if (parts.length < 4) continue;

            try {
                int customerId = Integer.parseInt(parts[0].trim());
                Payment customer = customers.get(customerId);
                if (customer == null) {
                    int payment = Integer.parseInt(parts[2].trim());
                    failedSum += payment;
                    continue;
                }

                int paymentAmount = Integer.parseInt(parts[2].trim());

                List<String> contractorServices = new ArrayList<>();
                for (int i = 3; i < parts.length; i++) {
                    String svc = parts[i].trim();
                    if (!svc.isEmpty()) contractorServices.add(svc);
                }

                boolean transferred = false;
                for (String cSvc : contractorServices) {
                    if (customer.usesService(cSvc)) {
                        transferred = true;
                        break;
                    }
                }

                if (!transferred) {
                    failedSum += paymentAmount;
                }
            } catch (NumberFormatException ex) {
            }
        }

        return failedSum;
    }
}


public class FreelanceExchange {
}
