package org.example;

import java.util.ArrayList;
import java.util.Date;

public class Diretorio  implements Comandos,Cloneable {
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
    private static long contadorInode = 1;
    private ArrayList<Comandos> diretoriosArquivos = new ArrayList<>();

    public Diretorio(String nomeDiretorio){
        this.nome = nomeDiretorio;
        this.inode = SistemaOperacional.getInstance().gerarInode();
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

    public void addFilho(Comandos diretorioArquivo){
        this.diretoriosArquivos.add(diretorioArquivo);
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
    public void find(String nomeDiretorioArquivo) {
        buscarRecursivo(this, nomeDiretorioArquivo, this.getNome());
    }

    @Override
    public void grep(String termo) {
        System.out.println("grep: operação inválida para diretório");
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
//todo: adicionar ao destino
    @Override
    public void cp(String nomeOrigem, Diretorio destino) {
        Comandos diretorioArquivoClonado = null;

        if (this.nome.equals(nomeOrigem)) {
             diretorioArquivoClonado = clonarDiretorioArquivo();

        }else{
            Comandos diretorioArquivo = buscarDiretorioArquivo(nomeOrigem);

            if(diretorioArquivo instanceof Diretorio diretorioOrigem) {
                diretorioArquivoClonado = diretorioOrigem.clonarDiretorioArquivo();

            }else if(diretorioArquivo instanceof Arquivo arquivoOrigem){
                diretorioArquivoClonado = arquivoOrigem.clonarDiretorioArquivo();
            }
        }
        if(diretorioArquivoClonado != null){
            destino.addFilho(diretorioArquivoClonado);
        }

    }

    public Comandos clonarDiretorioArquivo(){
        Diretorio clone = new Diretorio(this.nome);

        // Identidade
        clone.tipo = "diretorio";

        // Permissões
        clone.leitura = this.leitura;
        clone.escrita = this.escrita;
        clone.execucao = this.execucao;
        clone.permissoesOctal = this.permissoesOctal;
        clone.permissoesSimbolicas = this.permissoesSimbolicas;

        // Dono
        clone.proprietario = this.proprietario;
        clone.grupo = this.grupo;

        // Datas
        clone.dataCriacao = new Date();
        clone.dataUltimoAcesso = new Date();
        clone.dataAlteracaoMetadados = new Date();
        clone.dataUltimaModificacao = this.dataUltimaModificacao;

        // Inode novo
        clone.inode = SistemaOperacional.getInstance().gerarInode();

        // Clonagem profunda dos filhos (Composite)
        for (Comandos diretorioArquivo : this.diretoriosArquivos) {
            Comandos filhoClone = diretorioArquivo.clonarDiretorioArquivo();
            clone.diretoriosArquivos.add(filhoClone);
        }

        // Tamanho recalculado
        clone.tamanhoBytes = clone.getTamanhoBytes();

        return clone;
    }

    @Override
    public void mv(String nomeOrigem, Diretorio destino) {

        Comandos diretorioArquivoMover = buscarDiretorioArquivo(nomeOrigem);

        if(diretorioArquivoMover != null){
            destino.addFilho(diretorioArquivoMover);
            this.diretoriosArquivos.remove(diretorioArquivoMover);

        }
    }

    @Override
    public void diff(String nomeArquivo1, String nomeArquivo2) {

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
                "Criação: " + dataCriacao
        );
    }

    private long gerarInode() {
        return contadorInode++;
    }

    @Override
    public void zip(String nomeZip, ArrayList<String> itens) {

        if (!nomeZip.endsWith(".zip")) {
            System.out.println("Erro: arquivo zip deve terminar com .zip");
            return;
        }

        Arquivo zip = new Arquivo(nomeZip);

        for (String nomeItem : itens) {

            boolean encontrado = false;

            for (Comandos c : diretoriosArquivos) {

                if (c.getNome().equals(nomeItem)) {
                    zip.adicionarAoZip(c);
                    encontrado = true;
                    break;
                }
            }

            if (!encontrado) {
                System.out.println("Aviso: item '" + nomeItem + "' não encontrado");
            }
        }

        diretoriosArquivos.add(zip);

        System.out.println("Arquivo " + nomeZip + " criado com sucesso.");
    }

    @Override
    public void unzip(String nomeZip) {

        Comandos item = buscarDiretorioArquivo(nomeZip);

        if (!(item instanceof Arquivo)) {
            System.out.println("unzip: arquivo não encontrado");
            return;
        }

        Arquivo zipFile = (Arquivo) item;

        if (!zipFile.isZip()) {
            System.out.println("unzip: " + nomeZip + " não é um arquivo zip");
            return;
        }

        for (Comandos c : zipFile.getConteudoZip()) {
            diretoriosArquivos.add(c);
        }

        System.out.println("Arquivo " + nomeZip + " descompactado com sucesso.");
    }

}
