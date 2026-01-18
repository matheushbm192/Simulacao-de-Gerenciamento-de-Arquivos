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
    void find (String  nomeProcurado);
    void grep(String termo);
}
