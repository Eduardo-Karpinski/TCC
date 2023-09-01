package br.tcc.venda.feign;

import java.math.BigDecimal;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.tcc.venda.record.EstoqueRecord;
import jakarta.validation.Valid;

@FeignClient(value = "estoque", url = "http://localhost:8082/estoque")
public interface EstoqueFeign {

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    void save(@RequestBody @Valid final EstoqueRecord dto);
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{idProduto}/{quantidade}")
    void atualizaEstoque(@PathVariable final Long idProduto, @PathVariable final BigDecimal quantidade);
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    void update(@PathVariable final Long id, @RequestBody @Valid final EstoqueRecord dto);
	
}