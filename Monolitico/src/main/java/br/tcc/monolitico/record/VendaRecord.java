package br.tcc.monolitico.record;

import java.time.LocalDateTime;
import java.util.List;

import br.tcc.monolitico.domain.VendaProduto;

public record VendaRecord(
		Long id,
		List<VendaProduto> produtos,
		LocalDateTime data,
		Boolean isFinalizada) {}