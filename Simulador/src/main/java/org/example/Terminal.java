package org.example;

import java.util.ArrayList;
import java.util.Scanner;


public class Terminal {
    public static void main(String[] args) {

        Scanner entrada = new Scanner(System.in);

        ArrayList<Comandos> caminho = new ArrayList<>();
        ArrayList<String> historico = new ArrayList<>();

        Comandos atual;
        Comandos root = new Diretorio("C:");

        caminho.add(root);
        atual = root;

        while (true){

            String comando = entrada.nextLine();

            //mantém historico de comandos digitados
            historico.add(comando);

            String[] comandos = comando.split(" ");

            switch (comandos[0]){
                case "mkdir":
                    atual.mkdir(comandos[1]);
                    break;

                case "tree":
                    atual.tree();
                    break;

                case "exit":
                    System.out.println("Encerrando terminal");
                    return;

                    case "touch":
                    atual.touch(comandos[1]);
                    break;

                case "rmdir":
                    atual.rmdir(comandos[1]);
                    break;
                case "rename":
                    atual.rename(comandos[1],comandos[2]);
                    break;
                case "echo":
                    atual.echo(comandos[1],comandos[2],comandos[3]);
                    break;
                case "cat":
                    atual.cat(comandos[1]);
                    break;
                case "rm":
                    atual.rm(comandos[1]);
                    break;
                case "head":
                    atual.head(comandos[1],comandos[2]);
                    break;
                case "tail":
                    atual.tail(comandos[1],comandos[2]);
                    break;
                case "wc":
                    atual.wc(comandos[1]);
                    break;
                case "cd":
                    if(comandos.length==2){
                        if(comandos[1].equals("..")){

                            //cd..
                            if(caminho.size() > 1){
                                caminho.remove(caminho.size()-1);
                                atual = caminho.get(caminho.size()-1);

                            }else if ("/".equals(comandos[1])){
                                //cd /
                                caminho.clear();
                                caminho.add(root);
                                atual = root;

                            }else{
                                //cd <nome>
                                boolean encontrado = false;
                                for(Comandos c : atual.getFilhos()){
                                    if(c instanceof Diretorio && c.getNome().equals(comandos[1])){
                                        atual = c;
                                        caminho.add(atual);
                                        encontrado = true;
                                        break;
                                    }
                                }
                                if(!encontrado){
                                    System.out.println("Diretório não encontrado. ");
                                }
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


                default:
                    System.out.println("Comando inválido");
                    break;
            }
        }
    }
}