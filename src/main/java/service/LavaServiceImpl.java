package service;

import model.MaquinaLavaSeca;
import enums.estadoAtual;

public class LavaServiceImpl extends MaquinaServiceImpl implements LavaService{
    public void lavar(MaquinaLavaSeca maquinaLavaSeca, int rotacao, int temperatura) throws Exception {
        if (maquinaLavaSeca.isLigada() && maquinaLavaSeca.isPortaFechada() && maquinaLavaSeca.getEstadoAtual().equals(estadoAtual.LIGADA)) {
            maquinaLavaSeca.setTemperaturaAtual(temperatura);
            maquinaLavaSeca.setRotacaoAtual(rotacao);
            maquinaLavaSeca.setEstadoAtual(estadoAtual.LAVANDO);
        } else {
            throw new Exception("Não é possível realizar lavagem!");
        }
    }

    public void centrifugar(MaquinaLavaSeca maquinaLavaSeca, int rotacao) throws Exception {
        if (maquinaLavaSeca.isLigada() && maquinaLavaSeca.isPortaFechada() && ((maquinaLavaSeca.getEstadoAtual().equals(estadoAtual.LIGADA)) || (maquinaLavaSeca.getEstadoAtual().equals(estadoAtual.LAVANDO)))) {
            maquinaLavaSeca.setRotacaoAtual(rotacao);
            maquinaLavaSeca.setEstadoAtual(estadoAtual.CENTRIFUGANDO);
        } else {
            throw new Exception("Não é possível realizar centrifugação!");
        }
    }
}
