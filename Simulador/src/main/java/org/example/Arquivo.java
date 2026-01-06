package org.example;

import java.util.Date;

public class Arquivo implements Comandos {
    private String nome;
    private String tipo;
    private double tamanho;
    private Date dataCriacao;
    private Date dataUltimaModificacao;
    private String texto;
    private int contLinhas;
    private int contPalavras;
    private int countLetras;
    private boolean leitura;
    private boolean escrita;
    private boolean execucao;
    private String prprietario;
    @Override
    public void mkdir(String nomeDiretorio) {
    }
}
