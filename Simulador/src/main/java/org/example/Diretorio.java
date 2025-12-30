package org.example;

import java.util.ArrayList;

public class Diretorio implements Comandos {
    private String nome;
    private ArrayList<Comandos> diretoriosArquivos = new ArrayList<>();

    public Diretorio(String nomeDiretorio){
        this.nome = nomeDiretorio;
    }
    @Override
    public void mkdir(String nomeDiretorio){
        diretoriosArquivos.add(new Diretorio(nomeDiretorio));
    }



}
