package br.com.caelum.ingresso.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import br.com.caelum.ingresso.model.Filme;
import br.com.caelum.ingresso.model.Sala;
import br.com.caelum.ingresso.model.Sessao;

@Repository
public class SessaoDao {

	@PersistenceContext
	private EntityManager manager;

	public void save(Sessao sessao) {
		manager.persist(sessao);
	}

	public List<Sessao> buscaSessoesDaSala(Sala sala) {

		return manager.createQuery("select s from Sessao s where s.sala = :x", Sessao.class).setParameter("x", sala)
				.getResultList();

	}
	
	public List<Sessao> buscaSessoesDoFilme(Filme filme){
		
		return manager.createQuery("select s from Sessao s where s.filme = :filmeDoParametro", Sessao.class)
				.setParameter("filmeDoParametro", filme).getResultList();
		
	}

}
