package com.example.demo.T3.checked;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

class ParseLogData {
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy:HH:mm:ss");

    static class LogEntry {
        final String ip;
        final LocalDateTime dateTime;
        final int returnCode;
        final int processingTime;

        LogEntry(String ip, LocalDateTime dateTime, int returnCode, int processingTime) {
            this.ip = ip;
            this.dateTime = dateTime;
            this.returnCode = returnCode;
            this.processingTime = processingTime;
        }
    }

    LogEntry parse(String line) {
        String s = line == null ? "" : line.trim();
        int firstSpace = s.indexOf(' ');
        String ip = firstSpace > 0 ? s.substring(0, firstSpace) : s;

        int b = s.indexOf('[');
        int e = s.indexOf(']');
        String dateTimeStr = (b >= 0 && e > b) ? s.substring(b + 1, e) : "01/01/1970:00:00:00";
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, dtf);

        String[] parts = s.split(" ");
        int n = parts.length;
        int returnCode = Integer.parseInt(parts[n - 2]);
        int processingTime = Integer.parseInt(parts[n - 1]);

        return new LogEntry(ip, dateTime, returnCode, processingTime);
    }
}

class ProcessingLogData {

    private final ParseLogData parser = new ParseLogData();

    static class IpInfo {
        long count;
        LocalDateTime earliest;

        IpInfo(long count, LocalDateTime earliest) {
            this.count = count;
            this.earliest = earliest;
        }
    }

    public String[] processLogs(List<String> logLines) {
        Map<String, IpInfo> ipMap = new HashMap<>();
        Map<Integer, Integer> statusMap = new HashMap<>();
        long totalTime = 0L;
        long totalCount = 0L;

        for (String raw : logLines) {
            if (raw == null) continue;
            ParseLogData.LogEntry entry = parser.parse(raw);

            IpInfo info = ipMap.get(entry.ip);
            if (info == null) {
                ipMap.put(entry.ip, new IpInfo(1L, entry.dateTime));
            } else {
                info.count++;
                if (entry.dateTime.isBefore(info.earliest)) {
                    info.earliest = entry.dateTime;
                }
            }

            statusMap.merge(entry.returnCode, 1, Integer::sum);

            totalTime += entry.processingTime;
            totalCount++;
        }

        List<Map.Entry<String, IpInfo>> ipEntries = new ArrayList<>(ipMap.entrySet());
        ipEntries.sort((a, b) -> {
            int cmp = Long.compare(b.getValue().count, a.getValue().count);
            if (cmp != 0) return cmp;
            cmp = a.getValue().earliest.compareTo(b.getValue().earliest);
            if (cmp != 0) return cmp;
            return a.getKey().compareTo(b.getKey());
        });

        int topN = Math.min(3, ipEntries.size());
        StringBuilder topJson = new StringBuilder();
        topJson.append("{\"TopIPs\":{");
        for (int i = 0; i < topN; i++) {
            Map.Entry<String, IpInfo> e = ipEntries.get(i);
            if (i > 0) topJson.append(",");
            topJson.append("\"").append(e.getKey()).append("\":").append(e.getValue().count);
        }
        topJson.append("}}");

        List<Map.Entry<Integer, Integer>> statusEntries = new ArrayList<>(statusMap.entrySet());
        statusEntries.sort((a, b) -> {
            int cmp = Integer.compare(b.getValue(), a.getValue());
            if (cmp != 0) return cmp;
            return Integer.compare(a.getKey(), b.getKey());
        });

        StringBuilder statusJson = new StringBuilder();
        statusJson.append("{\"StatusCodes\":{");
        for (int i = 0; i < statusEntries.size(); i++) {
            Map.Entry<Integer, Integer> e = statusEntries.get(i);
            if (i > 0) statusJson.append(",");
            statusJson.append("\"").append(e.getKey()).append("\":").append(e.getValue());
        }
        statusJson.append("}}");

        long avg = totalCount == 0 ? 0 : (totalTime / totalCount);
        String avgJson = "{\"AverageResponseTimeMs\":" + avg + "}";

        return new String[]{topJson.toString(), statusJson.toString(), avgJson};
    }
}


public class LogAnalysingWithGrouping {
}
