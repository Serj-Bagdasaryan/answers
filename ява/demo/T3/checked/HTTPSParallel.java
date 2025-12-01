package com.example.demo.T3.checked;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

class MockHttpClient {
    private final Map<UUID, Boolean> testData = new HashMap<>();

    public MockHttpClient(List<String> inputLines) {
        for (String line : inputLines) {
            if (line == null) continue;
            String trimmed = line.trim();
            if (trimmed.isEmpty()) continue;
            String[] parts = trimmed.split("\\s+");
            if (parts.length < 2) continue;
            try {
                UUID id = UUID.fromString(parts[0]);
                boolean ok = "OK".equalsIgnoreCase(parts[1]);
                testData.put(id, ok);
            } catch (IllegalArgumentException ignored) {
            }
        }
    }

    public CompletableFuture<String> getResourceAsync(UUID id, AtomicInteger counter, int delayMs) {
        try {
            Thread.sleep(Math.max(0, delayMs));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            CompletableFuture<String> cf = new CompletableFuture<>();
            cf.completeExceptionally(e);
            return cf;
        }

        counter.incrementAndGet();

        Boolean ok = testData.get(id);
        if (ok != null && ok) {
            return CompletableFuture.completedFuture(id.toString());
        } else {
            CompletableFuture<String> cf = new CompletableFuture<>();
            cf.completeExceptionally(new RuntimeException("request failed"));
            return cf;
        }
    }
}

class HttpModel {
    private final MockHttpClient mockHttpClient;

    public HttpModel(MockHttpClient mockHttpClient) {
        this.mockHttpClient = mockHttpClient;
    }

    public List<String> processingInputLines(int degreeOfParallelism, List<String> inputLines) {
        if (degreeOfParallelism < 1) {
            degreeOfParallelism = 1;
        }

        ExecutorService executor = Executors.newFixedThreadPool(degreeOfParallelism);
        try {
            AtomicInteger counter = new AtomicInteger(0);
            List<CompletableFuture<String>> futures = new ArrayList<>(inputLines.size());

            for (int i = 0; i < inputLines.size(); i++) {
                final int idx = i;
                final String line = inputLines.get(i).trim();
                String[] parts = line.split("\\s+");
                final UUID id;
                try {
                    id = UUID.fromString(parts[0]);
                } catch (IllegalArgumentException ex) {
                    CompletableFuture<String> bad = CompletableFuture.completedFuture("none");
                    futures.add(bad);
                    continue;
                }

                final int delayMs = 50 + (idx * 73 % 200);

                CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> {
                    CompletableFuture<String> inner = mockHttpClient.getResourceAsync(id, counter, delayMs);
                    return inner.exceptionally(ex -> "none").join();
                }, executor).exceptionally(ex -> "none");

                futures.add(cf);
            }

            CompletableFuture<Void> all = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
            all.join();

            List<String> results = new ArrayList<>(futures.size());
            for (CompletableFuture<String> f : futures) {
                String r = f.join();
                results.add("none".equals(r) ? "none" : r);
            }

            return results;
        } finally {
            executor.shutdownNow();
        }
    }
}


public class HTTPSParallel {
}
