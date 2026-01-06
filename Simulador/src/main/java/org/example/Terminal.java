package org.example;

import java.util.ArrayList;
import java.util.Scanner;


public class Terminal {
    public static void main(String[] args) {

        ArrayList<String> historico = new ArrayList<>();

        Comandos root = new Diretorio("C:");
        ArrayList<Comandos> caminho = new ArrayList<>();
        caminho.add(root);
        Comandos atual;
        atual = root;
        while (true){
            Scanner entrada = new Scanner(System.in);
            String comando = entrada.nextLine();
            String[] comandos = comando.split(" ");
            switch (comandos[0]){
                case "mkdir":
                    atual.mkdir(comandos[1]);
                break;
                //TODO: adicionar demais comandos abaixo
            }
        }
    }
}