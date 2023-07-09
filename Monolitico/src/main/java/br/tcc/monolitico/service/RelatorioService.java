package br.tcc.monolitico.service;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.tcc.monolitico.domain.Estoque;
import br.tcc.monolitico.domain.Produto;
import br.tcc.monolitico.domain.Venda;
import br.tcc.monolitico.domain.VendaProduto;
import br.tcc.monolitico.repository.EstoqueRepository;
import br.tcc.monolitico.repository.ProdutoRepository;
import br.tcc.monolitico.repository.VendaRepository;
import br.tcc.monolitico.util.ExceptionMessage;

@Service
public class RelatorioService {

	public EstoqueRepository estoqueRepository;
	public VendaRepository vendaRepository;
	public ProdutoRepository produtoRepository;
	
	public RelatorioService(EstoqueRepository estoqueRepository, VendaRepository vendaRepository, ProdutoRepository produtoRepository) {
		this.estoqueRepository = estoqueRepository;
		this.vendaRepository = vendaRepository;
		this.produtoRepository = produtoRepository;
	}
	
	@SuppressWarnings("unchecked")
	public ResponseEntity<Object> getRelatorio(final LocalDateTime data1, final LocalDateTime data2) {
		try {
			JSONObject json = new JSONObject();
			Optional<List<Venda>> vendaOptional = vendaRepository.findAllByIsFinalizadaTrueAndDataBetween(data1, data2);
			Optional<List<Estoque>> estoqueOptional = estoqueRepository.findAllEstoqueBaixos();
			Optional<List<Produto>> produtoOptional = produtoRepository.getAllByValorBaixo();
			
			if (vendaOptional.isPresent()) {
				json.put("Total de vendas", vendaOptional.get().stream().count());
				json.put("Valor Vendido", NumberFormat.getCurrencyInstance().format(vendaOptional.get().stream()
						.map(Venda::getProdutos)
						.flatMap(produtos -> produtos.stream())
						.map(produto -> produto.getProduto().getPreco().multiply(produto.getQuantidade()))
						.reduce(BigDecimal.ZERO, BigDecimal::add)));
				json.put("Quantidade de produtos vendidos", vendaOptional.get().stream()
						.map(Venda::getProdutos)
						.flatMap(produtos -> produtos.stream())
						.map(VendaProduto::getQuantidade)
						.findFirst().orElse(BigDecimal.ZERO));
			}
			
			if (estoqueOptional.isPresent()) {
				JSONArray estoquesBaixos = new JSONArray();
				estoqueOptional.get().forEach(estoque -> {
					JSONObject estoqueJson = new JSONObject();
					estoqueJson.put("Produto", estoque.getProduto().getDescricao() + " ("+estoque.getProduto().getEan()+")");
					estoqueJson.put("quantidade", estoque.getQuantidade());
					estoqueJson.put("quantidadeMinima", estoque.getQuantidadeMinima());
					estoquesBaixos.add(estoqueJson);
				});
				json.put("estoques baixos", estoquesBaixos);
			}
			
			if (produtoOptional.isPresent()) {
				JSONArray estoquesBaixos = new JSONArray();
				produtoOptional.get().forEach(produto -> {
					JSONObject produtoJson = new JSONObject();
					produtoJson.put("Produto", produto.getDescricao() + " ("+produto.getEan()+")");
					produtoJson.put("Preço", produto.getPreco());
					produtoJson.put("Custo", produto.getCusto());
					estoquesBaixos.add(produtoJson);
				});
				json.put("Produto com preço baixo", estoquesBaixos);
			}
			
			return new ResponseEntity<>(json, HttpStatus.OK);
		} catch (Exception e) {
			return ExceptionMessage.returnError(HttpStatus.INTERNAL_SERVER_ERROR, e);
		}
	}
	
}