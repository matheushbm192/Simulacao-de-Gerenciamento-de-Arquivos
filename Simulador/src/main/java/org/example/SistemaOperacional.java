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
}
