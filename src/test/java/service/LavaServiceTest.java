package service;

import enums.estadoAtual;
import model.MaquinaLavaSeca;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

//@ExtendWith(MockitoExtension.class)
public class LavaServiceTest {
    //@Mock
    //private AlertaService alertaServiceMocked;
    //@InjectMocks
    private MaquinaService maquinaService;
    //@InjectMocks
    private LavaService lavaService;
    private MaquinaLavaSeca maquinaLavaSeca;

    @BeforeEach
    public void inicializaMaquinaServico () {
//        MockitoAnnotations.openMocks(this);
        maquinaService = new MaquinaServiceImpl();
        maquinaLavaSeca = new MaquinaLavaSeca();
    }

    @BeforeEach
    public void inicializaLavaServico () {
        lavaService = new LavaServiceImpl();
    }

    @Test
    public void deveLavarCorretamente() throws Exception {
        // dado:
        maquinaService.ligar(maquinaLavaSeca);
        maquinaService.fecharPorta(maquinaLavaSeca);
        // quando:
        lavaService.lavar(maquinaLavaSeca, 50, 30);
        // então:
        Assertions.assertEquals(estadoAtual.LAVANDO, maquinaLavaSeca.getEstadoAtual());
        Assertions.assertEquals(50, maquinaLavaSeca.getRotacaoAtual());
        Assertions.assertEquals(30, maquinaLavaSeca.getTemperaturaAtual());
    }

    @Test
    public void deveCentrifugarCorretamente() throws Exception {
        // dado:
        maquinaService.ligar(maquinaLavaSeca);
        maquinaService.fecharPorta(maquinaLavaSeca);
        // quando:
        lavaService.centrifugar(maquinaLavaSeca, 800);
        // então:
        Assertions.assertEquals(estadoAtual.CENTRIFUGANDO, maquinaLavaSeca.getEstadoAtual());
        Assertions.assertEquals(800, maquinaLavaSeca.getRotacaoAtual());
    }

    @Test
    public void deveRealizarCicloSomenteSeLigadaEPortaFechada() throws Exception {
        // dado:
        maquinaService.ligar(maquinaLavaSeca);
        maquinaService.fecharPorta(maquinaLavaSeca);
        // quando:
        lavaService.lavar(maquinaLavaSeca, 250, 50);
        // então:
        Assertions.assertTrue(maquinaLavaSeca.isLigada());
        Assertions.assertTrue(maquinaLavaSeca.isPortaFechada());
    }

    @Test
    public void deveDesligarAposFinalizarOCiclo() throws Exception {
        // dado
        maquinaService.fecharPorta(maquinaLavaSeca);
        maquinaService.ligar(maquinaLavaSeca);
        lavaService.lavar(maquinaLavaSeca, 50, 30);
        maquinaService.finalizarCiclo(maquinaLavaSeca);
        // quando
        maquinaService.desligar(maquinaLavaSeca);
        // então
        Assertions.assertEquals(estadoAtual.DESLIGADA, maquinaLavaSeca.getEstadoAtual());
        Assertions.assertEquals(0, maquinaLavaSeca.getRotacaoAtual());
        Assertions.assertEquals(0, maquinaLavaSeca.getTemperaturaAtual());
    }

//    @Test
//    public void deveEmitirAlertaViaSMSQuandoFinalizarCicloCorretamente() throws Exception {
//        // dado
//        maquinaService.fecharPorta(maquinaLavaSeca);
//        maquinaService.ligar(maquinaLavaSeca);
//        lavaService.lavar(maquinaLavaSeca, 50, 30);
//        // quando
//        //Mockito.when(alertaServiceMocked.emitirAlertaViaSMS(maquinaLavaSeca)).thenReturn(true);
//        Mockito.when(alertaServiceMocked.emitirAlertaViaSMS(Mockito.any(MaquinaLavaSeca.class))).thenReturn(true);
//        maquinaService.finalizarCiclo(maquinaLavaSeca);
//        // então
//        Assertions.assertEquals(estadoAtual.FINALIZADA, maquinaLavaSeca.getEstadoAtual());
//        Assertions.assertEquals(0, maquinaLavaSeca.getRotacaoAtual());
//        Assertions.assertEquals(0, maquinaLavaSeca.getTemperaturaAtual());
//    }

    // testes negativos:

    @Test
    public void naoDeveDesligarMaquinaSemFinalizarCiclo () throws Exception {
        // dado:
        maquinaService.ligar(maquinaLavaSeca);
        maquinaService.fecharPorta(maquinaLavaSeca);
        lavaService.lavar(maquinaLavaSeca, 50, 30);
        // então:
        Assertions.assertThrows(Exception.class, () -> maquinaService.desligar(maquinaLavaSeca));
    }

    @Test
    public void naoDeveLavarComPortaAberta () {
        // dado:
        // quando:
        // então:
        Assertions.assertThrows(Exception.class, () -> lavaService.lavar(maquinaLavaSeca, 100, 30));
    }

    @Test
    public void naoDeveCentrifugarComPortaAberta () {
        // dado:
        // quando:
        // então:
        Assertions.assertThrows(Exception.class, () -> lavaService.centrifugar(maquinaLavaSeca, 100));
    }
}
