package br.com.caelum.ingresso.model.descontos;

import java.math.BigDecimal;

public class SemDesconto implements Desconto {

	@Override
	public BigDecimal valorComDesconto(BigDecimal valorOriginal) {
		return valorOriginal;
	}

}
