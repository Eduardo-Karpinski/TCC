package br.tcc.relatorio.feign;

import java.util.List;
import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.tcc.relatorio.domain.Produto;

@FeignClient(value = "produto", url = "http://localhost:8081/produto")
public interface ProdutoFeign {

	@RequestMapping(method = RequestMethod.GET, value = "/valorBaixo", produces = MediaType.APPLICATION_JSON_VALUE)
	Optional<List<Produto>> getAllByValorBaixo();

}