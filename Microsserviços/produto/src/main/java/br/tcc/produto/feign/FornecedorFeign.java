package br.tcc.produto.feign;

import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.tcc.produto.domain.Fornecedor;

@FeignClient(value = "fornecedor", url = "http://localhost:8080/fornecedor")
public interface FornecedorFeign {

	@RequestMapping(method = RequestMethod.GET, value = "/{id}", produces = "application/json")
    Optional<Fornecedor> findById(@PathVariable final Long id);
	
}