package service;

import model.MaquinaLavaSeca;
import enums.estadoAtual;

public class MaquinaServiceImpl implements MaquinaService{

    public void ligar(MaquinaLavaSeca maquinaLavaSeca) throws Exception {
        if (maquinaLavaSeca.getEstadoAtual().equals(estadoAtual.DESLIGADA)){
            maquinaLavaSeca.setLigada(true);
            maquinaLavaSeca.setEstadoAtual(estadoAtual.LIGADA);
        } else {
            throw new Exception("Estado atual máquina: " + maquinaLavaSeca.getEstadoAtual().toString());
        }
    }

    @Override
    public void fecharPorta(MaquinaLavaSeca maquinaLavaSeca) throws Exception {
        if (!maquinaLavaSeca.isPortaFechada()) {
            maquinaLavaSeca.setPortaFechada(true);
        } else {
            throw new Exception("Porta já está fechada");
        }
    }

    @Override // porta não pode abrir quando tiver operando ARRUMAR
    public void abrirPorta(MaquinaLavaSeca maquinaLavaSeca) throws Exception {
        if(maquinaLavaSeca.isPortaFechada()) {
            if (maquinaLavaSeca.getEstadoAtual().equals(estadoAtual.LIGADA) || maquinaLavaSeca.getEstadoAtual().equals(estadoAtual.DESLIGADA) || maquinaLavaSeca.getEstadoAtual().equals(estadoAtual.FINALIZADA)) {
                maquinaLavaSeca.setPortaFechada(false);
            } else {
                throw new Exception("Estado atual da máquina: " + maquinaLavaSeca.getEstadoAtual().toString());
            }
        } else {
            throw new Exception("Porta já está aberta");
        }
    }

    public void desligar(MaquinaLavaSeca maquinaLavaSeca) throws Exception {
        if ((maquinaLavaSeca.getEstadoAtual().equals(estadoAtual.LIGADA)) || (maquinaLavaSeca.getEstadoAtual().equals(estadoAtual.FINALIZADA))) {
            maquinaLavaSeca.setLigada(false);
            maquinaLavaSeca.setEstadoAtual(estadoAtual.DESLIGADA);
        } else {
            throw new Exception("Estado atual da máquina: " + maquinaLavaSeca.getEstadoAtual().toString());
        }
    }

    public void finalizarCiclo(MaquinaLavaSeca maquinaLavaSeca, AlertaService alertaService) throws Exception {
        if (maquinaLavaSeca.isLigada() && maquinaLavaSeca.isPortaFechada() && ((maquinaLavaSeca.getEstadoAtual().equals(estadoAtual.LAVANDO)) || (maquinaLavaSeca.getEstadoAtual().equals(estadoAtual.CENTRIFUGANDO)) || (maquinaLavaSeca.getEstadoAtual().equals(estadoAtual.SECANDO)))) {
            maquinaLavaSeca.setTemperaturaAtual(0);
            maquinaLavaSeca.setRotacaoAtual(0);
            maquinaLavaSeca.setEstadoAtual(estadoAtual.FINALIZADA);
            if(alertaService.emitirAlertaViaSMS(maquinaLavaSeca)) {
                System.out.println("Alerta via SMS emitido com sucesso!!! -> Mocked");
            } else {
                System.out.println("Alerta via SMS não emitido... -> Mocked");
            }
        } else {
            throw new Exception("Estado atual da máquina: " + maquinaLavaSeca.getEstadoAtual().toString());
        }
    }
}
