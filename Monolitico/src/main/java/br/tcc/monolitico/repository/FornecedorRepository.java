package br.tcc.monolitico.repository;

import org.springframework.data.repository.CrudRepository;

import br.tcc.monolitico.domain.Fornecedor;

public interface FornecedorRepository extends CrudRepository<Fornecedor, Long> {

}