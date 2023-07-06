package br.tcc.monolitico.controller;

import java.math.BigDecimal;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.tcc.monolitico.service.VendaService;

@RestController
@RequestMapping("/venda")
public class VendaController {

	public final VendaService vendaService;
	
	public VendaController(VendaService vendaService) {
		this.vendaService = vendaService;
	}
	
	@PostMapping("{idVenda}/{idProduto}/{quantidade}")
	public ResponseEntity<Object> addProduto(@PathVariable final Long idVenda, @PathVariable final Long idProduto, @PathVariable final BigDecimal quantidade) {
		return vendaService.addProduto(idVenda, idProduto, quantidade);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Object> getById(@PathVariable final Long id) {
		return vendaService.getById(id);
	}
	
	@GetMapping
	public ResponseEntity<Object> get(@PageableDefault(page = 0, size = 10) final Pageable page) {
		return vendaService.get(page);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<Object> delete(@PathVariable final Long id) {
		return vendaService.delete(id);
	}
	
	@PostMapping
	public ResponseEntity<Object> save() {
		return vendaService.save();
	}
	
}