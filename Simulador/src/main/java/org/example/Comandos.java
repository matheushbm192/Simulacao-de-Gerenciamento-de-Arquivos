package org.example;

public interface Comandos {
    void mkdir(String nomeDiretorio);
    void tree();
    void touch(String nomeArquivo);
    void rmdir(String nomeDiretorio);
    void rename (String nomeDiretorioArquivo, String novoNomeDiretorioArquivo);
    void echo(String texto, String atributo, String nomeArquivo);
    void cat(String nomeArquivo);
    void rm(String nomeDiretorioArquivo);
    void getNome();
    void setNome(String nome);
}
