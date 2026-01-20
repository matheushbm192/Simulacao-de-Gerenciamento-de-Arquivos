package org.example;

import java.util.ArrayList;

public interface Comandos {
    void mkdir(String nomeDiretorio);
    void tree();
    void touch(String nomeArquivo);
    void rmdir(String nomeDiretorio);
    void rename (String nomeDiretorioArquivo, String novoNomeDiretorioArquivo);
    void echo(String texto,String atributo, String nomeArquivo);
    void cat(String nomeArquivo);
    void rm(String nomeDiretorioArquivo);
    void head(String nomeArquivo, String numeroDeLinhas);
    void tail(String nomeArquivo, String numeroDeLinhas);
    void wc(String nomeArquivo);
    String getNome();
    void setNome(String nome);
    ArrayList<Comandos> getFilhos();
    void find (String nomeDiretorioArquivo);
    void grep(String termo);
    void stat(String nomeDiretorioArquivo);
    double getTamanhoBytes();
    void du(String nomeDiretorio);
    void zip(String nomeZip, ArrayList<String> itens);
    void unzip(String nomeZip);
    void cp(String nomeOrigem,Diretorio destino);
    void mv(String nomeOrigem,String nomeDestino);
    void diff(String nomeArquivo1,String nomeArquivo2);
    Comandos clonarDiretorioArquivo();
}
