package br.tcc.relatorio.service;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import br.tcc.relatorio.domain.Estoque;
import br.tcc.relatorio.domain.Produto;
import br.tcc.relatorio.domain.Venda;
import br.tcc.relatorio.domain.VendaProduto;
import br.tcc.relatorio.feign.EstoqueFeign;
import br.tcc.relatorio.feign.ProdutoFeign;
import br.tcc.relatorio.feign.VendaFeign;

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
	
	public ModelAndView getRelatorio(final LocalDateTime data1, final LocalDateTime data2) {
		try {
			ModelAndView modelAndView = new ModelAndView("relatorio");
			
			ObjectMapper objectMapper = new ObjectMapper();
			ObjectNode objectNode = objectMapper.createObjectNode();
			
			Optional<List<Venda>> vendaOptional = vendaFeign.findAllByIsFinalizadaTrueAndDataBetween(data1.toString(), data2.toString());
			Optional<List<Estoque>> estoqueOptional = estoqueFeign.findAllEstoqueBaixo();
			Optional<List<Produto>> produtoOptional = produtoFeign.getAllByValorBaixo();
			
			NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
			
			if (vendaOptional.isPresent()) {
				objectNode.put("totalDeVendas", vendaOptional.get().stream().count());
				objectNode.put("valorVendido", numberFormat.format(vendaOptional.get().stream()
						.map(Venda::getProdutos)
						.flatMap(produtos -> produtos.stream())
						.map(produto -> produto.getPreco().multiply(produto.getQuantidade()))
						.reduce(BigDecimal.ZERO, BigDecimal::add)));
				objectNode.put("quantidadeDeProdutosVendidos", vendaOptional.get().stream()
						.map(Venda::getProdutos)
						.flatMap(produtos -> produtos.stream())
						.map(VendaProduto::getQuantidade)
						.reduce(BigDecimal.ZERO, BigDecimal::add));
			}
			
			if (estoqueOptional.isPresent()) {
				ArrayNode estoquesBaixos = objectMapper.createArrayNode();
				estoqueOptional.get().forEach(estoque -> {
					ObjectNode estoqueJson = objectMapper.createObjectNode();
					estoqueJson.put("produto", estoque.getProduto().getDescricao() + " ("+estoque.getProduto().getEan()+")");
					estoqueJson.put("quantidade", estoque.getQuantidade());
					estoqueJson.put("quantidadeMinima", estoque.getQuantidadeMinima());
					estoquesBaixos.add(estoqueJson);
				});
				objectNode.set("estoquesBaixos", estoquesBaixos);
			}
			
			if (produtoOptional.isPresent()) {
				ArrayNode produtosComPrecoBaixo = objectMapper.createArrayNode();
				produtoOptional.get().forEach(produto -> {
					ObjectNode produtoJson = objectMapper.createObjectNode();
					produtoJson.put("produto", produto.getDescricao() + " ("+produto.getEan()+")");
					produtoJson.put("preco", numberFormat.format(produto.getPreco()));
					produtoJson.put("custo", numberFormat.format(produto.getCusto()));
					produtosComPrecoBaixo.add(produtoJson);
				});
				objectNode.set("produtosComPrecoBaixo", produtosComPrecoBaixo);
			}
			
			String json = objectMapper.writeValueAsString(objectNode);
			modelAndView.addObject("data", json);
			return modelAndView;
		} catch (Exception e) {
			e.printStackTrace();
			ModelAndView modelAndView = new ModelAndView(null, HttpStatus.INTERNAL_SERVER_ERROR);
			return modelAndView;
		}
	}
	
}