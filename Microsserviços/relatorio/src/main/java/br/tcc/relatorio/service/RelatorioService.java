package br.tcc.relatorio.service;

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

import br.tcc.relatorio.domain.Estoque;
import br.tcc.relatorio.domain.Produto;
import br.tcc.relatorio.domain.Venda;
import br.tcc.relatorio.domain.VendaProduto;
import br.tcc.relatorio.feign.EstoqueFeign;
import br.tcc.relatorio.feign.ProdutoFeign;
import br.tcc.relatorio.feign.VendaFeign;
import br.tcc.relatorio.util.ExceptionMessage;

@Service
public class RelatorioService {

	public EstoqueFeign estoqueFeign;
	public VendaFeign vendaFeign;
	public ProdutoFeign produtoFeign;
	
	public RelatorioService(EstoqueFeign estoqueFeign, VendaFeign vendaFeign, ProdutoFeign produtoFeign) {
		this.estoqueFeign = estoqueFeign;
		this.vendaFeign = vendaFeign;
		this.produtoFeign = produtoFeign;
	}
	
	@SuppressWarnings("unchecked")
	public ResponseEntity<Object> getRelatorio(final LocalDateTime data1, final LocalDateTime data2) {
		try {
			JSONObject json = new JSONObject();
			Optional<List<Venda>> vendaOptional = vendaFeign.findAllByIsFinalizadaTrueAndDataBetween(data1.toString(), data2.toString());
			Optional<List<Estoque>> estoqueOptional = estoqueFeign.findAllEstoqueBaixo();
			Optional<List<Produto>> produtoOptional = produtoFeign.getAllByValorBaixo();
			
			if (vendaOptional.isPresent()) {
				json.put("totalDeVendas", vendaOptional.get().stream().count());
				json.put("valorVendido", NumberFormat.getCurrencyInstance().format(vendaOptional.get().stream()
						.map(Venda::getProdutos)
						.flatMap(produtos -> produtos.stream())
						.map(produto -> produto.getPreco().multiply(produto.getQuantidade()))
						.reduce(BigDecimal.ZERO, BigDecimal::add)));
				json.put("quantidadeDeProdutosVendidos", vendaOptional.get().stream()
						.map(Venda::getProdutos)
						.flatMap(produtos -> produtos.stream())
						.map(VendaProduto::getQuantidade)
						.reduce(BigDecimal.ZERO, BigDecimal::add));
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
				json.put("estoquesBaixos", estoquesBaixos);
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
				json.put("ProdutoComPreçoBaixo", estoquesBaixos);
			}
			
			return new ResponseEntity<>(json, HttpStatus.OK);
		} catch (Exception e) {
			return ExceptionMessage.returnError(HttpStatus.INTERNAL_SERVER_ERROR, e);
		}
	}
	
}