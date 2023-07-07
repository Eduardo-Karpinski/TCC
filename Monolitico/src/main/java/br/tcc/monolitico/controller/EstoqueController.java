package br.tcc.monolitico.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.tcc.monolitico.record.EstoqueRecord;
import br.tcc.monolitico.service.EstoqueService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/estoque")
public class EstoqueController {
	
	public final EstoqueService estoqueService;
	
	public EstoqueController(EstoqueService estoqueService) {
		this.estoqueService = estoqueService;
	}

	@GetMapping("{id}")
	public ResponseEntity<Object> getById(@PathVariable final Long id) {
		return estoqueService.getById(id);
	}
	
	@GetMapping
	public ResponseEntity<Object> get(@PageableDefault(page = 0, size = 10) final Pageable page) {
		return estoqueService.get(page);
	}
	
	@PutMapping("{id}")
	public ResponseEntity<Object> update(@PathVariable final Long id, @RequestBody @Valid final EstoqueRecord dto) {
		return estoqueService.update(id, dto);
	}
	
}