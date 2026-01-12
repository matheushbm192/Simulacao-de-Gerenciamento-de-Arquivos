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

    //uma árvore (temos que criar todas as estruturas de árvores)
    private ArrayList<Comandos> diretoriosArquivos = new ArrayList<>();

    public Diretorio(String nomeDiretorio){
        this.nome = nomeDiretorio;
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

}



