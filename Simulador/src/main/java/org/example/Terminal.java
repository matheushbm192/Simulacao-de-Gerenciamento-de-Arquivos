package org.example;

import java.util.ArrayList;
import java.util.Scanner;


public class Terminal {
    public static void main(String[] args) {

        Scanner entrada = new Scanner(System.in);

        ArrayList<Comandos> caminho = new ArrayList<>();
        ArrayList<String> historico = new ArrayList<>();
        int inode = 1;
        Comandos atual;
        Comandos root = new Diretorio("C:",inode);

        caminho.add(root);
        atual = root;

        while (true){

            System.out.print(montarPrompt(caminho));

            String comando = entrada.nextLine();

            historico.add(comando);

            //mantém historico de comandos digitados
            historico.add(comando);

            String[] comandos = comando.split(" ");

            switch (comandos[0]){
                case "mkdir":
                    if(comandos.length == 2) {
                        inode++;
                        atual.mkdir(comandos[1],inode);
                    }
                    break;

                case "rmdir":
                    if(comandos.length == 2) {
                        atual.rmdir(comandos[1]);
                    }
                    break;

                case "tree":
                    if(comandos.length == 1) {
                        atual.tree();
                    }
                    break;

                case "rename":
                    if(comandos.length == 3) {
                        atual.rename(comandos[1], comandos[2]);
                    }
                    break;

                case "touch":
                    if(comandos.length == 2) {
                        inode++;
                        atual.touch(comandos[1],inode);
                    }
                    break;

                case "echo":
                    if(comandos.length == 4) {
                        inode++;
                        atual.echo(comandos[1], comandos[2], comandos[3],inode);
                    }
                    break;

                case "cat":
                    if(comandos.length == 2) {
                        atual.cat(comandos[1]);
                    }
                    break;

                case "rm":
                    if(comandos.length == 2) {
                        atual.rm(comandos[1]);
                    }
                    break;

                case "head":
                    if(comandos.length == 3) {
                        atual.head(comandos[1], comandos[2]);
                    }
                    break;

                case "tail":
                    if(comandos.length == 3) {
                        atual.tail(comandos[1], comandos[2]);
                    }
                    break;

                case "wc":
                    if(comandos.length == 2){
                        atual.wc(comandos[1]);
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
                    }
                    break;

                case "find":
                    if (comandos.length == 4 && comandos[2].equals("-name")) {

                        if (comandos[1].equals(atual.getNome())) {
                            ((Diretorio) atual).find(comandos[3]);
                        } else {
                            System.out.println("Diretório inicial inválido.");
                        }

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

                    }
                    break;

                case "ls":
                    if (comandos.length == 2 && comandos[1].equals("-l")) {

                        for (Comandos c : atual.getFilhos()) {

                            if (c instanceof Arquivo) {
                                System.out.println(((Arquivo) c).detalhes());
                            } else if (c instanceof Diretorio) {
                                System.out.println(((Diretorio) c).detalhes());
                            }
                        }

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

                case "unzip":
                    if (comandos.length == 2) {
                        atual.unzip(comandos[1]);
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


}