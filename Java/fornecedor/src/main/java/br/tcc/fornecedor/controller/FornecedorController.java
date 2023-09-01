package br.tcc.fornecedor.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.tcc.fornecedor.record.FornecedorRecord;
import br.tcc.fornecedor.service.FornecedorService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/fornecedor")
public class FornecedorController {

	public final FornecedorService fornecedorService;

	public FornecedorController(FornecedorService fornecedorService) {
		this.fornecedorService = fornecedorService;
	}

	@GetMapping("{id}")
	public ResponseEntity<Object> getById(@PathVariable final Long id) {
		return fornecedorService.getById(id);
	}
	
	@GetMapping
	public ResponseEntity<Object> get(@PageableDefault(page = 0, size = 10) final Pageable page) {
		return fornecedorService.get(page);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Object> delete(@PathVariable final Long id) {
		return fornecedorService.delete(id);
	}

	@PutMapping("{id}")
	public ResponseEntity<Object> update(@PathVariable final Long id, @RequestBody @Valid final FornecedorRecord dto) {
		return fornecedorService.update(id, dto);
	}

	@PostMapping
	public ResponseEntity<Object> save(@RequestBody @Valid final FornecedorRecord dto) {
		return fornecedorService.save(dto);
	}

}