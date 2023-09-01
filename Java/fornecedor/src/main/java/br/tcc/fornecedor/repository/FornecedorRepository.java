package br.tcc.fornecedor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.tcc.fornecedor.domain.Fornecedor;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {

}