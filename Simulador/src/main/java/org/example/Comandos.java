package org.example;

public interface Comandos {
    String getNome();
    void setNome(String nomeDiretorioArquivo);
    void mkdir(String nomeDiretorio);
    void rmdir(String nomeDiretorio);
    void rename (String nomeDiretorioArquivo, String novoNomeDiretorioArquivo);
    void echo(String texto,String atributo, String nomeArquivo);
    void cat(String nomeArquivo);
    void rm(String nomeDiretorioArquivo);
    void head(String nomeArquivo, String numeroDeLinhas);
    void tail(String nomeArquivo, String numeroDeLinhas);
    void wc(String nomeArquivo);
}
