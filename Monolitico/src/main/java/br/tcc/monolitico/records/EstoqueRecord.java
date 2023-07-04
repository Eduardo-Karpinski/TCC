package br.tcc.monolitico.records;

import java.math.BigDecimal;

import br.tcc.monolitico.domain.Produto;

public record EstoqueRecord(
		Produto produto,
		BigDecimal Quantidade,
		BigDecimal QuantidadeMinima) {}