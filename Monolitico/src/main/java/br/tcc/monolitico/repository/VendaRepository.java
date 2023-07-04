package br.tcc.monolitico.repository;

import org.springframework.data.repository.CrudRepository;

import br.tcc.monolitico.domain.Venda;

public interface VendaRepository extends CrudRepository<Venda, Long> {

}