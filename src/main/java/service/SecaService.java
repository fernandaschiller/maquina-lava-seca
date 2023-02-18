package service;

import model.MaquinaLavaSeca;

public interface SecaService {
    void secar (MaquinaLavaSeca maquinaLavaSeca, int rotacao, int temperatura) throws Exception;
}
