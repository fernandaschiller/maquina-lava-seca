package service;

import model.MaquinaLavaSeca;
import enums.estadoAtual;

public class SecaServiceImpl extends MaquinaServiceImpl implements SecaService {
    public void secar(MaquinaLavaSeca maquinaLavaSeca, int rotacao, int temperatura) throws Exception {
        if (maquinaLavaSeca.isLigada() && maquinaLavaSeca.isPortaFechada() && ((maquinaLavaSeca.getEstadoAtual().equals(estadoAtual.LIGADA)) || (maquinaLavaSeca.getEstadoAtual().equals(estadoAtual.CENTRIFUGANDO)))) {
            maquinaLavaSeca.setTemperaturaAtual(temperatura);
            maquinaLavaSeca.setRotacaoAtual(rotacao);
            maquinaLavaSeca.setEstadoAtual(estadoAtual.SECANDO);
        } else {
            throw new Exception("Não é possível realizar secagem!");
        }
    }
}
