package com.example.demo.others;


import java.nio.ByteBuffer;

public class Test {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put((byte) 5);
        buffer.clear();
        System.out.println(buffer.position());
    }
}
