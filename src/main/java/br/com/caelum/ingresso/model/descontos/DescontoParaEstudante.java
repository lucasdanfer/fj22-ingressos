package br.com.caelum.ingresso.model.descontos;

import java.math.BigDecimal;

public class DescontoParaEstudante implements Desconto {

	@Override
	public BigDecimal valorComDesconto(BigDecimal valorOriginal) {
		return valorOriginal.divide(new BigDecimal(2));
	}

}
