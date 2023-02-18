package model;

import enums.estadoAtual;

public class MaquinaLavaSeca {
    private boolean ligada;
    private boolean portaFechada;
    private int temperaturaAtual;
    private static final int TEMPERATURA_MAXIMA = 90;
    private int rotacaoAtual;
    private static final int ROTACAO_MAXIMA = 1000;
    private estadoAtual estadoAtual;

    public MaquinaLavaSeca() {
        this.ligada = false;
        this.portaFechada = false;
        this.temperaturaAtual = 0;
        this.rotacaoAtual = 0;
        this.estadoAtual = enums.estadoAtual.DESLIGADA;
    }

    public boolean isLigada() {
        return ligada;
    }

    public void setLigada(boolean ligada) {
        this.ligada = ligada;
    }

    public boolean isPortaFechada() {
        return portaFechada;
    }

    public void setPortaFechada(boolean portaFechada) {
        this.portaFechada = portaFechada;
    }

    public float getTemperaturaAtual() {
        return temperaturaAtual;
    }

    public void setTemperaturaAtual(int temperaturaAtual) {
        if (temperaturaAtual >= 0) {
            if (temperaturaAtual <= getTemperaturaMaxima()) {
                this.temperaturaAtual = temperaturaAtual;
            } else {
                this.temperaturaAtual = getTemperaturaMaxima();
            }
        } else {
            throw new RuntimeException("Temperatura inválida.");
        }
    }

    public int getTemperaturaMaxima() {
        return TEMPERATURA_MAXIMA;
    }
    public int getRotacaoAtual() {
        return rotacaoAtual;
    }

    public void setRotacaoAtual(int rotacaoAtual) {
        if (rotacaoAtual >= 0) {
            if (rotacaoAtual <= getRotacaoMaxima()) {
                this.rotacaoAtual = rotacaoAtual;
            } else {
                this.rotacaoAtual = getRotacaoMaxima();
            }
        } else {
            throw new RuntimeException("Rotação inválida.");
        }
    }

    public int getRotacaoMaxima() {
        return ROTACAO_MAXIMA;
    }

    public enums.estadoAtual getEstadoAtual() {
        return estadoAtual;
    }

    public void setEstadoAtual(enums.estadoAtual estadoAtual) {
        this.estadoAtual = estadoAtual;
    }

    @Override
    public String toString() {
        return "MaquinaLavaSeca{" +
                "ligada=" + ligada +
                ", portaFechada=" + portaFechada +
                ", temperatura=" + temperaturaAtual +
                ", rotacao=" + rotacaoAtual +
                ", estadoAtual='" + estadoAtual + '\'' +
                '}';
    }
}
