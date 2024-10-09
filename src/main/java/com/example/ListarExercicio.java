package com.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ListarExercicio {   
    private List<Exercicio> exercicios; 
    private String arquivoCSV = "C:\\Users\\Mariana\\PROJETO\\src\\main\\java\\com\\example\\exercicios.csv";  //exercicio.csv

    public ListarExercicio(){ 
        exercicios = new ArrayList<>();  
        carregarExerciciosDoCSV();  
    }

    public void adicionarExercicio(Exercicio exercicio) throws IOException {
        if (exercicioExiste(exercicio.getId())) {
            throw new IllegalArgumentException("ID já existe");
        }
        exercicios.add(exercicio); 
        salvarExercicioNoCSV(exercicio);
    }

    private boolean exercicioExiste(int id) {
        return exercicios.stream().anyMatch(exercicio -> exercicio.getId() == id);
    }

    public List<Exercicio> getExercicios(){  
        return exercicios; 
    }

    public int getQuantidadeExercicios(){ 
        return exercicios.size(); 
    }

    private void carregarExerciciosDoCSV(){ 
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivoCSV))) { 
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(",");
                if (dados.length == 7) {
                    int id = Integer.parseInt(dados[0]);
                    String nome = dados[1];
                    String categoria = dados[2];
                    String dificuldade = dados[3];
                    int duracao = Integer.parseInt(dados[4]);
                    int calorias = Integer.parseInt(dados[5]);
                    String descricao = dados[6];
                    Exercicio exercicio = new Exercicio(id, nome, categoria, dificuldade, duracao, calorias, descricao);
                    exercicios.add(exercicio);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void salvarExercicioNoCSV(Exercicio exercicio) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivoCSV, true))) {
            String linha = exercicio.getId() + "," + exercicio.getNome() + "," + exercicio.getCategoria() + ","
                    + exercicio.getDificuldade() + "," + exercicio.getDuracao() + "," + exercicio.getCalorias() + "," + exercicio.getDescricao();
            writer.write(linha);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("Erro ao salvar exercício no CSV");
        }
    }

    public String getArquivoCSV(){
        return arquivoCSV;
    }
}

