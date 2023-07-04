package br.tcc.monolitico.repository;

import org.springframework.data.repository.CrudRepository;

import br.tcc.monolitico.domain.Estoque;

public interface EstoqueRepository extends CrudRepository<Estoque, Long> {

}