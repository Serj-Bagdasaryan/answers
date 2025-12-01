package com.example.demo.T2.checked;

import java.util.*;
import java.util.Map.Entry;

class LogParser {
    public static List<String> processingInputLines(List<String> inputLines) {
        Map<String, Integer> counts = new HashMap<>();
        if (inputLines == null || inputLines.isEmpty()) {
            return new ArrayList<>();
        }

        for (String line : inputLines) {
            if (line == null || line.length() == 0) continue;
            int lastSep = line.lastIndexOf("::");
            if (lastSep == -1) continue;
            int actionStart = lastSep + 2;
            if (actionStart >= line.length()) continue;
            String action = line.substring(actionStart).trim(); // trim для безопасности
            if (action.isEmpty()) continue;
            counts.put(action, counts.getOrDefault(action, 0) + 1);
        }

        List<Entry<String, Integer>> entries = new ArrayList<>(counts.entrySet());
        entries.sort((a, b) -> {
            int cmp = Integer.compare(b.getValue(), a.getValue()); // по убыванию частоты
            if (cmp != 0) return cmp;
            return a.getKey().compareTo(b.getKey()); // при равенстве — по алфавиту
        });

        List<String> result = new ArrayList<>(entries.size());
        for (Entry<String, Integer> e : entries) {
            result.add(e.getKey() + " - " + e.getValue());
        }
        return result;
    }
}

public class OptimizeLogParser {
    // Можно добавить main для локального теста
    public static void main(String[] args) {
        List<String> sample = Arrays.asList(
                /*"192.168.1.1::user1::file.txt::Read",
                "192.168.1.2::user2::doc.docx::Write",
                "192.168.1.3::user3::image.png::Write",
                "192.168.1.4::user4::data.csv::Write",
                "192.168.1.5::user5::script.py::Read",
                "10.0.0.1::admin::setup.sh::Delete"*/

                /*"192.168.1.1::user1::file.txt::Read",
                "192.168.1.2::user2::doc.docx::Write",
                "192.168.1.3::user3::image.png::Read",
                "192.168.1.4::user4::data.csv::Write",
                "192.168.1.5::user5::script.py::View",
                "192.168.1.6::user6::test.txt::Copy",
                "192.168.1.7::user7::file.txt::Read",
                "192.168.1.8::user8::doc.docx::Write",
                "192.168.1.9::user9::image.png::View",
                "192.168.1.10::user10::data.csv::Copy",
                "192.168.1.11::user11::script.py::Read",
                "192.168.1.12::user12::test.txt::Write"*/

                "192.168.1.1::user1::file.txt::Read",
                "192.168.1.2::user2::doc.docx::Read",
                "192.168.1.3::user3::image.png::Read",
                "192.168.1.4::user4::data.csv::Read",
                "192.168.1.5::user5::script.py::Read",
                "192.168.1.6::user6::test.txt::Read",
                "192.168.1.7::user7::file.txt::Read",
                "192.168.1.8::user8::doc.docx::Read",
                "192.168.1.9::user9::image.png::NewAct"

                /*"192.168.1.1::user1::file.txt::Read",
                "192.168.1.2::user2::doc.docx::Custom1",
                "192.168.1.3::user3::image.png::Read",
                "192.168.1.4::user4::data.csv::Custom2",
                "192.168.1.5::user5::script.py::Read",
                "192.168.1.6::user6::test.txt::Custom1",
                "192.168.1.7::user7::file.txt::Read",
                "192.168.1.8::user8::doc.docx::Custom2",
                "192.168.1.9::user9::image.png::Read",
                "192.168.1.10::user10::data.csv::Custom1",
                "192.168.1.11::user11::script.py::Read",
                "192.168.1.12::user12::test.txt::Custom2"*/

                /*"192.168.1.1::user1::file.txt::Read",
                "192.168.1.2::user2::doc.docx::Write",
                "192.168.1.3::user3::image.png::Write",
                "192.168.1.4::user4::data.csv::Write",
                "192.168.1.5::user5::script.py::Read",
                "192.168.1.6::user6::test.txt::Write"*/
        );
        List<String> out = LogParser.processingInputLines(sample);
        out.forEach(System.out::println);
        // Ожидаемый вывод:
        // Write - 3
        // Read - 2
    }
}

