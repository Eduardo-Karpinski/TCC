package br.tcc.estoque.record;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public record EstoqueRecord(
		Long id,
		@NotNull Long idProduto,
		@NotNull @DecimalMin(value = "0.0", inclusive = true) BigDecimal quantidade,
		@NotNull @DecimalMin(value = "0.0", inclusive = true) BigDecimal quantidadeMinima) {}