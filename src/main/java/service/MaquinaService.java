package service;

import model.MaquinaLavaSeca;

public interface MaquinaService {
    void ligar(MaquinaLavaSeca maquinaLavaSeca) throws Exception;
    void desligar(MaquinaLavaSeca maquinaLavaSeca) throws Exception;
    void abrirPorta(MaquinaLavaSeca maquinaLavaSeca) throws Exception;
    void fecharPorta(MaquinaLavaSeca maquinaLavaSeca) throws Exception;
    //String estadoAtual(MaquinaLavaSeca maquinaLavaSeca);
    void finalizarCiclo(MaquinaLavaSeca maquinaLavaSeca) throws Exception;
}

