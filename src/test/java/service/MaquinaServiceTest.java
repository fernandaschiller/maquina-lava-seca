package service;

import enums.estadoAtual;
import model.MaquinaLavaSeca;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MaquinaServiceTest {
    private AlertaService alertaServiceMocked;
    private MaquinaServiceImpl maquinaService;
    private LavaService lavaService;
    private MaquinaLavaSeca maquinaLavaSeca;

    @BeforeEach
    public void beforeEachTest () {
        maquinaService = new MaquinaServiceImpl();
        maquinaLavaSeca = new MaquinaLavaSeca();
        lavaService = new LavaServiceImpl();
    }

    @Test
    public void verificaEstadoAtualDesligada () {
        // dado:
        // quando:
        // então:
        Assertions.assertEquals(maquinaLavaSeca.toString(), "MaquinaLavaSeca{ligada=false, portaFechada=false, temperatura=0, rotacao=0, estadoAtual='DESLIGADA'}");
    }

    @Test
    public void verificaEstadoAtualLigadaEComPortaFechada () throws Exception {
        // dado:
        maquinaService.ligar(maquinaLavaSeca);
        maquinaService.fecharPorta(maquinaLavaSeca);
        // quando:
        // então:
        Assertions.assertEquals(maquinaLavaSeca.toString(), "MaquinaLavaSeca{ligada=true, portaFechada=true, temperatura=0, rotacao=0, estadoAtual='LIGADA'}");
    }

    @Test
    public void maquinaDeveIniciarDesligada () {
        // dado:
        // quando:
        // então:
        Assertions.assertFalse(maquinaLavaSeca.isLigada());
        Assertions.assertEquals(maquinaLavaSeca.getEstadoAtual(), estadoAtual.DESLIGADA);
    }

    @Test
    public void quandoDesligadaTempERotacaoDeveSerZero () {
        // dado:
        // quando:
        // então:
        Assertions.assertEquals(0, maquinaLavaSeca.getTemperaturaAtual());
        Assertions.assertEquals(0, maquinaLavaSeca.getRotacaoAtual());
    }

    @Test
    public void podeFecharPortaQuandoMaquinaDesligada () throws Exception {
        // dado:
        // quando:
        maquinaService.fecharPorta(maquinaLavaSeca);
        // então:
        Assertions.assertTrue(maquinaLavaSeca.isPortaFechada());
    }

    @Test
    public void podeAbrirPortaQuandoDesligada () throws Exception {
        // dado:
        maquinaService.fecharPorta(maquinaLavaSeca);
        // quando:
        maquinaService.abrirPorta(maquinaLavaSeca);
        // então:
        Assertions.assertFalse(maquinaLavaSeca.isPortaFechada());
    }

    @Test
    public void ligarMaquina () throws Exception {
        // dado:
        // quando:
        maquinaService.ligar(maquinaLavaSeca);
        // então:
        Assertions.assertTrue(maquinaLavaSeca.isLigada());
        Assertions.assertEquals(maquinaLavaSeca.getEstadoAtual(), estadoAtual.LIGADA);
    }

    @Test
    public void desligarMaquina () throws Exception {
        // dado:
        maquinaService.ligar(maquinaLavaSeca);
        // quando:
        maquinaService.desligar(maquinaLavaSeca);
        // então:
        Assertions.assertFalse(maquinaLavaSeca.isLigada());
        Assertions.assertEquals(maquinaLavaSeca.getEstadoAtual(), estadoAtual.DESLIGADA);
    }

    @Test
    public void podeFecharPortaQuandoLigada () throws Exception {
        // dado:
        maquinaService.ligar(maquinaLavaSeca);
        // quando:
        maquinaService.fecharPorta(maquinaLavaSeca);
        // então:
        Assertions.assertTrue(maquinaLavaSeca.isPortaFechada());
    }

    @Test
    public void podeAbrirPortaQuandoLigada () throws Exception {
        // dado:
        maquinaService.fecharPorta(maquinaLavaSeca);
        maquinaService.ligar(maquinaLavaSeca);
        // quando:
        maquinaService.abrirPorta(maquinaLavaSeca);
        // então:
        Assertions.assertFalse(maquinaLavaSeca.isPortaFechada());
    }

    @Test
    public void quandoApenasLigadaTempERotacaoDeveSerZero () throws Exception {
        // dado:
        // quando:
        maquinaService.ligar(maquinaLavaSeca);
        // então:
        Assertions.assertEquals(0, maquinaLavaSeca.getTemperaturaAtual());
        Assertions.assertEquals(0, maquinaLavaSeca.getRotacaoAtual());
    }

    // testes negativos

    @Test
    public void portaNaoPodeSerFechadaQuandoJaFechada () throws Exception {
        // dado:
        maquinaService.fecharPorta(maquinaLavaSeca);
        // quando:
        // então:
        Assertions.assertThrows(Exception.class, () -> maquinaService.fecharPorta(maquinaLavaSeca));
    }
    @Test
    public void portaNaoPodeSerAbertaQuandoJaAberta () {
        // dado:
        // quando:
        // então:
        Assertions.assertThrows(Exception.class, () -> maquinaService.abrirPorta(maquinaLavaSeca));
    }

    @Test
    public void maquinaNaoDeveLigarQuandoJaLigada () throws Exception {
        // dado:
        maquinaService.ligar(maquinaLavaSeca);
        // quando:
        // então:
        Assertions.assertThrows(Exception.class, () -> maquinaService.ligar(maquinaLavaSeca));
    }

    @Test
    public void naoDeveFinalizarCicloComPortaAberta () {
        // dado:
        // quando:
        // então:
        Assertions.assertThrows(Exception.class, () -> maquinaService.finalizarCiclo(maquinaLavaSeca, alertaServiceMocked));
    }

    @Test
    public void naoDeveCentrifugarComMaquinaLigadaEPortaAberta () throws Exception {
        // dado:
        maquinaService.ligar(maquinaLavaSeca);
        // quando:
        // então:
        Assertions.assertThrows(Exception.class, () -> lavaService.centrifugar(maquinaLavaSeca, 200));
    }

    @Test
    public void naoDeveLavarComMaquinaLigadaEPortaAberta () throws Exception {
        // dado:
        maquinaService.ligar(maquinaLavaSeca);
        // quando:
        // então:
        Assertions.assertThrows(Exception.class, () -> lavaService.lavar(maquinaLavaSeca, 200, 40));
    }

    @Test
    public void naoDeveCentrifugarComMaquinaDesligada () {
        // dado:
        // quando:
        // então:
        Assertions.assertThrows(Exception.class, () -> lavaService.centrifugar(maquinaLavaSeca, 200));
    }

    @Test
    public void naoDeveLavarComMaquinaDesligada () {
        // dado:
        // quando:
        // então:
        Assertions.assertThrows(Exception.class, () -> lavaService.lavar(maquinaLavaSeca, 200, 40));
    }

    @Test
    public void naoDeveLavarComMaquinaDesligadaEPortaAberta () {
        // dado:
        // quando:
        // então:
        Assertions.assertThrows(Exception.class, () -> lavaService.lavar(maquinaLavaSeca, 200, 40));
    }

    @Test
    public void naoDeveLavarComMaquinaDesligadaEPortaFechada () throws Exception {
        // dado:
        maquinaService.fecharPorta(maquinaLavaSeca);
        // quando:
        // então:
        Assertions.assertThrows(Exception.class, () -> lavaService.lavar(maquinaLavaSeca, 200, 40));
    }

    @Test
    public void naoDeveCentrifucarComMaquinaDesligadaEPortaAberta () {
        // dado:
        // quando:
        // então:
        Assertions.assertThrows(Exception.class, () -> lavaService.centrifugar(maquinaLavaSeca, 200));
    }

    @Test
    public void naoDeveCentrifugarComMaquinaDesligadaEPortaFechada () throws Exception {
        // dado:
        maquinaService.fecharPorta(maquinaLavaSeca);
        // quando:
        // então:
        Assertions.assertThrows(Exception.class, () -> lavaService.centrifugar(maquinaLavaSeca, 200));
    }
}
