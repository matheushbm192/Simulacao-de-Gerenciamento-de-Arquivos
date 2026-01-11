package org.example;

import java.util.ArrayList;
import java.util.Scanner;


public class Terminal {
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        ArrayList<String> historico = new ArrayList<>();
        ArrayList<Comandos> caminho = new ArrayList<>();
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
                case "rmdir":
                    atual.rmdir(comandos[1]);
                    break;
                case "rename":
                    atual.rename(comandos[1],comandos[2]);
                    break;
                case "echo":
                    atual.echoIncremento(comandos[1], , comandos[2]);
                    break;
                case "cat":
                    atual.cat(comandos[1]);
                    break;
                case "rm":
                    atual.rm(comandos[1]);
                    break;
            }
        }
    }
}