//package service;
//
//import enums.estadoAtual;
//import model.MaquinaLavaSeca;
//
//public class AlertaServiceImpl implements AlertaService{
//    @Override
//    public boolean emitirAlertaViaSMS(MaquinaLavaSeca maquinaLavaSeca) throws Exception {
//        if (maquinaLavaSeca.isLigada() && maquinaLavaSeca.isPortaFechada() && maquinaLavaSeca.getEstadoAtual().equals(estadoAtual.FINALIZADA)) {
//            System.out.println("Informamos que o alerva via SMS foi enviado com sucesso!");
//            return true;
//        } else {
//            throw new Exception("Não foi possível emitir o alerta via SMS");
//        }
//    }
//}
