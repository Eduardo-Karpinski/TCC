package br.tcc.monolitico.repository;

import org.springframework.data.repository.CrudRepository;

import br.tcc.monolitico.domain.Produto;

public interface ProdutoRepository extends CrudRepository<Produto, Long> {

}