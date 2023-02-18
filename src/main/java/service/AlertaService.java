package service;

import model.MaquinaLavaSeca;

public interface AlertaService {
    boolean emitirAlertaViaSMS(MaquinaLavaSeca maquinaLavaSeca) throws Exception;
}
