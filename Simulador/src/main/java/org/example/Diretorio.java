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

    @Override
    public ArrayList<Comandos> getFilhos(){
        return diretoriosArquivos;
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

            for (int i = 0; i <= nivel; i++) {
                System.out.print("   ");
            }

            System.out.println("|-- " + c.getNome());

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
                arquivo.escrever(texto);

            }
        }else{
            //se não existir, cria
            Arquivo novo = new Arquivo(nomeArquivo);
            novo.escrever(texto);
            diretoriosArquivos.add(novo);
        }
    }

    @Override
    public void cat(String nomeArquivo) {
        Comandos diretorioArquivo = buscarDiretorioArquivo(nomeArquivo);

        if (diretorioArquivo instanceof Arquivo arquivo){
            arquivo.cat(nomeArquivo);
        }
    }

    @Override
    public void rm(String nomeDiretorioArquivo) {
        Comandos diretorioArquivo = buscarDiretorioArquivo(nomeDiretorioArquivo);

        if (diretorioArquivo instanceof Diretorio dir) {
            if (dir.getFilhos().isEmpty()) {
                diretoriosArquivos.remove(dir);
            } else {
                System.out.println("rm: diretório não está vazio");
            }
        } else if (diretorioArquivo != null) {
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

    @Override
    public void find(String nomeProcurado) {
        buscarRecursivo(this, nomeProcurado, this.getNome());
    }

    @Override
    public void grep(String termo) {

    }

    private void buscarRecursivo(Comandos atual, String nomeProcurado, String caminhoAtual) {
        if (atual.getNome().equals(nomeProcurado)) {
            System.out.println(caminhoAtual);
        }

        for (Comandos c : atual.getFilhos()) {
            buscarRecursivo(c, nomeProcurado, caminhoAtual + "\\" + c.getNome());
        }
    }

    public void setPermissoes(String permissao) {
        leitura = permissao.contains("r");
        escrita = permissao.contains("w");
        execucao = permissao.contains("x");
    }

    public String detalhes() {

        String tipo = "d";
        String permissoes =
                (leitura ? "r" : "-") +
                        (escrita ? "w" : "-") +
                        (execucao ? "x" : "-");

        return String.format(
                "%s%s  %s  %d  %s",
                tipo,
                permissoes,
                proprietario,
                diretoriosArquivos.size(),
                nome
        );
    }

    // todo: usar esse método depois onde os comandos são inválidos para diretório, vai facilitar
    private void comandoInvalido(String comando) {
        System.out.println(comando + ": operação inválida para diretório");
    }





}
