package br.tcc.monolitico.record;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProdutoRecord(
		Long id,
		@NotNull @Size(min = 3, max = 100) String nome,
		@NotNull @Size(min = 3, max = 100) String descricao,
		@NotNull @Size(min = 1, max = 15) String unidadeDeMedida,
		@NotNull @Size(min = 3, max = 100) String ean,
		@NotNull @DecimalMin(value = "0.0", inclusive = false) BigDecimal preco,
		@NotNull @DecimalMin(value = "0.0", inclusive = false) BigDecimal custo,
		@NotNull Long idFornecedor) {}