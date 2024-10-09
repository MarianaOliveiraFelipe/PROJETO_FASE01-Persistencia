package com.example;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashSHA256 {

    public static void mostrarHashSHA256(String arquivoCSV) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            FileInputStream fis = new FileInputStream(arquivoCSV);
            byte[] dadosBytes = new byte[1024];
            int lerBytes;
            while ((lerBytes = fis.read(dadosBytes)) != -1) {
                md.update(dadosBytes, 0, lerBytes);
            }
            fis.close();

            byte[] digest = md.digest();
            StringBuilder hexadecimal = new StringBuilder();
            for (byte b : digest) {
                hexadecimal.append(String.format("%02x", b));
            }

            System.out.println("Hash SHA-256 do arquivo CSV: " + hexadecimal.toString());
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
