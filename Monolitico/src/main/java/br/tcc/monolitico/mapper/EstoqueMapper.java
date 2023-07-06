package br.tcc.monolitico.mapper;

import br.tcc.monolitico.domain.Estoque;
import br.tcc.monolitico.domain.Produto;
import br.tcc.monolitico.record.EstoqueRecord;

public class EstoqueMapper {

	public static Estoque input(Long id, EstoqueRecord input, Produto produto) {
		Estoque estoque = new Estoque();
		estoque.setId(id);
		estoque.setProduto(produto);
		estoque.setQuantidade(input.Quantidade());
		estoque.setQuantidadeMinima(input.QuantidadeMinima());
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