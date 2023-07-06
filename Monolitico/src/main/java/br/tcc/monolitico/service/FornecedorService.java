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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import br.tcc.monolitico.domain.Fornecedor;
import br.tcc.monolitico.mapper.FornecedorMapper;
import br.tcc.monolitico.record.FornecedorRecord;
import br.tcc.monolitico.repository.FornecedorRepository;
import br.tcc.monolitico.util.ExceptionMessage;

@Service
public class FornecedorService {

	public final FornecedorRepository fornecedorRepository;

	public FornecedorService(FornecedorRepository fornecedorRepository) {
		this.fornecedorRepository = fornecedorRepository;
	}
	
	public ResponseEntity<Object> getById(final Long id) {
		try {
			Optional<Fornecedor> fornecedorOptional = fornecedorRepository.findById(id);
			
			if (fornecedorOptional.isEmpty()) {
				return ExceptionMessage.returnError(HttpStatus.BAD_REQUEST,
						new NoSuchElementException("No such Fornecedor found"));
			}
			
			return new ResponseEntity<>(FornecedorMapper.output(fornecedorOptional.get()), HttpStatus.OK);
		} catch (Exception e) {
			return ExceptionMessage.returnError(HttpStatus.INTERNAL_SERVER_ERROR, e);
		}
	}
	
	public ResponseEntity<Object> get(final Pageable page) {
		try {
			Page<Fornecedor> findAll = fornecedorRepository.findAll(page);
			PageImpl<FornecedorRecord> pageImpl = new PageImpl<>(
					findAll.getContent().stream().map(FornecedorMapper::output).collect(Collectors.toList()), page,
					findAll.getTotalElements());
			return new ResponseEntity<>(pageImpl, HttpStatus.OK);
		} catch (Exception e) {
			return ExceptionMessage.returnError(HttpStatus.INTERNAL_SERVER_ERROR, e);
		}
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<Object> delete(final Long id) {
		try {
			fornecedorRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return ExceptionMessage.returnError(HttpStatus.INTERNAL_SERVER_ERROR, e);
		}
	}

	@PutMapping("{id}")
	public ResponseEntity<Object> update(final Long id, final FornecedorRecord dto) {
		try {
			if (fornecedorRepository.existsById(id)) {
				Fornecedor fornecedor = FornecedorMapper.input(id, dto);
				fornecedorRepository.save(fornecedor);
				return new ResponseEntity<>(HttpStatus.OK);
			}
			return ExceptionMessage.returnError(HttpStatus.BAD_REQUEST,
					new NoSuchElementException("No such Fornecedor found"));
		} catch (Exception e) {
			return ExceptionMessage.returnError(HttpStatus.INTERNAL_SERVER_ERROR, e);
		}
	}

	@PostMapping
	public ResponseEntity<Object> save(final FornecedorRecord dto) {
		try {
			Fornecedor fornecedor = FornecedorMapper.input(null, dto);
			fornecedorRepository.save(fornecedor);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return ExceptionMessage.returnError(HttpStatus.INTERNAL_SERVER_ERROR, e);
		}
	}

}