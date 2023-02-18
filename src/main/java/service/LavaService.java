package service;

import model.MaquinaLavaSeca;

public interface LavaService {
    void lavar(MaquinaLavaSeca maquinaLavaSeca, int rotacao, int temperatura) throws Exception;
    void centrifugar(MaquinaLavaSeca maquinaLavaSeca, int rotacao) throws Exception;
}
