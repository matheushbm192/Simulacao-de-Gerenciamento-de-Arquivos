package org.example;

import java.util.ArrayList;
import java.util.Date;

public class Diretorio  implements Comandos,Cloneable {

    private String nome;
    private String tipo;
    private int tamanhoBytes;
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
    private Diretorio diretorioPai;
    private boolean zip;
    private int numeroDiretorio = 1;
    private int numeroArquivo = 1;



    public Diretorio(String nomeDiretorio, Diretorio diretorioPai){
        this.nome = nomeDiretorio.trim();
        this.diretorioPai = diretorioPai;
        this.inode = SistemaOperacional.getInstance().gerarInode();
        this.zip = nome.endsWith(".zip");

        if (zip) {
            this.leitura = false;
            this.escrita = false;
            this.execucao = false;
        } else {
            this.leitura = true;
            this.escrita = true;
            this.execucao = true;
        }

        this.proprietario = "user";
        this.grupo = "user";
        this.tipo = "d";
        this.dataCriacao = new Date();
        this.dataUltimaModificacao = new Date();
        this.dataUltimoAcesso = new Date();
        this.dataAlteracaoMetadados = new Date();
        this.tamanhoBytes = 0;
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

    public int getTamanhoBytes() {
        int total = 0;

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

    public Diretorio getDiretorioPai() {
        return diretorioPai;
    }

    public void setDiretorioPai(Diretorio diretorioPai) {
        this.diretorioPai = diretorioPai;
    }

    public boolean isZip() {
        return zip;
    }

    public  Comandos buscarPorPathParcial(String path, Comandos atual){
        String[] nomesDiretoriosArquivos = path.split("\\\\");
        if(nomesDiretoriosArquivos.length == 1 ){
            if(atual instanceof Diretorio diretorioAtual){
                return  diretorioAtual.buscarDiretorioArquivo(nomesDiretoriosArquivos[0]);
            }
        }else{ //local/janta/documento.txt
            for (int i = 1; i < nomesDiretoriosArquivos.length; i++) {
                if(atual instanceof Diretorio diretorioAtual){
                    atual =  diretorioAtual.buscarDiretorioArquivo(nomesDiretoriosArquivos[i]);
                }
                if(atual != null && atual.getNome().equals(nomesDiretoriosArquivos[nomesDiretoriosArquivos.length-1])){
                    return  atual;
                }
            }
        }
        return null;
    }

    public Comandos buscarDiretorioArquivo(String nomeDiretorioArquivo){
        for(Comandos diretorioArquivo : diretoriosArquivos ){
            if(nomeDiretorioArquivo.equals(diretorioArquivo.getNome())){
                return diretorioArquivo;
            }
        }
        return null;
    }



    @Override
    public void mkdir(String nomeDiretorio){
        Diretorio  diretorio = validarNomeDiretorio(nomeDiretorio,this);
        diretoriosArquivos.add(diretorio);
    }

    public Diretorio validarNomeDiretorio(String nomeDiretorio,Diretorio diretorioRaiz){
        for (Comandos diretorios : diretorioRaiz.diretoriosArquivos){
            if (diretorios.getNome().equals(nomeDiretorio)){
                nomeDiretorio += diretorioRaiz.numeroDiretorio;
                diretorioRaiz.numeroDiretorio++;
                break;
            }
        }

        Diretorio diretorio = new Diretorio(nomeDiretorio,diretorioRaiz);
        return diretorio;
    }

    @Override
    public void touch(String nomeArquivo) {
       Arquivo arquivo =  validarNomeArquivo(nomeArquivo,this);
        diretoriosArquivos.add(arquivo);
    }

    public Arquivo validarNomeArquivo(String nomeArquivo,Diretorio diretorioRaiz) {
        for (Comandos arquivos : diretorioRaiz.diretoriosArquivos){
            if (arquivos.getNome().equals(nomeArquivo)){
                nomeArquivo += diretorioRaiz.numeroArquivo;
                diretorioRaiz.numeroArquivo++;
                break;
            }
        }
        Arquivo arquivo = new Arquivo(nomeArquivo,diretorioRaiz);
        return arquivo;
    }

    @Override
    public void tree() {
        //this == atual (terminal)
        imprimirTree(this, 0);

    }

    private void imprimirTree(Diretorio dir, int nivel) {

    // Indentação do nível atual
        for (int i = 0; i < nivel; i++) {
            System.out.print("   ");
        }

        System.out.println("|-- " + dir.getNome());

        // Percorre filhos
        for (Comandos c : dir.diretoriosArquivos) {

            if (c instanceof Diretorio diretorio) {
                imprimirTree(diretorio, nivel + 1);
            } else {
                for (int i = 0; i <= nivel; i++) {
                    System.out.print("   ");
                }
                System.out.println("|-- " + c.getNome());
            }
        }
    }
    @Override
    public void rmdir(String nomeDiretorio) {
        Comandos diretorioArquivo = buscarPorPathParcial(nomeDiretorio,this);

        if(diretorioArquivo instanceof Diretorio diretorio){

            if(diretorio.diretoriosArquivos.isEmpty() ){
                Diretorio diretorioPai = diretorio.getDiretorioPai();
                diretorioPai.diretoriosArquivos.remove(diretorioArquivo);
            }
        }
    }

    @Override
    public void rename(String nomeDiretorioArquivo, String novoNomeDiretorioArquivo) {
         Comandos diretorioArquivo = buscarPorPathParcial(nomeDiretorioArquivo,this);
         if(diretorioArquivo != null){
             diretorioArquivo.setNome(novoNomeDiretorioArquivo);
         }
    }

    @Override
    public void echo(String texto, String atributo, String nomeArquivo) {

        if (!atributo.equals(">") && !atributo.equals(">>")) {
            System.out.println("echo: operador inválido (use > ou >>)");
            return;
        }

        Comandos diretorioArquivo = buscarPorPathParcial(nomeArquivo,this);

        if (isZip()) {
            System.out.println("echo: não é possível escrever em arquivo zip");
            return;
        }

        // Arquivo já existe
        if (diretorioArquivo instanceof Arquivo arquivo) {

            if (atributo.equals(">>")) {
                arquivo.textoIncrement(texto);
            } else {
                arquivo.escrever(texto);
            }

        }
        // Arquivo NÃO existe → cria
        else {

            Arquivo novo = validarNomeArquivo(nomeArquivo,this);

            // tanto > quanto >> criam arquivo
            novo.escrever(texto);
            diretoriosArquivos.add(novo);
        }

}

    @Override
    public void cat(String nomeArquivo) {

        Comandos diretorioArquivo = buscarPorPathParcial(nomeArquivo,this);

        if (isZip()) {
            System.out.println("cat: não é possível ler arquivo zip");
            return;
        }

        if (diretorioArquivo instanceof Arquivo arquivo){
            arquivo.cat(nomeArquivo);
        }
    }

    @Override
    public void rm(String nomeDiretorioArquivo) {
        Comandos diretorioArquivo = buscarPorPathParcial(nomeDiretorioArquivo,this);

        if(diretorioArquivo != null){
            Diretorio diretorioPai = diretorioArquivo.getDiretorioPai();
            diretorioPai.diretoriosArquivos.remove(diretorioArquivo);
        }



    }

    @Override
    public void head(String nomeArquivo, String numeroDeLinhas) {
        Comandos diretorioArquivo = buscarPorPathParcial(nomeArquivo,this);
        if(diretorioArquivo instanceof Arquivo arquivo){
            arquivo.head(nomeArquivo,numeroDeLinhas);
        }
    }

    @Override
    public void tail(String nomeArquivo, String numeroDeLinhas) {
        Comandos diretorioArquivo = buscarPorPathParcial(nomeArquivo,this);
        if(diretorioArquivo instanceof Arquivo arquivo){
            arquivo.tail(nomeArquivo,numeroDeLinhas);
        }
    }

    @Override
    public void wc(String nomeArquivo) {
        Comandos diretorioArquivo = buscarPorPathParcial(nomeArquivo,this);
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
            Comandos diretorioArquivo = buscarPorPathParcial(nomeDiretorioArquivo,this);
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
        Comandos diretorioArquivo = buscarPorPathParcial(nomeDiretorio,this);
        if(diretorioArquivo instanceof Diretorio){
            int quantidadeBlocos = (int) getTamanhoBytes() / bloco;
            System.out.println(quantidadeBlocos + " blocos");
        } else if (diretorioArquivo instanceof Arquivo arquivo) {
            arquivo.du(nomeDiretorio);
        }else{
            System.out.println("Diretorio não foi encontrado");
        }

    }

    @Override
    public void cp(String nomeOrigem, Diretorio destino) {
        Comandos diretorioArquivoClonado = null;

        if (this.nome.equals(nomeOrigem)) {
             diretorioArquivoClonado = clonarDiretorioArquivo(destino);

        }else{
            Comandos diretorioArquivo = buscarPorPathParcial(nomeOrigem,this);

            if(diretorioArquivo instanceof Diretorio diretorioOrigem) {
                diretorioArquivoClonado = diretorioOrigem.clonarDiretorioArquivo(destino);

            }else if(diretorioArquivo instanceof Arquivo arquivoOrigem){
                diretorioArquivoClonado = arquivoOrigem.clonarDiretorioArquivo(destino);
            }
        }
        if(diretorioArquivoClonado != null){
            destino.addFilho(diretorioArquivoClonado);
        }

    }

    public Comandos clonarDiretorioArquivo(Diretorio destino){
        Diretorio clone = new Diretorio(this.nome, destino);

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
            Comandos filhoClone = diretorioArquivo.clonarDiretorioArquivo(clone);
            clone.diretoriosArquivos.add(filhoClone);
        }

        // Tamanho recalculado
        clone.tamanhoBytes = clone.getTamanhoBytes();

        return clone;
    }

    @Override
    public void mv(String nomeOrigem, Diretorio destino) {

        Comandos diretorioArquivoMover = buscarPorPathParcial(nomeOrigem,this);

        if(diretorioArquivoMover != null){
            destino.addFilho(diretorioArquivoMover);
            this.diretoriosArquivos.remove(diretorioArquivoMover);

        }
    }

    @Override
    public void diff(String nomeaArquivo1, String nomeaArquivo2) {
        Comandos arquivo1 = buscarPorPathParcial(nomeaArquivo1,this);
        Comandos arquivo2 = buscarPorPathParcial(nomeaArquivo2,this);

        if(arquivo1 instanceof Arquivo arquivoPrincipal && arquivo2 instanceof Arquivo arquivoComparado){
            arquivoPrincipal.comparar(arquivoComparado);
        }else{
            System.out.println("Argumento invalidos os arqumentos devem ser arquivos");
        }
    }

    private void buscarRecursivo(Comandos atual, String nomeProcurado, String caminhoAtual) {
        if (atual.getNome().equals(nomeProcurado)) {
            System.out.println(caminhoAtual);
        }

        if(atual instanceof Diretorio diretorioAtual) {
            for (Comandos c : diretorioAtual.getFilhos()) {
                buscarRecursivo(c, nomeProcurado, caminhoAtual + "\\" + c.getNome());
            }
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
                        (execucao ? "x" : "-") +
                        "r--r--"; // grupo + outros (exemplo fixo)

        int links = diretoriosArquivos.size() + 2;
        String grupo = "group";

        String data = new java.text.SimpleDateFormat("yyyy-MM-dd")
                .format(dataUltimaModificacao);

        return String.format(
                "%s%s  %d  %s  %s  %5d  %s  %s",
                tipo,
                permissoes,
                links,
                proprietario,
                grupo,
                (int) tamanhoBytes,
                data,
                nome
        );
    }

    private void comandoInvalido(String comando) {
        System.out.println(comando + ": operação inválida para diretório");
    }


    private void printStat() {
            this.tamanhoBytes = getTamanhoBytes();

            // Tipo (d para diretório, - para arquivo)
            String tipo = (this instanceof Diretorio) ? "d" : "-";

            // Permissões simbólicas
            String permissoesUsuario =
                    (leitura ? "r" : "-") +
                            (escrita ? "w" : "-") +
                            (execucao ? "x" : "-");

            // Permissões fixas para grupo e outros (exemplo)
            String permissoesGrupoOutros = "r--r--";

            String permissoesSimbolicas = permissoesUsuario + permissoesGrupoOutros;

            // Permissões octal
            int octalUsuario = (leitura ? 4 : 0) + (escrita ? 2 : 0) + (execucao ? 1 : 0);
            int octalGrupo = 4;  // r--
            int octalOutros = 4;  // r--
            String permissoesOctal = String.format("%d%d%d", octalUsuario, octalGrupo, octalOutros);

            // Links
            int links = (this instanceof Diretorio) ? diretoriosArquivos.size() + 2 : 1;

            // Grupo fixo
            String grupo = "group";

            // Datas formatadas (ano e hora)
            String dataModificacao = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm").format(dataUltimaModificacao);
            String dataAcesso = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm").format(dataUltimoAcesso);
            String dataAlteracao = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm").format(dataAlteracaoMetadados);
            String dataCriacaoStr = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm").format(dataCriacao);


            System.out.printf(
                    "Tamanho: %-15d Blocos: %-10d Bloco IO: %-6d %s%n",
                    (int) tamanhoBytes,
                    (int) Math.ceil(((double) tamanhoBytes) / ((double) bloco)),
                    4096,
                    tipo
            );

            System.out.printf(
                    "Inode: %-12d Links: %d%n",
                    inode,
                    links
            );

            System.out.printf(
                    "Acesso: (%s/%s)  UID: (1000/%s)   GID: (1000/%s)%n",
                    permissoesOctal,
                    permissoesSimbolicas,
                    proprietario,
                    grupo
            );

            System.out.println("Acesso:      " + dataAcesso);
            System.out.println("Modificação: " + dataModificacao);
            System.out.println("Alteração:   " + dataAlteracao);
            System.out.println("Criação:     " + dataCriacaoStr);
        }




    @Override
    public void zip(String nomeZip, ArrayList<String> itens) {

            if (!nomeZip.endsWith(".zip")) {
                System.out.println("Erro: arquivo zip deve terminar com .zip");
                return;
            }

        Arquivo zip = validarNomeArquivo(nomeZip,this);
        int itensAdicionados = 0;
        int erros = 0;
            for (String nomeItem : itens) {

                boolean encontrado = false;

                for (Comandos c : diretoriosArquivos) {
                    if (c.getNome().equals(nomeItem)) {
                        zip.adicionarAoZip(c);
                        itensAdicionados++;
                        encontrado = true;
                        break;
                    }
                }

                if (!encontrado) {
                    System.out.println("Aviso: item '" + nomeItem + "' não encontrado");
                    erros++;
                }
            }

            // nenhum válido
            if (itensAdicionados == 0) {
                System.out.println("zip: nenhum item válido para compactar");
                return;
            }

            // houve erro em algum item
            if (erros > 0) {
                System.out.println("zip: operação cancelada devido a itens inválidos");
                return;
            }

            diretoriosArquivos.add(zip);
            System.out.println("Arquivo " + nomeZip + " criado com sucesso.");
        }



        @Override
    public void unzip(String nomeZip) {
        Comandos item = buscarPorPathParcial(nomeZip,this);

        if (!(item instanceof Arquivo)) {
            System.out.println("unzip: arquivo não encontrado");
            return;
        }

        Arquivo zipFile = (Arquivo) item;

        if (!zipFile.isZip()) {
            System.out.println("unzip: " + nomeZip + " não é um arquivo zip");
            return;
        }

        // Cria diretório de descompactação
        String nomePasta = nomeZip.replace(".zip", "");
        Diretorio pasta = validarNomeDiretorio(nomePasta,item.getDiretorioPai());
        item.getDiretorioPai().diretoriosArquivos.add(pasta);


        for (Comandos c : zipFile.getConteudoZip()) {
            // Clonar cada item dentro da pasta
            if (c instanceof Arquivo arq) {
                Arquivo novo = (Arquivo) arq.clonarDiretorioArquivo(pasta);
                pasta.addFilho(novo);
            } else if (c instanceof Diretorio dir) {
                Diretorio novoDir = (Diretorio) dir.clonarDiretorioArquivo(pasta);
                pasta.addFilho(novoDir);
            }
        }

        System.out.println("Arquivo " + nomeZip + " descompactado com sucesso.");
    }



}
