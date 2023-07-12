package br.tcc.relatorio.controller;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.tcc.relatorio.service.RelatorioService;

@RestController
@RequestMapping("/relatorio")
public class RelatorioController {
	
	public RelatorioService relatorioService;

	public RelatorioController(RelatorioService relatorioService) {
		this.relatorioService = relatorioService;
	}
	
	@GetMapping("/{data1}/{data2}")
	public ResponseEntity<Object> getRelatorio(@PathVariable final LocalDateTime data1, @PathVariable final LocalDateTime data2) {
		return relatorioService.getRelatorio(data1, data2);
	}

}