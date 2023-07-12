package br.tcc.relatorio.feign;

import java.util.List;
import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.tcc.relatorio.domain.Estoque;

@FeignClient(value = "estoque", url = "http://localhost:8082/estoque")
public interface EstoqueFeign {

	@RequestMapping(method = RequestMethod.GET, value = "/estoqueBaixo", produces = MediaType.APPLICATION_JSON_VALUE)
	Optional<List<Estoque>> findAllEstoqueBaixo();

}