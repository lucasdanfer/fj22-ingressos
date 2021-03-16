package br.com.caelum.ingresso.model.descontos;

import java.math.BigDecimal;

public class DescontoParaBancos implements Desconto {

	@Override
	public BigDecimal valorComDesconto(BigDecimal valorOriginal) {
		return valorOriginal.multiply(new BigDecimal("0.7"));
	}

}
