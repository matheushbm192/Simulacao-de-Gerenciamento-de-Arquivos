package org.example;

public class SistemaOperacional {
    private static SistemaOperacional instancia;
    private int proximoInode = 1;

    private SistemaOperacional() {
    }

    public static SistemaOperacional getInstance() {
        if (instancia == null) {
            instancia = new SistemaOperacional();
        }
        return instancia;
    }

    public synchronized int gerarInode() {
        return proximoInode++;
    }

    public  Comandos buscarPorPathParcial(String path, Comandos atual){
        String[] nomesDiretoriosArquivos = path.split("/");
        if(nomesDiretoriosArquivos.length == 1 ){
            if(atual instanceof Diretorio diretorioAtual){
               return  diretorioAtual.buscarDiretorioArquivo(nomesDiretoriosArquivos[0]);
            }
        }else{ //local/janta/documento.txt
            for (int i = 0; i < nomesDiretoriosArquivos.length; i++) {
                if(atual instanceof Diretorio diretorioAtual){
                   atual =  diretorioAtual.buscarDiretorioArquivo(nomesDiretoriosArquivos[i]);
                }
                if(atual.getNome().equals(nomesDiretoriosArquivos[nomesDiretoriosArquivos.length-1])){
                    return  atual;
                }
            }
        }
        return null;
    }
}
