package br.tcc.monolitico.record;

import java.math.BigDecimal;

import br.tcc.monolitico.domain.Fornecedor;

public record ProdutoRecord(
		String nome,
		String descricao,
		String unidadeDeMedida,
		String ean,
		BigDecimal preco,
		BigDecimal custo,
		Fornecedor fornecedor) {}