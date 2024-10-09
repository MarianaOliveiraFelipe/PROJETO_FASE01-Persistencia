package com.example;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class CompactadorCSV {

    public static void compactarCSV(String arquivoCSV) {
        String ArquivoZip = "exercicios.zip";  //
        try {
            FileOutputStream fos = new FileOutputStream(ArquivoZip);
            ZipOutputStream zipOut = new ZipOutputStream(fos);
            FileInputStream fis = new FileInputStream(arquivoCSV);
            ZipEntry zipEntry = new ZipEntry(arquivoCSV);
            zipOut.putNextEntry(zipEntry);
            byte[] bytes = new byte[1024];
            int tamanho;
            while ((tamanho = fis.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, tamanho);
            }
            zipOut.close();
            fis.close();
            System.out.println("Arquivo CSV compactado com sucesso.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
