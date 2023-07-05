package br.tcc.monolitico.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.tcc.monolitico.domain.Fornecedor;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {

}