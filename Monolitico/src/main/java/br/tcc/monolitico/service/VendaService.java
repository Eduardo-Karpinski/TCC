package br.tcc.monolitico.service;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.tcc.monolitico.domain.Produto;
import br.tcc.monolitico.domain.Venda;
import br.tcc.monolitico.domain.VendaProduto;
import br.tcc.monolitico.mapper.VendaMapper;
import br.tcc.monolitico.record.VendaRecord;
import br.tcc.monolitico.repository.ProdutoRepository;
import br.tcc.monolitico.repository.VendaProdutoRepository;
import br.tcc.monolitico.repository.VendaRepository;
import br.tcc.monolitico.util.ExceptionMessage;

@Service
public class VendaService {
	
	public final VendaRepository vendaRepository;
	public final ProdutoRepository produtoRepository;
	public final VendaProdutoRepository vendaProdutoRepository;
	
	public VendaService(VendaRepository vendaRepository, ProdutoRepository produtoRepository, VendaProdutoRepository vendaProdutoRepository) {
		this.vendaRepository = vendaRepository;
		this.produtoRepository = produtoRepository;
		this.vendaProdutoRepository = vendaProdutoRepository;
	}
	
	//public ResponseEntity<Object> removeProduto(Long idVenda, Long idProduto) {}
	
	public ResponseEntity<Object> addProduto(final Long idVenda, final Long idProduto, final BigDecimal quantidade) {
		try {
			Optional<Venda> vendaOptional = vendaRepository.findById(idVenda);
			Optional<Produto> produtoOptional = produtoRepository.findById(idProduto);
			
			if (vendaOptional.isEmpty()) {
				return ExceptionMessage.returnError(HttpStatus.BAD_REQUEST,
						new NoSuchElementException("No such Venda found"));
			}
			
			if (produtoOptional.isEmpty()) {
				return ExceptionMessage.returnError(HttpStatus.BAD_REQUEST,
						new NoSuchElementException("No such Produto found"));
			}
			
			Optional<VendaProduto> vendaProdutoOptional = vendaProdutoRepository.findByProduto(produtoOptional.get());
			
			if (vendaProdutoOptional.isEmpty()) {
				VendaProduto produto = new VendaProduto();
				produto.setProduto(produtoOptional.get());
				produto.setQuantidade(quantidade);
				vendaProdutoRepository.save(produto);
				vendaOptional.get().getProdutos().add(produto);
				vendaRepository.save(vendaOptional.get());
			} else {
				VendaProduto produto = vendaProdutoOptional.get();
				produto.setProduto(produtoOptional.get());
				produto.setQuantidade(produto.getQuantidade().add(quantidade));
				vendaProdutoRepository.save(produto);
			}
			
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return ExceptionMessage.returnError(HttpStatus.INTERNAL_SERVER_ERROR, e);
		}
	}
	
	public ResponseEntity<Object> getById(final Long id) {
		try {
			Optional<Venda> vendaOptional = vendaRepository.findById(id);
			
			if (vendaOptional.isEmpty()) {
				return ExceptionMessage.returnError(HttpStatus.BAD_REQUEST,
						new NoSuchElementException("No such Venda found"));
			}
			
			return new ResponseEntity<>(VendaMapper.output(vendaOptional.get()), HttpStatus.OK);
		} catch (Exception e) {
			return ExceptionMessage.returnError(HttpStatus.INTERNAL_SERVER_ERROR, e);
		}
	}
	
	public ResponseEntity<Object> get(final Pageable page) {
		try {
			try {
				Page<Venda> findAll = vendaRepository.findAll(page);
				
				PageImpl<VendaRecord> pageImpl = new PageImpl<>(
						findAll.getContent().stream().map(VendaMapper::output).collect(Collectors.toList()), page,
						findAll.getTotalElements());
				return new ResponseEntity<>(pageImpl, HttpStatus.OK);
			} catch (Exception e) {
				return ExceptionMessage.returnError(HttpStatus.INTERNAL_SERVER_ERROR, e);
			}
		} catch (Exception e) {
			return ExceptionMessage.returnError(HttpStatus.INTERNAL_SERVER_ERROR, e);
		}
	}
	
	public ResponseEntity<Object> delete(final Long id) {
		try {
			vendaRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return ExceptionMessage.returnError(HttpStatus.INTERNAL_SERVER_ERROR, e);
		}
	}
	
	public ResponseEntity<Object> save() {
		try {
			Venda venda = new Venda();
			vendaRepository.save(venda);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return ExceptionMessage.returnError(HttpStatus.INTERNAL_SERVER_ERROR, e);
		}
	}
	
}