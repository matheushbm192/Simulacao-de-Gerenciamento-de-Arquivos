package org.example;

import java.util.ArrayList;
import java.util.Date;

public class Diretorio  implements Comandos {
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

    //uma árvore (temos que criar todas as estruturas de árvores)
    private ArrayList<Comandos> diretoriosArquivos = new ArrayList<>();

    public Diretorio(String nomeDiretorio){
        this.nome = nomeDiretorio;
    }

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

    public double getTamanhoBytes() {
        return tamanhoBytes;
    }

    public void setTamanhoBytes(double tamanhoBytes) {
        this.tamanhoBytes = tamanhoBytes;
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

    public ArrayList<Comandos> getDiretoriosArquivos() {
        return diretoriosArquivos;
    }

    public void setDiretoriosArquivos(ArrayList<Comandos> diretoriosArquivos) {
        this.diretoriosArquivos = diretoriosArquivos;
    }

    private Comandos buscarDiretorioArquivo(String nomeDiretorioArquivo){
        for(Comandos diretorioArquivo : diretoriosArquivos ){
            if(nomeDiretorioArquivo.equals(diretorioArquivo.getNome())){
                return diretorioArquivo;
            }
        }
        return null;
    }

    @Override
    public void mkdir(String nomeDiretorio){
        diretoriosArquivos.add(new Diretorio(nomeDiretorio));
    }

    @Override
    public void tree() {
        //this == atual (terminal)
        imprimirTree(this, 0);
    }

    @Override
    public void touch(String nomeArquivo) {
        diretoriosArquivos.add(new Arquivo(nomeArquivo));
    }

    private void imprimirTree(Diretorio dir, int nivel) {


        for (int i = 0; i < nivel; i++) {
            System.out.print("   ");
        }

        System.out.println("|-- " + dir.nome);

        for (Comandos c : dir.diretoriosArquivos) {
            if (c instanceof Diretorio) {
                imprimirTree((Diretorio) c, nivel + 1);
            }
        }
    }
    @Override
    public void rmdir(String nomeDiretorio) {
        Comandos diretorioArquivo = buscarDiretorioArquivo(nomeDiretorio);

        if(diretorioArquivo instanceof Diretorio diretorio){

            if(diretorio.diretoriosArquivos.isEmpty() ){
                diretoriosArquivos.remove(diretorio);

            }
        }
    }

    @Override
    public void rename(String nomeDiretorioArquivo, String novoNomeDiretorioArquivo) {
         Comandos diretorioArquivo = buscarDiretorioArquivo(nomeDiretorioArquivo);
         if(diretorioArquivo != null){
             diretorioArquivo.setNome(novoNomeDiretorioArquivo);
         }
    }

    //todo:rever implemntação
    @Override
    public void echo(String texto, String atributo, String nomeArquivo) {
        Comandos diretorioArquivo = buscarDiretorioArquivo(nomeArquivo);

        if(diretorioArquivo instanceof Arquivo arquivo){
            if (">>".equals(atributo)){
                arquivo.textoIncrement(texto);
            }else if(">".equals(atributo)){
                //todo: codigo paola
            }

        }
    }

    @Override
    public void cat(String nomeArquivo) {
        Comandos diretorioArquivo = buscarDiretorioArquivo(nomeArquivo);

        if (diretorioArquivo instanceof Arquivo arquivo){
            System.out.println(arquivo.getNome());
        }
    }

    @Override
    public void rm(String nomeDiretorioArquivo) {
        Comandos diretorioArquivo = buscarDiretorioArquivo(nomeDiretorioArquivo);

        if(diretorioArquivo != null){
                diretoriosArquivos.remove(diretorioArquivo);

        }
    }

    @Override
    public void head(String nomeArquivo, String numeroDeLinhas) {
        Comandos diretorioArquivo = buscarDiretorioArquivo(nomeArquivo);
        if(diretorioArquivo instanceof Arquivo arquivo){
            arquivo.head(nomeArquivo,numeroDeLinhas);
        }
    }

    @Override
    public void tail(String nomeArquivo, String numeroDeLinhas) {
        Comandos diretorioArquivo = buscarDiretorioArquivo(nomeArquivo);
        if(diretorioArquivo instanceof Arquivo arquivo){
            arquivo.tail(nomeArquivo,numeroDeLinhas);
        }
    }

    @Override
    public void wc(String nomeArquivo) {
        Comandos diretorioArquivo = buscarDiretorioArquivo(nomeArquivo);
        if(diretorioArquivo instanceof Arquivo arquivo){
            arquivo.wc(nomeArquivo);
        }
    }


}
