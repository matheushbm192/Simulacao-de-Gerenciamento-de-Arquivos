package org.example;

import java.util.Scanner;


public class Terminal {
    public static void main(String[] args) {
        Comandos root = new Diretorio("C");
        Comandos anterior;
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