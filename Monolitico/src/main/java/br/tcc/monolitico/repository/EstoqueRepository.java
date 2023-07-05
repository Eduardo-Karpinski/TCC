package br.tcc.monolitico.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.tcc.monolitico.domain.Estoque;

public interface EstoqueRepository extends JpaRepository<Estoque, Long> {

}