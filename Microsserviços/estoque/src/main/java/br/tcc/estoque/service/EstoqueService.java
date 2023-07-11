package br.tcc.estoque.service;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.tcc.estoque.domain.Estoque;
import br.tcc.estoque.domain.Produto;
import br.tcc.estoque.feign.ProdutoFeign;
import br.tcc.estoque.mapper.EstoqueMapper;
import br.tcc.estoque.record.EstoqueRecord;
import br.tcc.estoque.repository.EstoqueRepository;
import br.tcc.estoque.util.ExceptionMessage;

@Service
public class EstoqueService {

	public final EstoqueRepository estoqueRepository;
	public final ProdutoFeign produtoFeign;
	
	public EstoqueService(EstoqueRepository estoqueRepository, ProdutoFeign produtoFeign) {
		this.estoqueRepository = estoqueRepository;
		this.produtoFeign = produtoFeign;
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
	
	public ResponseEntity<Object> update(final Long id, final EstoqueRecord dto) {
		try {
			Optional<Produto> produtoOptional = produtoFeign.findById(dto.idProduto());
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
			Optional<Produto> produtoOptional = produtoFeign.findById(dto.idProduto());
			
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