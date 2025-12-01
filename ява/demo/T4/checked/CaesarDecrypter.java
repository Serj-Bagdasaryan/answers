package com.example.demo.T4.checked;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class CaesarCipherDecoder {
    private static final String ALPHABET = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";

    public String decode(String encryptedText, int shift) {
        StringBuilder decoded = new StringBuilder();

        for (char c : encryptedText.toCharArray()) {
            if (c == ' ') {
                decoded.append(' ');
                continue;
            }

            int index = ALPHABET.indexOf(c);
            if (index == -1) {
                decoded.append(c);
                continue;
            }

            int newIndex = (index - shift) % ALPHABET.length();
            if (newIndex < 0) {
                newIndex += ALPHABET.length();
            }

            decoded.append(ALPHABET.charAt(newIndex));
        }

        return decoded.toString();
    }
}



public class CaesarDecrypter {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String encryptedText = br.readLine();
        String shiftLine = br.readLine();
        if (encryptedText == null) encryptedText = "";
        if (shiftLine == null) shiftLine = "0";

        encryptedText = encryptedText.trim();
        int shift;
        try {
            shift = Integer.parseInt(shiftLine.trim());
        } catch (NumberFormatException e) {
            shift = 0;
        }

        System.out.println(decrypt(encryptedText, shift));
    }
}



