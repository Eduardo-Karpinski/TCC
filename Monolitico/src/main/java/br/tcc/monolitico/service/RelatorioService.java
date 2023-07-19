package br.tcc.monolitico.service;

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

import br.tcc.monolitico.domain.Estoque;
import br.tcc.monolitico.domain.Produto;
import br.tcc.monolitico.domain.Venda;
import br.tcc.monolitico.domain.VendaProduto;
import br.tcc.monolitico.repository.EstoqueRepository;
import br.tcc.monolitico.repository.ProdutoRepository;
import br.tcc.monolitico.repository.VendaRepository;

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
	
	public ModelAndView getRelatorio(final LocalDateTime data1, final LocalDateTime data2) {
		try {
			
			ModelAndView modelAndView = new ModelAndView("relatorio");
			
			ObjectMapper objectMapper = new ObjectMapper();
			ObjectNode objectNode = objectMapper.createObjectNode();
			
			Optional<List<Venda>> vendaOptional = vendaRepository.findAllByIsFinalizadaTrueAndDataBetween(data1, data2);
			Optional<List<Estoque>> estoqueOptional = estoqueRepository.findAllEstoqueBaixo();
			Optional<List<Produto>> produtoOptional = produtoRepository.getAllByValorBaixo();
			
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
				ArrayNode estoquesBaixos = objectMapper.createArrayNode();
				produtoOptional.get().forEach(produto -> {
					ObjectNode produtoJson = objectMapper.createObjectNode();
					produtoJson.put("produto", produto.getDescricao() + " ("+produto.getEan()+")");
					produtoJson.put("preco", numberFormat.format(produto.getPreco()));
					produtoJson.put("custo", numberFormat.format(produto.getCusto()));
					estoquesBaixos.add(produtoJson);
				});
				objectNode.set("produtosComPrecoBaixo", estoquesBaixos);
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