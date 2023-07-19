package br.tcc.monolitico.controller;

import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.tcc.monolitico.service.RelatorioService;

@Controller
@RequestMapping("/relatorio")
public class RelatorioController {
	
	public RelatorioService relatorioService;

	public RelatorioController(RelatorioService relatorioService) {
		this.relatorioService = relatorioService;
	}
	
	@GetMapping("/{data1}/{data2}")
	public ModelAndView getRelatorio(@PathVariable final LocalDateTime data1, @PathVariable final LocalDateTime data2) {
		return relatorioService.getRelatorio(data1, data2);
	}

}