package com.example.demo.T1.checked;

import java.util.*;

class Interval {
    // ваш код
}


class SensorData {
    String type;
    String id;
    int value;
    int accuracy;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    public SensorData(String type, String id, int value, int accuracy) {
        this.type = type;
        this.id = id;
        this.value = value;
        this.accuracy = accuracy;
    }

}

class ProcessingSensorData {
    public List<String> processingInputLines(List<String> inputLines) {
        Map<String, SensorData> temp = new HashMap<>();
        Map<String, SensorData> hydro = new HashMap<>();
        int avgRightT, avgLeftT, avgRightH, avgLeftH;

        for (String str : inputLines) {
            String[] line = str.trim().substring(1, str.length() - 1).split(",");
            String type = (line[0].trim().toUpperCase().contains("H")) ? "H" : "T";
            String id = line[1].trim().substring(line[1].indexOf(':') + 1);
            int value = Integer.parseInt(line[2].trim().substring(line[2].indexOf(':') + 1));
            int accuracy = Integer.parseInt(line[3].trim().substring(line[3].indexOf(':') + 1));
            if (type.equals("H")) {
                hydro.put(id, new SensorData(type, id,value, accuracy));
            } else {
                temp.put(id, new SensorData(type, id,value, accuracy));
            }
        }
        List<String> result = new ArrayList<>();
        if (!temp.isEmpty()) {
            avgLeftT = temp.entrySet().stream().map(k -> k.getValue().value - k.getValue().accuracy)
                    .reduce(Integer::sum).get() / (temp.size());
            avgRightT = temp.entrySet().stream().map(k -> k.getValue().value + k.getValue().accuracy)
                    .reduce(Integer::sum).get() / (temp.size());
            result.add(String.format("{\"Type\":\"%s\",\"AverageLeft\":%d,\"AverageRight\":%d,\"Samples\":%d}",
                    "Temp", avgLeftT, avgRightT, temp.size()));
        } else {
            result.add("Temp: none");
        }

        if (!hydro.isEmpty()) {
            avgLeftH = hydro.entrySet().stream().map(k -> k.getValue().value - k.getValue().accuracy)
                    .reduce(Integer::sum).get() / (hydro.size());
            avgRightH = hydro.entrySet().stream().map(k -> k.getValue().value + k.getValue().accuracy)
                    .reduce(Integer::sum).get() / (hydro.size());
            result.add(String.format("{\"Type\":\"%s\",\"AverageLeft\":%d,\"AverageRight\":%d,\"Samples\":%d}",
                    "Humidity", avgLeftH, avgRightH, hydro.size()));
        } else {
            result.add("Humidity: none");
        }
        return result;
    }
}

public class IoTSensors {
    public static void main(String[] args) {

    }
}