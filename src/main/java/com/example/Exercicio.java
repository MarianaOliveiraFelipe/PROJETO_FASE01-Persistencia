package com.example;
public class Exercicio {
    
    private int id;
    private String nome;
    private String categoria;
    private String dificuldade;
    private int duracao;
    private int calorias;
    private String descricao;

    
    public Exercicio(int id, String nome, String categoria, String dificuldade, int duracao, int calorias, String descricao){
        this.id = id;
        this.nome = nome;
        this.categoria = categoria;
        this.dificuldade = dificuldade;
        this.duracao = duracao;
        this.calorias = calorias;
        this.descricao = descricao;
    }
     

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getNome(){
        return nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public String getCategoria(){
        return categoria;
    }

    public void setCategoria(String categoria){
        this.categoria = categoria;
    }

    public String getDificuldade(){
        return dificuldade;
    }

    public void setDificuldade(String dificuldade){
        this.dificuldade = dificuldade;
    }

    public int getDuracao(){
        return duracao;
    }

    public void setDuracao(int duracao){
        this.duracao = duracao;
    }

    public int getCalorias(){
        return calorias;
    }

    public void setCalorias(int calorias){
        this.calorias = calorias;
    }

    public String getDescricao(){
        return descricao;
    }

    public void setDescricao(String descricao){
        this.descricao = descricao;
    }
}
