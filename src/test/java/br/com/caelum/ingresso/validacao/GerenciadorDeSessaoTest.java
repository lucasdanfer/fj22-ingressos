package br.com.caelum.ingresso.validacao;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.ingresso.model.Filme;
import br.com.caelum.ingresso.model.Sala;
import br.com.caelum.ingresso.model.Sessao;
import br.com.ingresso.validacao.GerenciadorDeSessao;

public class GerenciadorDeSessaoTest {

//	Não sermos capazes de adicionar uma sessão no mesmo horário de começo de outra.
//	Não podermos adicionar uma sessão cuja duração conflite com o horário da próxima sessão.
//	Não podermos adicionar uma sessão com inicio que conflite com a duração da sessão que está passando.

//	Podermos adicionar se o horário não conflitar com o horário da sessão anterior.
//	Podermos adicionar se a duração da sessão não conflitar com o horário da próxima sessão.

	private Filme rogueOne;
	private Sala sala3D;
	private Sessao sessaoDasDez;
	private Sessao sessaoDasTreze;
	private Sessao sessaoDasDezoito;

	@Before
	public void preparaSessoes() {
		this.rogueOne = new Filme("Rogue One", Duration.ofMinutes(120), "SCI-FI");
		this.sala3D = new Sala("Sala 3D");
		this.sessaoDasDez = new Sessao(LocalTime.parse("10:00:00"), rogueOne, sala3D);
		this.sessaoDasTreze = new Sessao(LocalTime.parse("13:00:00"), rogueOne, sala3D);
		this.sessaoDasDezoito = new Sessao(LocalTime.parse("18:00:00"), rogueOne, sala3D);
	}

	// Podermos adicionar se não tiver nenhuma sessão.
	@Test
	public void deveAdicionarSeNaoTiverNenhumaSessao() {
		
		// simulo que não tem nenhuma sessão no banco de dados
		List<Sessao> listaVazia = new ArrayList<Sessao>();
		GerenciadorDeSessao gerenciadorDeSessao = new GerenciadorDeSessao(listaVazia);
		
		// chama o codigo que queremos testar
		boolean resultado = gerenciadorDeSessao.cabe(sessaoDasDez);

		// verificar se o metodo está agindo como o esperado
		Assert.assertTrue(resultado);
		Assert.assertEquals(0, listaVazia.size());

	}

}
