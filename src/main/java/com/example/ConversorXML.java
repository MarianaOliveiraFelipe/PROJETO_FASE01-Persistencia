package com.example;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ConversorXML {

    public static void converterParaXML(List<Exercicio> exercicios) {  //
        try {
            XmlMapper xmlMapper = new XmlMapper();
            String xml = xmlMapper.writeValueAsString(exercicios); //

            try(FileWriter xmlWriter = new FileWriter("exercicios.xml")){ //
                xmlWriter.write(xml);
            }

            System.out.println("Dados convertidos para XML com sucesso.");
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}

