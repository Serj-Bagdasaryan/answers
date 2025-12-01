package com.example.demo.T2.checked;

import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;

class SalesReport {
    public String generateReport(String salesData) {
        if (salesData == null) return "";
        String data = salesData.trim();
        if (data.isEmpty()) return "";

        Map<Integer, Map<String, Integer>> quartMap = new HashMap<>();

        String[] records = data.split(";");
        for (String rec : records) {
            if (rec == null) continue;
            String r = rec.trim();
            if (r.isEmpty()) continue;

            String[] parts = r.split(":", 3);
            if (parts.length != 3) continue; // защищаемся от некорректных записей

            String date = parts[0];
            String product = parts[1];
            String qtyStr = parts[2];

            if (date.length() < 7) continue;
            int month;
            try {
                month = Integer.parseInt(date.substring(5, 7));
            } catch (NumberFormatException ex) {
                continue;
            }
            int quarter = (month - 1) / 3 + 1;
            int qty;
            try {
                qty = Integer.parseInt(qtyStr);
            } catch (NumberFormatException ex) {
                continue;
            }

            quartMap.computeIfAbsent(quarter, k -> new HashMap<>())
                    .merge(product, qty, Integer::sum);
        }

        StringBuilder sb = new StringBuilder();
        for (int q = 1; q <= 4; q++) {
            Map<String, Integer> prodMap = quartMap.get(q);
            if (prodMap == null || prodMap.isEmpty()) continue;

            if (sb.length() > 0) sb.append("\n");
            sb.append("Q").append(q).append(":\n");

            Map<String, Integer> sorted = new TreeMap<>(prodMap);
            boolean first = true;
            for (Map.Entry<String, Integer> e : sorted.entrySet()) {
                if (!first) {
                    sb.append("\n");
                }
                first = false;
                sb.append("- ").append(e.getKey()).append(": ").append(e.getValue());
            }
        }

        return sb.toString();
    }
}


public class SalesReportQuart {
    public static void main(String[] args) {
        SalesReport salesReport = new SalesReport();
        System.out.println(salesReport.generateReport("2023-02-05:Шляпа:4;2023-03-20:Кольцо:7;2023-04-25:Браслет:6;2023-04-26:Браслет:12"));
    }
}
