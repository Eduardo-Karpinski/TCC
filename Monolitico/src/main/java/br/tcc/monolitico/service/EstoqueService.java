package br.tcc.monolitico.service;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.tcc.monolitico.domain.Estoque;
import br.tcc.monolitico.domain.Produto;
import br.tcc.monolitico.mapper.EstoqueMapper;
import br.tcc.monolitico.record.EstoqueRecord;
import br.tcc.monolitico.repository.EstoqueRepository;
import br.tcc.monolitico.repository.ProdutoRepository;
import br.tcc.monolitico.util.ExceptionMessage;

@Service
public class EstoqueService {

	public final EstoqueRepository estoqueRepository;
	public final ProdutoRepository produtoRepository;
	
	public EstoqueService(EstoqueRepository estoqueRepository, ProdutoRepository produtoRepository) {
		this.estoqueRepository = estoqueRepository;
		this.produtoRepository = produtoRepository;
	}
	
	public ResponseEntity<Object> getById(final Long id) {
		try {
			Optional<Estoque> produtoOptional = estoqueRepository.findById(id);
			
			if (produtoOptional.isEmpty()) {
				return ExceptionMessage.returnError(HttpStatus.BAD_REQUEST,
						new NoSuchElementException("No such Estoque found"));
			}
			
			return new ResponseEntity<>(EstoqueMapper.output(produtoOptional.get()), HttpStatus.OK);
		} catch (Exception e) {
			return ExceptionMessage.returnError(HttpStatus.INTERNAL_SERVER_ERROR, e);
		}
	}
	
	public ResponseEntity<Object> get(final Pageable page) {
		try {
			Page<Estoque> findAll = estoqueRepository.findAll(page);
			
			PageImpl<EstoqueRecord> pageImpl = new PageImpl<>(
					findAll.getContent().stream().map(EstoqueMapper::output).collect(Collectors.toList()), page,
					findAll.getTotalElements());
			return new ResponseEntity<>(pageImpl, HttpStatus.OK);
		} catch (Exception e) {
			return ExceptionMessage.returnError(HttpStatus.INTERNAL_SERVER_ERROR, e);
		}
	}
	
	public ResponseEntity<Object> delete(final Long id) {
		try {
			estoqueRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return ExceptionMessage.returnError(HttpStatus.INTERNAL_SERVER_ERROR, e);
		}
	}
	
	public ResponseEntity<Object> update(final Long id, final EstoqueRecord dto) {
		try {
			Optional<Produto> produtoOptional = produtoRepository.findById(dto.idProduto());
			Optional<Estoque> estoqueOptional = estoqueRepository.findById(id);
			
			if (produtoOptional.isEmpty()) {
				return ExceptionMessage.returnError(HttpStatus.BAD_REQUEST,
						new NoSuchElementException("No such Produto found"));
			}
			
			if (estoqueOptional.isEmpty()) {
				return ExceptionMessage.returnError(HttpStatus.BAD_REQUEST,
						new NoSuchElementException("No such Estoque found"));
			}
			
			Estoque estoque = EstoqueMapper.input(id, dto, produtoOptional.get());
			estoqueRepository.save(estoque);
			return new ResponseEntity<>(HttpStatus.OK);			
		} catch (Exception e) {
			return ExceptionMessage.returnError(HttpStatus.INTERNAL_SERVER_ERROR, e);
		}
	}
	
	public ResponseEntity<Object> save(final EstoqueRecord dto) {
		try {
			Optional<Produto> produtoOptional = produtoRepository.findById(dto.idProduto());
			
			if (produtoOptional.isEmpty()) {
				return ExceptionMessage.returnError(HttpStatus.BAD_REQUEST,
						new NoSuchElementException("No such Produto found"));
			}
			
			Estoque estoque = EstoqueMapper.input(null, dto, produtoOptional.get());
			estoqueRepository.save(estoque);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return ExceptionMessage.returnError(HttpStatus.INTERNAL_SERVER_ERROR, e);
		}
	}
	
}