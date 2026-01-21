package org.example;

import java.util.ArrayList;
import java.util.Scanner;


public class Terminal {
    public static void main(String[] args) {

        Scanner entrada = new Scanner(System.in);

        ArrayList<Comandos> caminho = new ArrayList<>();
        ArrayList<String> historico = new ArrayList<>();

        Comandos atual;
        Comandos root = new Diretorio("C:",null);

        caminho.add(root);
        atual = root;

        while (true){

            System.out.print(montarPrompt(caminho));

            String comando = entrada.nextLine();

            historico.add(comando);

            String[] comandos = comando.split(" (?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

            switch (comandos[0]){
                case "mkdir":
                    if(comandos.length == 2) {
                        atual.mkdir(comandos[1]);
                    }else {
                        System.out.println("Argumentos Inválidos (- mkdir <nome>)");
                    }
                    break;

                case "rmdir":
                    if(comandos.length == 2) {
                        atual.rmdir(comandos[1]);
                    }else {
                        System.out.println("Argumentos Inválidos (rmdir <nome>)");
                    }
                    break;

                case "tree":
                    if(comandos.length == 1) {
                        atual.tree();
                    }else {
                        System.out.println("Argumentos Inválidos (tree)");
                    }
                    break;

                case "rename":
                    if(comandos.length == 3) {
                        atual.rename(comandos[1], comandos[2]);
                    }else {
                        System.out.println("Argumentos Inválidos (rename <nome_antigo> <novo_nome>)");
                    }
                    break;

                case "touch":
                    if(comandos.length == 2) {
                        atual.touch(comandos[1]);
                    }else {
                        System.out.println("Argumentos Inválidos (touch <nome>)");
                    }
                    break;

                case "echo":
                    if (comandos.length == 4) {

                        String texto = comandos[1].replaceAll("^\"|\"$", "");
                        String operador = comandos[2];
                        String arquivo = comandos[3];

                        atual.echo(texto, operador, arquivo);

                    } else {
                        System.out.println("Argumentos Inválidos (echo \"texto\" >|>> arquivo)");
                    }
                    break;

                case "cat":
                    if(comandos.length == 2) {
                        atual.cat(comandos[1]);
                    }else {
                        System.out.println("Argumentos Inválidos (cat <arquivo>)");
                    }
                    break;

                case "rm":
                    if(comandos.length == 2) {
                        atual.rm(comandos[1]);
                    }else {
                        System.out.println("Argumentos Inválidos (rm <nome>)");
                    }
                    break;

                case "head":
                    if(comandos.length == 3) {
                        atual.head(comandos[1], comandos[2]);
                    }else {
                        System.out.println("Argumentos Inválidos (head <arquivo> <n>)");
                    }
                    break;

                case "tail":
                    if(comandos.length == 3) {
                        atual.tail(comandos[1], comandos[2]);
                    }else {
                        System.out.println("Argumentos Inválidos (tail <arquivo> <n>)");
                    }
                    break;

                case "wc":
                    if(comandos.length == 2){
                        atual.wc(comandos[1]);
                    }else {
                        System.out.println("Argumentos Inválidos (wc <arquivo>)");
                    }
                    break;

                case "cd":
                    if (comandos.length == 2) {

                        // cd ..
                        if (comandos[1].equals("..")) {

                            if (caminho.size() > 1) {
                                caminho.remove(caminho.size() - 1);
                                atual = caminho.get(caminho.size() - 1);
                            }

                        }
                        // cd /
                        else if (comandos[1].equals("/")) {

                            caminho.clear();
                            caminho.add(root);
                            atual = root;

                        }
                        // cd <nome>
                        else {

                            boolean encontrado = false;

                            for (Comandos c : atual.getFilhos()) {
                                if (c instanceof Diretorio && c.getNome().equals(comandos[1])) {
                                    atual = c;
                                    caminho.add(atual);
                                    encontrado = true;
                                    break;
                                }
                            }

                            if (!encontrado) {
                                System.out.println("Diretório não encontrado.");
                            }
                        }
                    } else {
                        System.out.println("Argumentos Inválidos (cd <nome>|..)");
                    }
                    break;

                case "find":
                    if (comandos.length == 4 && comandos[2].equals("-name")) {

                        if (comandos[1].equals(atual.getNome())) {
                            ((Diretorio) atual).find(comandos[3]);
                        } else {
                            System.out.println("Diretório inicial inválido.");
                        }

                    }else {
                        System.out.println("Argumentos Inválidos (find <diretorio> -name <nome>)");
                    }
                    break;

                case "grep":
                    if (comandos.length == 3) {

                        String termo = comandos[1];
                        String nomeArquivo = comandos[2];

                        boolean encontrado = false;

                        for (Comandos c : atual.getFilhos()) {
                            if (c instanceof Arquivo && c.getNome().equals(nomeArquivo)) {
                                ((Arquivo) c).grep(termo);
                                encontrado = true;
                                break;
                            }
                        }

                        if (!encontrado) {
                            System.out.println("Arquivo não encontrado.");
                        }

                    }else {
                        System.out.println("Argumentos Inválidos (grep <termo> <arquivo>)");
                    }
                    break;

                case "chmod":
                    if (comandos.length == 3) {

                        String permissao = comandos[1];
                        String nome = comandos[2];

                        boolean encontrado = false;

                        for (Comandos c : atual.getFilhos()) {

                            if (c.getNome().equals(nome)) {

                                if (c instanceof Arquivo) {
                                    ((Arquivo) c).setPermissoes(permissao);
                                } else if (c instanceof Diretorio) {
                                    ((Diretorio) c).setPermissoes(permissao);
                                }

                                encontrado = true;
                                break;
                            }
                        }

                        if (!encontrado) {
                            System.out.println("Arquivo ou diretório não encontrado.");
                        }

                    }else {
                        System.out.println("Argumentos Inválidos (chmod <permissao> <nome>)");
                    }
                    break;

                case "chown":
                    if (comandos.length == 3) {

                        String novoProprietario = comandos[1];
                        String nome = comandos[2];

                        boolean encontrado = false;

                        for (Comandos c : atual.getFilhos()) {

                            if (c.getNome().equals(nome)) {

                                if (c instanceof Arquivo) {
                                    ((Arquivo) c).setProprietario(novoProprietario);
                                } else if (c instanceof Diretorio) {
                                    ((Diretorio) c).setProprietario(novoProprietario);
                                }

                                encontrado = true;
                                break;
                            }
                        }

                        if (!encontrado) {
                            System.out.println("Arquivo ou diretório não encontrado.");
                        }

                    }else {
                        System.out.println("Argumentos Inválidos (chown <proprietario> <nome>)");
                    }
                    break;

                case "ls":
                    if (atual.getFilhos().isEmpty()) {
                        System.out.println("(diretório vazio)");
                    } else {
                        for (Comandos c : atual.getFilhos()) {
                            if (c instanceof Diretorio) {
                                System.out.println(((Diretorio) c).detalhes());
                            } else if (c instanceof Arquivo) {
                                System.out.println(((Arquivo) c).detalhes());
                            }
                        }
                    }
                    break;
                case "du":
                    if(comandos.length == 2){
                        atual.du(comandos[1]);
                    }else{
                        System.out.println("Argumentos Inválidos (du <nome>)");
                    }
                    break;
                case "stat":
                    if(comandos.length == 2){
                        atual.stat(comandos[1]);
                    }else{
                        System.out.println("Argumentos Inválidos (stat <nome>)");
                    }
                    break;
                case "cp":
                    if(comandos.length == 3){
                        Comandos diretorioArquivo = pesquisarNaArvore(root,comandos[2]);
                        if(diretorioArquivo instanceof Diretorio destino) {
                            atual.cp(comandos[1], destino);
                        }else {
                            System.out.println("Argumentos Inválidos, o destino deve ser um diretorio");
                        }
                    }else{
                        System.out.println("Argumentos Inválidos (cp <origem> <destino>)");
                    }
                    break;
                case"mv":
                    if (comandos.length == 3){
                        Comandos diretorioArquivo = pesquisarNaArvore( root,comandos[2]);
                        if(diretorioArquivo instanceof Diretorio destino) {
                            atual.mv(comandos[1], destino);
                        }else {
                            System.out.println("Argumentos Inválidos, o destino deve ser um diretorio");
                        }

                    }else{
                        System.out.println("Argumentos Inválidos (mv <origem> <destino>)");
                    }
                    break;
                case"diff":
                    if (comandos.length == 3){
                        atual.diff(comandos[1],comandos[2]);

                    }else{
                        System.out.println("Argumentos Inválidos (diff <arquivo1> <arquivo2>)");
                    }
                    break;
                case "exit":
                    if(comandos.length == 1){
                        System.out.println("Encerrando terminal");
                        return;
                    }
                    break;

                case "zip":
                    if (comandos.length >= 3) {

                        String nomeZip = comandos[1];
                        ArrayList<String> itens = new ArrayList<>();

                        for (int i = 2; i < comandos.length; i++) {
                            itens.add(comandos[i]);
                        }

                        atual.zip(nomeZip, itens);
                    }
                    break;
                case "history":
                    if(comandos.length == 1){
                        for (int i = 0; i < historico.size()-1 ; i++) {
                            System.out.println(historico.get(i));
                        }

                    }
                    break;
                case "unzip":
                    if (comandos.length == 2) {
                        atual.unzip(comandos[1]);
                    }
                    break;

                case "pwd":
                    if (comandos.length == 1) {
                        // Mostra o caminho completo atual
                        System.out.println(montarPrompt(caminho).replace("PS ", "").replace("> ", ""));
                    } else {
                        System.out.println("Argumentos inválidos (pwd)");
                    }
                    break;

                    default:
                    System.out.println("Comando inválido");
                    break;
            }
        }
    }

    private static String montarPrompt(ArrayList<Comandos> caminho) {
        StringBuilder sb = new StringBuilder("PS ");

        for (int i = 0; i < caminho.size(); i++) {
            sb.append(caminho.get(i).getNome());

            if (i < caminho.size() - 1) {
                sb.append("\\");
            }
        }

        sb.append("> ");
        return sb.toString();
    }



    public static Comandos pesquisarNaArvore(Comandos atual, String nomeDestino) {

        if (atual == null || nomeDestino == null) {
            return null;
        }

        if (nomeDestino.equals(atual.getNome())) {
            return atual;
        }

        if (atual.getFilhos() != null) {
            for (Comandos diretorioArquivo : atual.getFilhos()) {
                Comandos encontrado = pesquisarNaArvore(diretorioArquivo, nomeDestino);
                if (encontrado != null) {
                    return encontrado;
                }
            }
        }

        return null;

    }
}