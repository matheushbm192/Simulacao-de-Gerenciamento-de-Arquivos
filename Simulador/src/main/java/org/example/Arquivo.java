package org.example;

import java.util.ArrayList;
import java.util.Date;

public class Arquivo implements Comandos {
    private String nome;
    private String tipo;
    private Date dataCriacao;
    private Date dataUltimaModificacao;
    private String texto;
    private int contLinhas;
    private int contPalavras;
    private int countLetras;
    private boolean leitura;
    private boolean escrita;
    private boolean execucao;
    private String proprietario;
    private long inode;
    private String grupo;
    private String permissoesOctal;   // ex: 0644
    private String permissoesSimbolicas; // ex: -rw-r--r--
    private Date dataUltimoAcesso;
    private Date dataAlteracaoMetadados;
    private int tamanhoBytes;
    private final int bloco = 512;
    private boolean zip;
    private ArrayList<Comandos> conteudoZip = new ArrayList<>();


    //todo: arrumar o construtor pois em sua criação ele precisa ter mais informações
    public Arquivo(String nome) {
        this.nome = nome;
        this.inode = SistemaOperacional.getInstance().gerarInode();
        this.texto = "";

        this.zip = nome.endsWith(".zip");
        this.tipo = "-";

        this.leitura = true;
        this.escrita = true;
        this.execucao = false;

        this.proprietario = "user";
        this.grupo = "user";

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
    public ArrayList<Comandos> getFilhos() {
        return null;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public int getContLinhas() {
        return contLinhas;
    }

    public void setContLinhas(int contLinhas) {
        this.contLinhas = contLinhas;
    }

    public int getContPalavras() {
        return contPalavras;
    }

    public void setContPalavras(int contPalavras) {
        this.contPalavras = contPalavras;
    }

    public int getCountLetras() {
        return countLetras;
    }

    public void setCountLetras(int countLetras) {
        this.countLetras = countLetras;
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


    //todo: não gostei dessa implementação, implementar a separação de linha  fuuramente
    public void textoIncrement(String textoIncrement) {
        StringBuilder textoAdicional = new StringBuilder();
        textoAdicional.append(" ").append(textoIncrement);
       texto += textoAdicional.toString() ;

    }

    private String adicionaQuebraLinha(String textoPuro) {

        String[] palavras = textoPuro.split(" ");
        StringBuilder textoTratado = new StringBuilder();

        int palavrasPorLinha = 4;
        int contador = 0;

        for (String palavra : palavras) {
            textoTratado.append(palavra).append(" ");
            contador++;

            if (contador == palavrasPorLinha) {
                textoTratado.append("\n");
                contador = 0;
            }
        }

        return textoTratado.toString();
    }

    public void escrever(String texto) {
        this.texto = texto;
        this.dataUltimaModificacao = new Date();
    }

    private void atualizarTamanho() {
        this.tamanhoBytes = texto.length();
    }


    @Override
    public void mkdir(String nomeDiretorio) {
        System.out.println("Erro: arquivo não pode conter diretórios.");
    }

    @Override
    public void rmdir(String nomeDiretorio) {
        comandoInvalido("rmdir");

    }

    @Override
    public void rename(String nomeDiretorioArquivo, String novoNomeDiretorioArquivo) {
        comandoInvalido("rename");

    }

    @Override
    public void echo(String texto, String atributo, String nomeArquivo) {
        System.out.println("Erro: echo deve ser executado em um diretório.");
    }

    @Override
    public void cat(String nomeArquivo) {
        System.out.println(texto);
    }

    @Override
    public void rm(String nomeDiretorioArquivo) {
        comandoInvalido("rm");
    }

    @Override
    public void tree() {
        //arquivo não tem filhos
        System.out.println(nome);
    }

    @Override
    public void touch(String nomeArquivo) {
        System.out.println("Erro: não é possível criar arquivo dentro de um arquivo.");
    }

    @Override
    public void head(String nomeArquivo, String numeroDeLinhas) throws RuntimeException {
        int numeroDeLinhasConvert;
        String textoComQuebraLinha = adicionaQuebraLinha(texto);
        String[] textoSeparadoPorLinha = textoComQuebraLinha.split("\n");
        int quantidadeLinhas = textoSeparadoPorLinha.length;

        try{
            numeroDeLinhasConvert = Integer.parseInt(numeroDeLinhas);

        } catch (RuntimeException e) {
            throw new RuntimeException("O valor de linhas informado não é um número!");
        }

        if(numeroDeLinhasConvert <= quantidadeLinhas){
            for (int i = 0; i < numeroDeLinhasConvert; i++) {
                System.out.println(textoSeparadoPorLinha[i]);
            }

        }else {
            throw new RuntimeException("Número de linhas informado é superior ao numero de linhas do arquivo!");
        }
    }

    @Override
    public void tail(String nomeArquivo, String numeroDeLinhas) {
        int numeroDeLinhasConvert;
        String textoComQuebraLinha = adicionaQuebraLinha(texto);
        String[] textoSeparadoPorLinha = textoComQuebraLinha.split("\n");
        int quantidadeLinhas = textoSeparadoPorLinha.length;

        try{
            numeroDeLinhasConvert = Integer.parseInt(numeroDeLinhas);

        } catch (RuntimeException e) {
            throw new RuntimeException("O valor de linhas informado não é um número!");
        }

        if(numeroDeLinhasConvert <= quantidadeLinhas){
            int linhaInicial = quantidadeLinhas - numeroDeLinhasConvert;
            for (int i = linhaInicial; i < quantidadeLinhas; i++) {
                System.out.println(textoSeparadoPorLinha[i]);
            }

        }else {
            throw new RuntimeException("Número de linhas informado é superior ao numero de linhas do arquivo!");
        }
    }

    @Override
    public void wc(String nomeArquivo) {
        System.out.println("linhas: " +countLinhas());
        System.out.println("palavras: " + countPalavras());
        System.out.println("caracteres: " + countCaracteres());

    }

    public void setPermissoes(String permissao) {
        leitura = permissao.contains("r");
        escrita = permissao.contains("w");
        execucao = permissao.contains("x");
    }

    private int countLinhas(){
        String textoComQuebraLinha = adicionaQuebraLinha(texto);
        String[] textoSeparadoPorLinha = textoComQuebraLinha.split("\n");
        return textoSeparadoPorLinha.length;
    }

    private int countPalavras(){
        String[] palavras = texto.split(" ");
        return palavras.length;
    }

    private int countCaracteres(){
        String[] caracteres = texto.split("");
        return caracteres.length;
    }

    @Override
    public void find(String nomeDiretorioArquivo) {
        // arquivo não inicia busca
        System.out.println("Erro: find deve ser executado em um diretório.");
    }

    // todo: criar para diretório também? mesmo que seja inválido?
    public void grep(String termo) {

        if (!leitura) {
            System.out.println("Permissão negada: leitura.");
            return;
        }

        if (texto.isEmpty()) {
            return;
        }

        String[] linhas = texto.split("\n");

        for (int i = 0; i < linhas.length; i++) {
            if (linhas[i].contains(termo)) {
                System.out.println(
                        getNome() + ":" + (i + 1) + ": " + linhas[i]
                );
            }
        }
    }

    @Override
    public void stat(String nomeDiretorioArquivo) {
        printStat();
    }

    @Override
    public double getTamanhoBytes() {
        return tamanhoBytes;
    }

    private void printStat() {

        System.out.println("Arquivo: " + nome);

        System.out.println(
                "Tamanho: " + tamanhoBytes +
                        "  Blocos: " + (int) Math.ceil(tamanhoBytes / bloco) +
                        "  Bloco IO: 4096 " +
                        tipo
        );

        System.out.println(
                "Dispositivo: 802h/2050d" +
                        "  Inode: " + inode +
                        "  Links: 1"
        );

        System.out.println(
                "Acesso: (" + permissoesOctal + "/" + permissoesSimbolicas + ")" +
                        "  UID: (1000/" + proprietario + ")" +
                        "  GID: (1000/" + grupo + ")"
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
    @Override
    public void du(String nomeDiretorio) {
        System.out.println("Erro: du deve ser executado em um diretório.");
    }

    @Override
    public void cp(String nomeOrigem, Diretorio destino) {

        if(this.nome.equals(nomeOrigem)){
          destino.addFilho(this.clonarDiretorioArquivo(destino));
        }else{
            System.out.println("Argumento invalido");
        }

    }

    @Override
    public Comandos clonarDiretorioArquivo(Diretorio destino) {
        //todo: fazer verificação se destino existe, se não existir fazer algo quanto ao nome da copia
        Arquivo clone = new Arquivo( this.nome);
        // Identidade
        clone.tipo = this.tipo;

        // Conteúdo (cópia real)
        clone.texto = this.texto != null ? new String(this.texto) : null;

        // Contadores
        clone.contLinhas = this.contLinhas;
        clone.contPalavras = this.contPalavras;
        clone.countLetras = this.countLetras;

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
        clone.dataCriacao = new Date(); // Birth
        clone.dataUltimoAcesso = new Date();
        clone.dataAlteracaoMetadados = new Date();
        clone.dataUltimaModificacao = this.dataUltimaModificacao;

        // Inode novo
        clone.inode = SistemaOperacional.getInstance().gerarInode();

        // Tamanho recalculado
        clone.tamanhoBytes = (clone.texto == null) ? 0 : clone.texto.length();

        return clone;
    }

    @Override
    public void mv(String nomeOrigem, Diretorio destino) {
        System.out.println("Erro: du deve ser executado em um diretório.");
    }

    @Override
    public void diff(Arquivo arquivo2) {

    }

    @Override
    public void zip(String nomeZip, ArrayList<String> itens) {
        System.out.println("zip: operação inválida em arquivo");
    }

    @Override
    public void unzip(String nomeZip) {

        System.out.println("Erro: unzip só pode ser executado em diretórios.");
    }

    public String detalhes() {

        String tipo = "-";
        String permissoes =
                (leitura ? "r" : "-") +
                        (escrita ? "w" : "-") +
                        (execucao ? "x" : "-");

        return String.format(
                "%s%s  %s  %d  %s",
                tipo,
                permissoes,
                proprietario,
                (int) tamanhoBytes,
                nome
        );
    }

    // todo: usar esse método depois onde os comandos são inválidos para arquivo, vai facilitar
    private void comandoInvalido(String comando) {
        System.out.println(comando + ": operação inválida para arquivo");
    }

    public void adicionarAoZip(Comandos c) {
        conteudoZip.add(c);
    }


    public boolean isZip() {
        return zip;
    }
    public ArrayList<Comandos> getConteudoZip() {
        return conteudoZip;
    }
}
