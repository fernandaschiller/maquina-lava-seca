package service;

import enums.estadoAtual;
import model.MaquinaLavaSeca;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SecaServiceTest {
    private MaquinaService maquinaService;
    private LavaService lavaService;
    private SecaService secaService;
    private MaquinaLavaSeca maquinaLavaSeca;

    @BeforeEach
    public void inicializaMaquinaServico () {
        maquinaService = new MaquinaServiceImpl();
        maquinaLavaSeca = new MaquinaLavaSeca();
        secaService = new SecaServiceImpl();
        lavaService = new LavaServiceImpl();
    }

    @Test
    public void maquinaDeveSecarCorretamente() throws Exception {
        // dado:
        maquinaService.ligar(maquinaLavaSeca);
        maquinaService.fecharPorta(maquinaLavaSeca);
        // quando:
        secaService.secar(maquinaLavaSeca, 200, 40);
        // então:
        Assertions.assertEquals(estadoAtual.SECANDO, maquinaLavaSeca.getEstadoAtual());
        Assertions.assertEquals(200, maquinaLavaSeca.getRotacaoAtual());
        Assertions.assertEquals(40, maquinaLavaSeca.getTemperaturaAtual());
    }

    @Test
    public void maquinaDeveSecarCorretamenteAposLavarECentrifugar() throws Exception {
        // dado:
        maquinaService.ligar(maquinaLavaSeca);
        maquinaService.fecharPorta(maquinaLavaSeca);
        lavaService.lavar(maquinaLavaSeca, 50, 20);
        lavaService.centrifugar(maquinaLavaSeca, 800);
        // quando:
        secaService.secar(maquinaLavaSeca, 200, 40);
        // então:
        Assertions.assertEquals(estadoAtual.SECANDO, maquinaLavaSeca.getEstadoAtual());
        Assertions.assertEquals(200, maquinaLavaSeca.getRotacaoAtual());
        Assertions.assertEquals(40, maquinaLavaSeca.getTemperaturaAtual());
    }

    @Test
    public void finalizarSecagemCorretamente() throws Exception {
        // dado:
        maquinaService.ligar(maquinaLavaSeca);
        maquinaService.fecharPorta(maquinaLavaSeca);
        secaService.secar(maquinaLavaSeca, 200, 40);
        // quando:
        maquinaService.finalizarCiclo(maquinaLavaSeca);
        // então:
        Assertions.assertEquals(estadoAtual.FINALIZADA, maquinaLavaSeca.getEstadoAtual());
        Assertions.assertEquals(0, maquinaLavaSeca.getRotacaoAtual());
        Assertions.assertEquals(0, maquinaLavaSeca.getTemperaturaAtual());
    }

    @Test
    public void tempNaoDeveExcederTempMaxima () throws Exception {
        // dado:
        maquinaService.ligar(maquinaLavaSeca);
        maquinaService.fecharPorta(maquinaLavaSeca);
        // quando:
        secaService.secar(maquinaLavaSeca, 200, 1000);
        // então:
        Assertions.assertEquals(maquinaLavaSeca.getTemperaturaMaxima(), maquinaLavaSeca.getTemperaturaAtual());
    }

    @Test
    public void rotacaoNaoDeveExcederRotacaoMaxima () throws Exception {
        // dado:
        maquinaService.ligar(maquinaLavaSeca);
        maquinaService.fecharPorta(maquinaLavaSeca);
        // quando:
        secaService.secar(maquinaLavaSeca, 9999, 40);
        // então:
        Assertions.assertEquals(maquinaLavaSeca.getRotacaoMaxima(), maquinaLavaSeca.getRotacaoAtual());
    }

    // testes negativos

    @Test
    public void naoDeveSecarComMaquinaDesligadaEPortaAberta () {
        // dado:
        // quando:
        // então:
        Assertions.assertThrows(Exception.class, () -> secaService.secar(maquinaLavaSeca, 200, 40));
    }

    @Test
    public void naoDeveSecarComMaquinaDesligadaEPortaFechada () throws Exception {
        // dado:
        maquinaService.fecharPorta(maquinaLavaSeca);
        // quando:
        // então:
        Assertions.assertThrows(Exception.class, () -> secaService.secar(maquinaLavaSeca, 200, 40));
    }

    @Test
    public void naoDeveSecarComMaquinaLigadaEPortaAberta () throws Exception {
        // dado:
        maquinaService.ligar(maquinaLavaSeca);
        // quando:
        // então:
        Assertions.assertThrows(Exception.class, () -> secaService.secar(maquinaLavaSeca, 200, 40));
    }

    @Test
    public void naoDeveSecarAposApenasLavar () throws Exception {
        // dado:
        maquinaService.ligar(maquinaLavaSeca);
        maquinaService.fecharPorta(maquinaLavaSeca);
        lavaService.lavar(maquinaLavaSeca, 150,30);
        // quando:
        // então:
        Assertions.assertThrows(Exception.class, () -> secaService.secar(maquinaLavaSeca, 200, 40));
    }

    @Test
    public void secagemNaoDeveAceitarTempNegativa () throws Exception {
        // dado:
        maquinaService.ligar(maquinaLavaSeca);
        maquinaService.fecharPorta(maquinaLavaSeca);
        // quando:
        // então:
        Assertions.assertThrows(Exception.class, () -> secaService.secar(maquinaLavaSeca, 200, -40));
    }

    @Test
    public void secagemNaoDeveAceitarRotacaoNegativa () throws Exception {
        // dado:
        maquinaService.ligar(maquinaLavaSeca);
        maquinaService.fecharPorta(maquinaLavaSeca);
        // quando:
        // então:
        Assertions.assertThrows(Exception.class, () -> secaService.secar(maquinaLavaSeca, -200, 40));
    }

    @Test
    public void naoDeveAbrirPortaComSecagemAcontecendo () throws Exception {
        // dado:
        maquinaService.ligar(maquinaLavaSeca);
        maquinaService.fecharPorta(maquinaLavaSeca);
        secaService.secar(maquinaLavaSeca, 200, 40);
        // quando:
        // então:
        Assertions.assertThrows(Exception.class, () -> maquinaService.abrirPorta(maquinaLavaSeca));
    }
 }
