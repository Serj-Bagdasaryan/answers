package com.example.demo.T1.checked;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class CandidateReport {

    static class Rating {
        int s1, s2, s3, s4;

        Rating(int s1, int s2, int s3, int s4) {
            this.s1 = s1;
            this.s2 = s2;
            this.s3 = s3;
            this.s4 = s4;
        }

        void combine(Rating other) {
            this.s1 = Math.max(this.s1, other.s1);
            this.s2 = Math.max(this.s2, other.s2);
            this.s3 = Math.min(this.s3, other.s3);
            this.s4 = Math.min(this.s4, other.s4);
        }

        boolean dominates(Rating other) {
            return this.s1 > other.s1 && this.s2 > other.s2 && this.s3 < other.s3 && this.s4 < other.s4;
        }

        boolean equalsRating(Rating other) {
            return this.s1 == other.s1 && this.s2 == other.s2 && this.s3 == other.s3 && this.s4 == other.s4;
        }
    }

    static class Candidate {
        int id;
        String lastName;
        Rating rating;

        Candidate(int id, String lastName, Rating rating) {
            this.id = id;
            this.lastName = lastName;
            this.rating = rating;
        }
    }

    static final Comparator<Candidate> RATING_COMPARATOR = new Comparator<>() {
        @Override
        public int compare(Candidate a, Candidate b) {
            if (a.rating.dominates(b.rating)) return -1; // a лучше -> раньше
            if (b.rating.dominates(a.rating)) return 1;  // b лучше -> раньше
            // равенство или несравнимость -> по возрастанию ID
            return Integer.compare(a.id, b.id);
        }
    };
}

class ProcessingCandidatesRatings {

    String processingInputLines(List<String> inputLines) {
        if (inputLines == null || inputLines.isEmpty()) {
            return "[]";
        }

        Map<Integer, CandidateReport.Candidate> map = new HashMap<>();

        for (String line : inputLines) {
            if (line == null || line.isEmpty()) continue;
            // формат гарантирован: нет пробелов, разделитель ';'
            String[] parts = line.split(";");
            if (parts.length != 6) continue; // защита на случай некорректного входа

            int id;
            try {
                id = Integer.parseInt(parts[0]);
            } catch (NumberFormatException ex) {
                continue;
            }
            String lastName = parts[1];
            int s1 = Integer.parseInt(parts[2]);
            int s2 = Integer.parseInt(parts[3]);
            int s3 = Integer.parseInt(parts[4]);
            int s4 = Integer.parseInt(parts[5]);

            CandidateReport.Rating newRating = new CandidateReport.Rating(s1, s2, s3, s4);

            CandidateReport.Candidate existing = map.get(id);
            if (existing == null) {
                map.put(id, new CandidateReport.Candidate(id, lastName, newRating));
            } else {
                existing.rating.combine(newRating);
            }
        }

        List<CandidateReport.Candidate> candidates = new ArrayList<>(map.values());
        Collections.sort(candidates, CandidateReport.RATING_COMPARATOR);

        int take = Math.min(3, candidates.size());
        StringBuilder sb = new StringBuilder();
        sb.append("[");

        for (int i = 0; i < take; i++) {
            CandidateReport.Candidate c = candidates.get(i);
            if (i > 0) sb.append(",");
            sb.append("{");
            sb.append("\"ID\":\"").append(c.id).append("\",");
            sb.append("\"LastName\":\"").append(escapeJson(c.lastName)).append("\",");
            sb.append("\"s1\":").append(c.rating.s1).append(",");
            sb.append("\"s2\":").append(c.rating.s2).append(",");
            sb.append("\"s3\":").append(c.rating.s3).append(",");
            sb.append("\"s4\":").append(c.rating.s4);
            sb.append("}");
        }

        sb.append("]");
        return sb.toString();
    }

    private String escapeJson(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}


public class HardRating {
}
