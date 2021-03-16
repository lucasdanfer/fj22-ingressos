package br.com.caelum.ingresso.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.caelum.ingresso.dao.FilmeDao;
import br.com.caelum.ingresso.dao.SalaDao;
import br.com.caelum.ingresso.dao.SessaoDao;
import br.com.caelum.ingresso.model.Ingresso;
import br.com.caelum.ingresso.model.Sessao;
import br.com.caelum.ingresso.model.descontos.Desconto;
import br.com.caelum.ingresso.model.descontos.DescontoParaBancos;
import br.com.caelum.ingresso.model.descontos.DescontoParaEstudante;
import br.com.caelum.ingresso.model.descontos.SemDesconto;
import br.com.caelum.ingresso.model.form.SessaoForm;

@Controller
public class SessaoController {

	@Autowired
	private FilmeDao filmeDao;

	@Autowired
	private SalaDao salaDao;

	@Autowired
	private SessaoDao sessaoDao;

	@GetMapping("/admin/sessao")
	public ModelAndView form(@RequestParam("salaId") Integer salaId) {
		ModelAndView modelAndView = new ModelAndView("sessao/sessao");

		modelAndView.addObject("sala", salaDao.findOne(salaId));
		modelAndView.addObject("filmes", filmeDao.findAll());

		return modelAndView;
	}

	@PostMapping("/admin/sessao")
	@Transactional
	public ModelAndView salva(@Valid SessaoForm form) {

		Sessao novaSessao = form.toSessao(salaDao, filmeDao);
		LocalDateTime inicioNovaSessao = novaSessao.getHorario().atDate(LocalDate.now());
		LocalDateTime fimNovaSessao = inicioNovaSessao.plus(novaSessao.getFilme().getDuracao());
		
		// sessão nao pode terminar no proximo dia
		LocalDateTime meiaNoite = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
		
		if (fimNovaSessao.isAfter(meiaNoite)) {
			throw new RuntimeException("Sessão termina em outro dia");
		}

		String tipoDesconto = "";
		Desconto desconto;
		
		switch(tipoDesconto) {
		case "ESTUDANTE":
			desconto = new DescontoParaEstudante();
			break;
		case "BANCO":
			desconto = new DescontoParaBancos();
			break;
		default:
			desconto = new SemDesconto();
		}
		
		
		Ingresso ingressoSemDesconto = new Ingresso(novaSessao, desconto);
		
		
		// recupera lista de sessoes da sala
		List<Sessao> sessoesDaSala = sessaoDao.buscaSessoesDaSala(novaSessao.getSala());

		// percorre lista de sessões
//		for (Sessao s : sessoesDaSala) {
//
//			LocalDateTime inicioSessaoAntiga = s.getHorario().atDate(LocalDate.now());
//			LocalDateTime fimSessaoAntiga = inicioSessaoAntiga.plus(s.getFilme().getDuracao());
//
//			// Cenario 1: O fim da nova sessão, nao pode ser depois do começo da antiga
//			boolean ehDepoisDoComecoDaAntiga = fimNovaSessao.isAfter(inicioSessaoAntiga);
//
//			// Cenario 2: O começo da nova sessão, nao pode ser antes do fim da antiga
//			boolean ehAntesDoFimDaAntiga = inicioNovaSessao.isBefore(fimSessaoAntiga);
//
//			// se for conflitante, dá exception
//			if (ehDepoisDoComecoDaAntiga || ehAntesDoFimDaAntiga) {
//				throw new RuntimeException("Horario Conflitante");
//			}
//
//		}

		// se nao deu exception nenhuma, salva
		sessaoDao.save(novaSessao);

		return new ModelAndView("redirect:/admin/sala/" + form.getSalaId() + "/sessoes");
	}

}
