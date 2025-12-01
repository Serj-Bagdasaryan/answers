package com.example.demo.T4.checked;

import java.util.*;
import java.util.stream.Collectors;

class RequestProcessingSystem {

    private static class Req {
        final int id;
        final int dur;
        Req(int id, int dur) { this.id = id; this.dur = dur; }
    }

    private static class Assigned {
        final int id;
        final long finish;
        Assigned(int id, long finish) { this.id = id; this.finish = finish; }
    }

    public List<String> tasksModelling(List<String> inputRequests) {
        if (inputRequests == null) return Collections.singletonList("none");

        List<Req> valid = new ArrayList<>();
        for (String raw : inputRequests) {
            if (raw == null) continue;
            String s = raw.trim();
            if (s.isEmpty()) continue;
            String[] parts = s.split(";");
            if (parts.length != 2) continue;
            String sid = parts[0].trim();
            String sdur = parts[1].trim();
            int id, dur;
            try {
                id = Integer.parseInt(sid);
                dur = Integer.parseInt(sdur);
            } catch (NumberFormatException ex) {
                continue;
            }
            if (id < 10000 || id > 99999) continue;
            if (dur < 1 || dur > 59) continue;
            valid.add(new Req(id, dur));
        }

        if (valid.isEmpty()) return Collections.singletonList("none");

        ArrayDeque<Assigned>[] queues = new ArrayDeque[3];
        List<Assigned>[] allAssigned = new ArrayList[3];
        for (int i = 0; i < 3; i++) {
            queues[i] = new ArrayDeque<>();
            allAssigned[i] = new ArrayList<>();
        }

        List<Integer> unaccepted = new ArrayList<>();
        int n = valid.size();
        for (int i = 0; i < n; i++) {
            if (i >= 60) {
                unaccepted.add(valid.get(i).id);
                continue;
            }
            Req r = valid.get(i);
            long t = i;

            for (int s = 0; s < 3; s++) {
                while (!queues[s].isEmpty() && queues[s].peekFirst().finish <= t) {
                    queues[s].pollFirst();
                }
            }

            int pick = 0;
            int minLen = queues[0].size();
            for (int s = 1; s < 3; s++) {
                int len = queues[s].size();
                if (len < minLen) {
                    minLen = len;
                    pick = s;
                }
            }

            long lastFinish = queues[pick].isEmpty() ? t : queues[pick].peekLast().finish;
            long start = Math.max(lastFinish, t);
            long finish = start + r.dur;
            Assigned a = new Assigned(r.id, finish);
            queues[pick].offerLast(a);
            allAssigned[pick].add(a);
        }

        List<Integer> unprocessed = new ArrayList<>();
        for (int s = 0; s < 3; s++) {
            for (Assigned a : allAssigned[s]) {
                if (a.finish > 60L) unprocessed.add(a.id);
            }
        }
        unprocessed.addAll(unaccepted);

        if (unprocessed.isEmpty()) return Collections.singletonList("none");

        Collections.sort(unprocessed);
        return unprocessed.stream().map(String::valueOf).collect(Collectors.toList());
    }
}


public class RequestFlowProcessing {
}
