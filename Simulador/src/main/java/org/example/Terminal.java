package org.example;

import java.util.ArrayList;
import java.util.Scanner;


public class Terminal {
    public static void main(String[] args) {

        ArrayList<Comandos> caminho = new ArrayList<>();
        ArrayList<String> historico = new ArrayList<>();

        Comandos root = new Diretorio("C:");

        caminho.add(root);
        Comandos atual;
        atual = root;

        Scanner entrada = new Scanner(System.in);

        while (true){

            String comando = entrada.nextLine();

            //analisar essa divisão
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

                default:
                    System.out.println("Comando inválido");
            }
        }
    }
}