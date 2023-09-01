package br.tcc.relatorio.feign;

import java.util.List;
import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.tcc.relatorio.domain.Venda;

@FeignClient(value = "venda", url = "http://localhost:8083/venda")
public interface VendaFeign {
	
	@RequestMapping(method = RequestMethod.GET, value = "/{data1}/{data2}", produces = MediaType.APPLICATION_JSON_VALUE)
	Optional<List<Venda>> findAllByIsFinalizadaTrueAndDataBetween(@PathVariable final String data1, @PathVariable final String	 data2);

}