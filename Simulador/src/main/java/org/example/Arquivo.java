package org.example;

import java.util.Date;

public class Arquivo implements Comandos {
    private String nome;
    private String tipo;
    private double tamanho;
    private Date dataCriacao;
    private Date dataUltimaModificacao;
    private String conteudo = "";
    private int contLinhas;
    private int contPalavras;
    private int countLetras;
    private boolean leitura;
    private boolean escrita;
    private boolean execucao;
    private String prprietario;

    //todo: arrumar o construtor pois em sua criação ele precisa ter mais informações
    public Arquivo(String nome) {
        this.nome = nome;
    }

    public void escrever(String texto) {
        this.conteudo = texto;
        this.dataUltimaModificacao = new Date();
    }

    @Override
    public void mkdir(String nomeDiretorio) {
    }

    @Override
    public void tree() {
        //arquivo não tem filhos
        System.out.println(nome);
    }

    @Override
    public void touch(String nomeArquivo) {
        System.out.println("Erro: não é possível criar arquivo dentro de um arquivo.");
    }
}
