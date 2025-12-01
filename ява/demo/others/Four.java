package com.example.demo.others;

import java.util.ArrayList;
import java.util.List;

public class Four {
    public static void main(String[] args) {
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            threads.add(new Thread(new ThreadImpl()));
        }
        threads.forEach(Thread::start);
        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException ignored) {
            }
        });
    }

    private static class ThreadImpl implements Runnable {
        private static int counter = 0;
        @Override
        public void run() {
            int locVar = ++counter;
            try {
                Thread.sleep((long) (Math.random() * 20));
            } catch (InterruptedException ignored) {
            }
            System.out.println(locVar);
        }
    }
}
