package br.tcc.venda.record;

import java.time.LocalDateTime;
import java.util.List;

import br.tcc.venda.domain.VendaProduto;

public record VendaRecord(
		Long id,
		List<VendaProduto> produtos,
		LocalDateTime data,
		Boolean isFinalizada) {}