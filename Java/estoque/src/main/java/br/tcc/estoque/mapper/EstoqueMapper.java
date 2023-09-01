package br.tcc.estoque.mapper;

import br.tcc.estoque.domain.Estoque;
import br.tcc.estoque.domain.Produto;
import br.tcc.estoque.record.EstoqueRecord;

public class EstoqueMapper {

	public static Estoque input(Long id, EstoqueRecord input, Produto produto) {
		Estoque estoque = new Estoque();
		estoque.setId(id);
		estoque.setProduto(produto);
		estoque.setQuantidade(input.quantidade());
		estoque.setQuantidadeMinima(input.quantidadeMinima());
		return estoque;
	}

	public static EstoqueRecord output(Estoque output) {
		return new EstoqueRecord(
				output.getId(),
				output.getProduto().getId(), 
				output.getQuantidade(),
				output.getQuantidadeMinima());
	}

}