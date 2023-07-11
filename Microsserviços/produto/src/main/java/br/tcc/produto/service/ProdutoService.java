package br.tcc.produto.service;

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

import br.tcc.produto.domain.Fornecedor;
import br.tcc.produto.domain.Produto;
import br.tcc.produto.feign.EstoqueFeign;
import br.tcc.produto.feign.FornecedorFeign;
import br.tcc.produto.mapper.ProdutoMapper;
import br.tcc.produto.record.EstoqueRecord;
import br.tcc.produto.record.ProdutoRecord;
import br.tcc.produto.repository.ProdutoRepository;
import br.tcc.produto.util.ExceptionMessage;

@Service
public class ProdutoService {
	
	public final ProdutoRepository produtoRepository;
	public final FornecedorFeign fornecedorFeign;
	public final EstoqueFeign estoqueFeign;
	
	public ProdutoService(ProdutoRepository produtoRepository, FornecedorFeign fornecedorFeign, EstoqueFeign estoqueFeign) {
		this.produtoRepository = produtoRepository;
		this.fornecedorFeign = fornecedorFeign;
		this.estoqueFeign = estoqueFeign;
	}
	
	public ResponseEntity<Object> getById(final Long id) {
		try {
			Optional<Produto> produtoOptional = produtoRepository.findById(id);
			
			if (produtoOptional.isEmpty()) {
				return ExceptionMessage.returnError(HttpStatus.BAD_REQUEST,
						new NoSuchElementException("No such Produto found"));
			}
			
			return new ResponseEntity<>(ProdutoMapper.output(produtoOptional.get()), HttpStatus.OK);
		} catch (Exception e) {
			return ExceptionMessage.returnError(HttpStatus.INTERNAL_SERVER_ERROR, e);
		}
	}
	
	public ResponseEntity<Object> get(final Pageable page) {
		try {
			Page<Produto> findAll = produtoRepository.findAll(page);
			
			PageImpl<ProdutoRecord> pageImpl = new PageImpl<>(
					findAll.getContent().stream().map(ProdutoMapper::output).collect(Collectors.toList()), page,
					findAll.getTotalElements());
			return new ResponseEntity<>(pageImpl, HttpStatus.OK);
		} catch (Exception e) {
			return ExceptionMessage.returnError(HttpStatus.INTERNAL_SERVER_ERROR, e);
		}
		
	}
	
	public ResponseEntity<Object> delete(final Long id) {
		try {
			produtoRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return ExceptionMessage.returnError(HttpStatus.INTERNAL_SERVER_ERROR, e);
		}
	}

	public ResponseEntity<Object> update(final Long id, final ProdutoRecord dto) {
		try {
			Optional<Fornecedor> fornecedorOptional = fornecedorFeign.findById(dto.idFornecedor());
			Optional<Produto> produtoOptional = produtoRepository.findById(id);
			
			if (fornecedorOptional.isEmpty()) {
				return ExceptionMessage.returnError(HttpStatus.BAD_REQUEST,
						new NoSuchElementException("No such Fornecedor found"));
			}
			
			if (produtoOptional.isEmpty()) {
				return ExceptionMessage.returnError(HttpStatus.BAD_REQUEST,
						new NoSuchElementException("No such Produto found"));
			}
			
			Produto produto = ProdutoMapper.input(id, dto, fornecedorOptional.get());
			produtoRepository.save(produto);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return ExceptionMessage.returnError(HttpStatus.INTERNAL_SERVER_ERROR, e);
		}
	}
	
	public ResponseEntity<Object> save(final ProdutoRecord dto) {
		try {
			Optional<Fornecedor> fornecedorOptional = fornecedorFeign.findById(dto.idFornecedor());
			
			if (fornecedorOptional.isPresent()) {
				Produto produto = ProdutoMapper.input(null, dto, fornecedorOptional.get());
				produtoRepository.save(produto);
				
				estoqueFeign.save(new EstoqueRecord(null, produto.getId(), BigDecimal.ZERO, BigDecimal.ZERO));
				
				return new ResponseEntity<>(HttpStatus.OK);
			}
			
			return ExceptionMessage.returnError(HttpStatus.BAD_REQUEST,
					new NoSuchElementException("No such Fornecedor found"));
		} catch (Exception e) {
			return ExceptionMessage.returnError(HttpStatus.INTERNAL_SERVER_ERROR, e);
		}
	}

}