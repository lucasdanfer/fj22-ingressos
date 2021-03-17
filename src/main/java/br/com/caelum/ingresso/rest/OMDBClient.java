package br.com.caelum.ingresso.rest;

import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class OMDBClient {
	
	public Optional<DetalhesDoFilme> buscaDadosNoOmdb(String nomeDoFilme) {
		
		RestTemplate client = new RestTemplate();
		
		try {
			
			return Optional.ofNullable(client.getForObject("https://omdb-fj22.herokuapp.com/movie?title=" + nomeDoFilme.replace(" ", "+"), DetalhesDoFilme.class));
			
		} catch (RestClientException ex) {
			
			return Optional.empty();
			
		}
		
	}

}
