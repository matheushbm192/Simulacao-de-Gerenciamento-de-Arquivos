package org.example;

import java.util.ArrayList;
import java.util.Date;

public class Diretorio implements Comandos {
    private String nome;
    private String tipo;
    private double tamanho;
    private double tamanhoBytes;
    private Date dataCriacao;
    private Date dataUltimaModificacao;
    private boolean leitura;
    private boolean escrita;
    private boolean execucao;
    private String proprietario;

    private ArrayList<Comandos> diretoriosArquivos = new ArrayList<>();

    public Diretorio(String nomeDiretorio){
        this.nome = nomeDiretorio;
    }
    @Override
    public void mkdir(String nomeDiretorio){
        diretoriosArquivos.add(new Diretorio(nomeDiretorio));
    }



}
