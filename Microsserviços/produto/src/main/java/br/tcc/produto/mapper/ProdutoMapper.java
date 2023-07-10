package br.tcc.produto.mapper;

import br.tcc.produto.domain.Fornecedor;
import br.tcc.produto.domain.Produto;
import br.tcc.produto.record.ProdutoRecord;

public class ProdutoMapper {

	public static Produto input(Long id, ProdutoRecord input, Fornecedor fornecedor) {
		Produto produto = new Produto();
		produto.setId(id);
		produto.setNome(input.nome());
		produto.setDescricao(input.descricao());
		produto.setUnidadeDeMedida(input.unidadeDeMedida());
		produto.setEan(input.ean());
		produto.setPreco(input.preco());
		produto.setCusto(input.custo());
		produto.setFornecedor(fornecedor);
		return produto;
	}

	public static ProdutoRecord output(Produto output) {
		return new ProdutoRecord(
				output.getId(),
				output.getNome(),
				output.getDescricao(),
				output.getUnidadeDeMedida(),
				output.getEan(),
				output.getPreco(),
				output.getCusto(),
				output.getFornecedor().getId());
	}

}