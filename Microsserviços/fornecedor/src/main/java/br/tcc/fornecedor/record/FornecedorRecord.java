package br.tcc.fornecedor.record;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record FornecedorRecord(
		Long id,
		@NotNull @Size(min = 3, max = 100) String nome,
		@NotNull @Size(min = 3, max = 100) String cnpj,
		@NotNull @Size(min = 3, max = 100) String telefone,
		@NotNull @Size(min = 3, max = 100) String cep,
		@NotNull @Size(min = 3, max = 100) String endereco,
		@NotNull @Size(min = 3, max = 100) String complemento,
		@NotNull @Size(min = 3, max = 100) String bairro,
		@NotNull @Size(min = 3, max = 100) String municipio,
		@NotNull @Size(min = 3, max = 100) String estado) {}