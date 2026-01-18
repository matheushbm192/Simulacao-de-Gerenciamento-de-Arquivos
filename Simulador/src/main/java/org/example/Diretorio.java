package org.example;

import java.util.ArrayList;
import java.util.Date;

public class Diretorio  implements Comandos {
    private String nome;
    private String tipo;
    private double tamanhoBytes;
    private Date dataCriacao;
    private Date dataUltimaModificacao;
    private boolean leitura;
    private boolean escrita;
    private boolean execucao;
    private String proprietario;
    private String caminho;
    private long inode;
    private String grupo;
    private String permissoesOctal;   // ex: 0644
    private String permissoesSimbolicas; // ex: -rw-r--r--
    private Date dataUltimoAcesso;
    private Date dataAlteracaoMetadados;
    private final int bloco = 512;
    private ArrayList<Comandos> diretoriosArquivos = new ArrayList<>();

    public Diretorio(String nomeDiretorio,int inode){
        this.nome = nomeDiretorio;
        this.inode = inode;
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

    public double getTamanhoBytes() {
        double total = 0;

        for (Comandos diretorioArquivo : diretoriosArquivos) {
            total += diretorioArquivo.getTamanhoBytes();
        }

        return total;
    }

    private void setTamanhoBytes(){

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
    public void mkdir(String nomeDiretorio,int inode){
        diretoriosArquivos.add(new Diretorio(nomeDiretorio,inode));
    }

    @Override
    public void tree() {
        //this == atual (terminal)
        imprimirTree(this, 0);

    }

    @Override
    public void touch(String nomeArquivo, int inode) {
        diretoriosArquivos.add(new Arquivo(nomeArquivo,inode));
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
    public void echo(String texto, String atributo, String nomeArquivo,int inode) {
        Comandos diretorioArquivo = buscarDiretorioArquivo(nomeArquivo);

        if(diretorioArquivo instanceof Arquivo arquivo){
            if (">>".equals(atributo)){
                arquivo.textoIncrement(texto);

            }else if(">".equals(atributo)){
                arquivo.escrever(texto);

            }
        }else{
            //se não existir, cria
            Arquivo novo = new Arquivo(nomeArquivo,inode);
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
    public void find(String nomeDiretorioArquivo) {
        buscarRecursivo(this, nomeDiretorioArquivo, this.getNome());
    }

    @Override
    public void grep(String termo) {

    }

    @Override
    public void stat(String nomeDiretorioArquivo) {
        Diretorio diretorio;
        if(this.nome.equals(nomeDiretorioArquivo)){
            diretorio = this;
        }else{
            Comandos diretorioArquivo = buscarDiretorioArquivo(nomeDiretorioArquivo);
            if (diretorioArquivo instanceof Diretorio){
                diretorio = (Diretorio) diretorioArquivo;
            }else if(diretorioArquivo instanceof Arquivo arquivo){
                arquivo.stat(nomeDiretorioArquivo);
                return;
            }else {
                System.out.println("O arquivo | diretorio não foi encontrado!");
                return;
            }
            printStat();

        }


    }

    @Override
    public void du(String nomeDiretorio) {
        Comandos diretorioArquivo = buscarDiretorioArquivo(nomeDiretorio);
        if(diretorioArquivo instanceof Diretorio){
            int quantidadeBlocos = (int) getTamanhoBytes() / bloco;
            System.out.println(quantidadeBlocos + "/"+nomeDiretorio);
        } else if (diretorioArquivo instanceof Arquivo arquivo) {
            arquivo.du(nomeDiretorio);
        }else{
            System.out.println("Diretorio não foi encontrado");
        }

    }

    private void buscarRecursivo(Comandos atual, String nomeProcurado, String caminhoAtual) {
        if (atual.getNome().equals(nomeProcurado)) {
            System.out.println(caminhoAtual);
        }

        for (Comandos c : atual.getFilhos()) {
            buscarRecursivo(c, nomeProcurado, caminhoAtual + "\\" + c.getNome());
        }
    }

    //todo: setPermissoes precisa definir as permissoes em octal tambem
    public void setPermissoes(String permissaoSimbolica) {

        // Exemplo esperado: drwxr-xr-x
        this.permissoesSimbolicas = permissaoSimbolica;

        // Remove o tipo (d, -, l)
        String perms = permissaoSimbolica.substring(1);

        int dono = calcularValor(perms.substring(0, 3));
        int grupo = calcularValor(perms.substring(3, 6));
        int outros = calcularValor(perms.substring(6, 9));
        this.grupo = perms.substring(3, 6);
        this.permissoesOctal = "0" + dono + grupo + outros;


        this.leitura = perms.charAt(0) == 'r';
        this.escrita = perms.charAt(1) == 'w';
        this.execucao = perms.charAt(2) == 'x';
    }

    // Converte rwx → número (4,2,1)
    private int calcularValor(String permissao) {
        int valor = 0;

        if (permissao.charAt(0) == 'r') valor += 4;
        if (permissao.charAt(1) == 'w') valor += 2;
        if (permissao.charAt(2) == 'x') valor += 1;

        return valor;
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




    private void printStat() {
    this.tamanhoBytes = getTamanhoBytes();
        System.out.println("  Diretorio: " + nome);

        System.out.printf(
                "  Tamanho: %-15.0f Blocos: %-10d Bloco IO: %-6d %s%n",
                tamanhoBytes,
                (int) Math.ceil(tamanhoBytes / bloco),
                4096,
                tipo
        );

        System.out.printf(
                " Inode: %-12d Links: %d%n",
                inode,
                diretoriosArquivos.size() + 2
        );

        //TODO: Ajustar Permissão
        System.out.printf(
                "Acesso: (%s/%s)  UID: (1000/%s)   GID: (1000/%s)%n",
                permissoesOctal,
                permissoesSimbolicas,
                proprietario,
                grupo
        );

        System.out.println(
                "Acesso: " + dataUltimoAcesso
        );

        System.out.println(
                "Modificação: " + dataUltimaModificacao
        );

        System.out.println(
                "Alteração: " + dataAlteracaoMetadados
        );

        System.out.println(
                " Criação: " + dataCriacao
        );
    }



}
