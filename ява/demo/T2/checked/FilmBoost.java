package com.example.demo.T2.checked;

import java.time.LocalTime;
import java.util.*;

class ProcessingFilmData {

    public static List<String> processInputLines(List<String> inputLines) {
        FilmData filmData = new FilmData(new HashMap<>());
        for (String line : inputLines) {
            filmData.filmMap.computeIfAbsent(
                    line.substring(
                        line.indexOf(";") + 1,
                        line.indexOf(";", line.indexOf(";") + 1)),
                    v -> new ArrayList<>()).add(line);
        }
        Set<String> set = new HashSet<>();
        for (Map.Entry<String, ArrayList<String>> entry : filmData.filmMap.entrySet()) {
            if (isTimeCheating(entry) || isNegativeCheating(entry) || isPositiveCheating(entry)) {
                set.add(entry.getKey() + ";hasRatingBoost");
            }
        }

        Comparator<String> comparator = Comparator.comparing(x-> Integer.parseInt(x.substring(0, x.indexOf(";"))));

            if (set.isEmpty()) {
                return List.of("none");
            } else {
                return new ArrayList<>(set).stream().sorted(comparator).toList();
            }
        }


    private static boolean isTimeCheating(Map.Entry<String, ArrayList<String>> entry) {
        if (entry.getValue().size() >= 5) {
            for (int i = 0; i < entry.getValue().size() - 4; i++) {
                if (LocalTime.parse(entry.getValue().get(i).substring(entry.getValue().get(i).lastIndexOf(";") + 1))
                        .plusMinutes(10L).isBefore(
                                LocalTime.parse(entry.getValue().get(i + 4)
                                        .substring(entry.getValue().get(i).lastIndexOf(";") + 1)))) {
                    return false;
                } else {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isPositiveCheating(Map.Entry<String, ArrayList<String>> entry) {
        if (entry.getValue().size() > 2) {
            for (int i = 0; i < entry.getValue().size(); i++) {
                String current = entry.getValue().get(i);
                String rating = current.substring(
                        current.indexOf(";", current.indexOf(";") + 1) + 1,
                        current.lastIndexOf(";"));
                if (!rating.equals("10") && !rating.equals("9")) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    private static boolean isNegativeCheating(Map.Entry<String, ArrayList<String>> entry) {
        if (entry.getValue().size() > 2) {
            for (int i = 0; i < entry.getValue().size(); i++) {
                String current = entry.getValue().get(i);
                String rating = current.substring(
                        current.indexOf(";", current.indexOf(";") + 1) + 1,
                        current.lastIndexOf(";"));
                if (!rating.equals("0") && !rating.equals("1") && !rating.equals("2")) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}

class FilmData {
    Map<String, ArrayList<String>> filmMap;

    public FilmData(Map<String, ArrayList<String>> filmMap) {
        this.filmMap = filmMap;
    }
}


public class FilmBoost {
    public static void main(String[] args) {
        ProcessingFilmData.processInputLines(List.of(
                /*"10024;20024;9;18:00:30",
                "10024;20024;5;18:01:15",
                "10024;20024;9;18:02:45",

                "10026;20026;1;18:00:30",
                "10026;20026;5;18:01:15",
                "10026;20026;6;18:02:45",
                "10026;20026;7;18:03:30",

                "10025;20025;1;18:03:30",
                "10025;20025;5;18:04:15",
                "10025;20025;1;18:05:45"*/
                "10024;20024;9;18:00:30",
                "10024;20024;9;18:01:15",
                "10024;20024;9;18:02:45",
                "10025;20025;1;18:03:30",
                "10025;20025;1;18:04:15",
                "10025;20025;1;18:05:45",
                "10026;20026;9;18:06:30",
                "10026;20026;9;18:07:15",
                "10027;20027;2;18:08:45",
                "10027;20027;2;18:09:30",
                "10028;20028;0;18:10:15",
                "10028;20028;0;18:11:45"
        )).stream().forEach(System.out::println);
    }
}




