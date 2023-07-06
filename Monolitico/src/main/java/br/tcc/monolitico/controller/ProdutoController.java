package br.tcc.monolitico.controller;

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

import br.tcc.monolitico.record.ProdutoRecord;
import br.tcc.monolitico.service.ProdutoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

	public final ProdutoService produtoService;
	
	public ProdutoController(ProdutoService produtoService) {
		this.produtoService = produtoService;
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Object> getById(@PathVariable final Long id) {
		return produtoService.getById(id);
	}
	
	@GetMapping
	public ResponseEntity<Object> get(@PageableDefault(page = 0, size = 10) final Pageable page) {
		return produtoService.get(page);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<Object> delete(@PathVariable final Long id) {
		return produtoService.delete(id);
	}

	@PutMapping("{id}")
	public ResponseEntity<Object> update(@PathVariable final Long id, @RequestBody @Valid final ProdutoRecord dto) {
		return produtoService.update(id, dto);
	}
	
	@PostMapping
	public ResponseEntity<Object> save(@RequestBody @Valid final ProdutoRecord dto) {
		return produtoService.save(dto);
	}

}