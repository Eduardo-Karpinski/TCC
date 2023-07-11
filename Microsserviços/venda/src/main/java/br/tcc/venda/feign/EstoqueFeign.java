package br.tcc.venda.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.tcc.venda.record.EstoqueRecord;
import jakarta.validation.Valid;

@FeignClient(value = "estoque", url = "http://localhost:8082/estoque")
public interface EstoqueFeign {

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    void save(@RequestBody @Valid final EstoqueRecord dto);
	
}