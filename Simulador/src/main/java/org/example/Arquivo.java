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
    private String proprietario;
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getTamanho() {
        return tamanho;
    }

    public void setTamanho(double tamanho) {
        this.tamanho = tamanho;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Date getDataUltimaModificacao() {
        return dataUltimaModificacao;
    }

    public void setDataUltimaModificacao(Date dataUltimaModificacao) {
        this.dataUltimaModificacao = dataUltimaModificacao;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public int getContLinhas() {
        return contLinhas;
    }

    public void setContLinhas(int contLinhas) {
        this.contLinhas = contLinhas;
    }

    public int getContPalavras() {
        return contPalavras;
    }

    public void setContPalavras(int contPalavras) {
        this.contPalavras = contPalavras;
    }

    public int getCountLetras() {
        return countLetras;
    }

    public void setCountLetras(int countLetras) {
        this.countLetras = countLetras;
    }

    public boolean isLeitura() {
        return leitura;
    }

    public void setLeitura(boolean leitura) {
        this.leitura = leitura;
    }

    public boolean isEscrita() {
        return escrita;
    }

    public void setEscrita(boolean escrita) {
        this.escrita = escrita;
    }

    public boolean isExecucao() {
        return execucao;
    }

    public void setExecucao(boolean execucao) {
        this.execucao = execucao;
    }

    public String getProprietario() {
        return proprietario;
    }

    public void setProprietario(String proprietario) {
        this.proprietario = proprietario;
    }

    //todo: não gostei dessa implementação, implementar a separação de linha  fuuramente
    public void textoIncrement(String textoIncrement){
        texto.concat(textoIncrement);
    }


    @Override
    public void mkdir(String nomeDiretorio) {
    }

    @Override
    public void rmdir(String nomeDiretorio) {

    }

    @Override
    public void rename(String nomeDiretorioArquivo, String novoNomeDiretorioArquivo) {

    }

    @Override
    public void echo(String texto, String atributo, String nomeArquivo) {

    }

    @Override
    public void cat(String nomeArquivo) {

    }

    @Override
    public void rm(String nomeDiretorioArquivo) {

    }
}
