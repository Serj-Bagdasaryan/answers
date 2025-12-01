package com.example.demo.T2;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

class ClientData implements Comparable<ClientData> {
    int refundAmount;
    int refundSum;
    int clientID;

    @Override
    public int compareTo(ClientData o) {
        int cmp = -Integer.compare(this.refundSum, o.refundSum);
        if (cmp != 0) return cmp;
        cmp = -Integer.compare(this.refundAmount, o.refundAmount);
        if (cmp != 0) return cmp;
        return Integer.compare(this.clientID, o.clientID);
    }


    public ClientData plus(int refundSum) {
        this.refundAmount++;
        this.refundSum += refundSum;
        return this;
    }

    public ClientData(int clientID) {
        this.clientID = clientID;
        this.refundSum = 0;
        this.refundAmount = 0;
    }
}

class ProcessingClientData {
    public List<String> processInputLines(List<String> inputLines) {
        Iterator<String> iterator = inputLines.iterator();
        String firstLine = iterator.next();
        HashMap<String, ClientData> map = new HashMap<>();
        while (iterator.hasNext()) {
            String[] arr = iterator.next().split(":");
            LocalDate date = LocalDate.parse(arr[4], DateTimeFormatter.ofPattern("dd-MM-uuuu"));
            if (arr[2].equals("accepted") ||
                    (date.getMonth().ordinal() + 1 != Integer.parseInt(firstLine.substring(0, firstLine.indexOf(" ")))) ) {
                continue;
            } else {
                map.put(arr[0], map.getOrDefault(arr[0], new ClientData(Integer.parseInt(arr[0]))).plus(Integer.parseInt(arr[3])));
            }
        }
        if (map.size() < Integer.parseInt(firstLine.substring(firstLine.indexOf(" ")).trim()) ) {
            return new ArrayList<>(List.of("none"));
        } else {
            Set<ClientData> resultSet = new TreeSet<>();
            for (Map.Entry<String, ClientData> entry : map.entrySet()) {
                resultSet.add(entry.getValue());
            }
            return resultSet.stream().limit(Integer.parseInt(firstLine.substring(firstLine.indexOf(" ")).trim()) )
                    .map(clientData -> clientData.clientID + ";" + clientData.refundSum + ";" + clientData.refundAmount)
                    .toList();
        }
    }
}

class Main1{
    public static void main(String[] args) {
        ProcessingClientData ProcessingClientData = new ProcessingClientData();
        ProcessingClientData.processInputLines(List.of("5 2",
                "10001:20001:accepted:15000:01-05-2024",
                "10002:20002:rejected:25000:15-05-2024",
                "10001:20003:rejected:35000:20-05-2024",
                "10003:20004:rejected:10000:10-05-2024",
                "10002:20005:rejected:15000:25-05-2024")).forEach(System.out::println);
    }
}
