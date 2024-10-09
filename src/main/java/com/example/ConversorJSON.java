package com.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ConversorJSON {

    public static void converterParaJSON(List<Exercicio> exercicios) {  //
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(exercicios);  //

            try(FileWriter jsonWriter = new FileWriter("exercicios.json")){ //
                jsonWriter.write(json);
            }

            System.out.println("Dados convertidos para JSON com sucesso.");
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}

