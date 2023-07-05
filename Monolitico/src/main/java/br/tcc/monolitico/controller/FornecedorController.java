package br.tcc.monolitico.controller;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.tcc.monolitico.domain.Fornecedor;
import br.tcc.monolitico.mapper.FornecedorMapper;
import br.tcc.monolitico.record.FornecedorRecord;
import br.tcc.monolitico.repository.FornecedorRepository;
import br.tcc.monolitico.util.ExceptionMessage;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/fornecedor")
public class FornecedorController {

	public final FornecedorRepository repository;

	public FornecedorController(FornecedorRepository repository) {
		this.repository = repository;
	}

	@GetMapping
	private ResponseEntity<Object> get(@PageableDefault(page = 0, size = 10) Pageable page) {
		try {
			Page<Fornecedor> findAll = repository.findAll(page);
			PageImpl<FornecedorRecord> pageImpl = new PageImpl<>(
					findAll.getContent().stream().map(FornecedorMapper::output).collect(Collectors.toList()), page,
					findAll.getTotalElements());
			return new ResponseEntity<>(pageImpl, HttpStatus.OK);
		} catch (Exception e) {
			return ExceptionMessage.returnError(HttpStatus.INTERNAL_SERVER_ERROR, e);
		}
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Object> delete(@PathVariable final Long id) {
		try {
			repository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			return ExceptionMessage.returnError(HttpStatus.BAD_REQUEST, e);
		} catch (Exception e) {
			return ExceptionMessage.returnError(HttpStatus.INTERNAL_SERVER_ERROR, e);
		}
	}

	@PutMapping("{id}")
	public ResponseEntity<Object> update(@PathVariable final Long id, @RequestBody @Valid final FornecedorRecord dto) {
		try {
			if (repository.existsById(id)) {
				Fornecedor fornecedor = FornecedorMapper.input(id, dto);
				repository.save(fornecedor);
				return new ResponseEntity<>(HttpStatus.OK);
			}
			return ExceptionMessage.returnError(HttpStatus.BAD_REQUEST,
					new NoSuchElementException("No such element found"));
		} catch (Exception e) {
			return ExceptionMessage.returnError(HttpStatus.INTERNAL_SERVER_ERROR, e);
		}
	}

	@PostMapping
	public ResponseEntity<Object> save(@RequestBody @Valid final FornecedorRecord dto) {
		try {
			Fornecedor fornecedor = FornecedorMapper.input(null, dto);
			repository.save(fornecedor);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return ExceptionMessage.returnError(HttpStatus.INTERNAL_SERVER_ERROR, e);
		}
	}

}