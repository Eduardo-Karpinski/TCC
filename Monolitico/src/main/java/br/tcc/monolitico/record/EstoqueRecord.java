package br.tcc.monolitico.record;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public record EstoqueRecord(
		Long id,
		@NotNull Long idProduto,
		@NotNull @DecimalMin(value = "0.0", inclusive = false) BigDecimal Quantidade,
		@NotNull @DecimalMin(value = "0.0", inclusive = false) BigDecimal QuantidadeMinima) {}