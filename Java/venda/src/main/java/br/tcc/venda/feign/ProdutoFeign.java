package br.tcc.venda.feign;

import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.tcc.venda.domain.Produto;


@FeignClient(value = "produto", url = "http://localhost:8081/produto")
public interface ProdutoFeign {

	@RequestMapping(method = RequestMethod.GET, value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    Optional<Produto> findById(@PathVariable final Long id);
	
}