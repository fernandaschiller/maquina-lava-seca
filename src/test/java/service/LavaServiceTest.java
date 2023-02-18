package service;

import enums.estadoAtual;
import model.MaquinaLavaSeca;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class LavaServiceTest {
    @Mock
    private AlertaService alertaServiceMocked;
    @InjectMocks
    private MaquinaServiceImpl maquinaService;
    private LavaService lavaService;
    private MaquinaLavaSeca maquinaLavaSeca;

    @BeforeEach
    public void beforeEachTest () {
        MockitoAnnotations.openMocks(this);
        maquinaService = new MaquinaServiceImpl();
        maquinaLavaSeca = new MaquinaLavaSeca();
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
    public void deveDesligarAposFinalizarOCicloEEmitirSMS() throws Exception {
        // dado
        maquinaService.fecharPorta(maquinaLavaSeca);
        maquinaService.ligar(maquinaLavaSeca);
        lavaService.lavar(maquinaLavaSeca, 50, 30);

        Mockito.when(alertaServiceMocked.emitirAlertaViaSMS(maquinaLavaSeca)).thenReturn(true);
        maquinaService.finalizarCiclo(maquinaLavaSeca, alertaServiceMocked);
        // quando
        maquinaService.desligar(maquinaLavaSeca);
        // então
        Mockito.verify(alertaServiceMocked, Mockito.times(1)).emitirAlertaViaSMS(maquinaLavaSeca);
        Mockito.verify(alertaServiceMocked, Mockito.atLeastOnce()).emitirAlertaViaSMS(maquinaLavaSeca);
        Assertions.assertEquals(estadoAtual.DESLIGADA, maquinaLavaSeca.getEstadoAtual());
        Assertions.assertEquals(0, maquinaLavaSeca.getRotacaoAtual());
        Assertions.assertEquals(0, maquinaLavaSeca.getTemperaturaAtual());
    }

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
