package br.tcc.monolitico.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.tcc.monolitico.domain.Venda;

public interface VendaRepository extends JpaRepository<Venda, Long> {

}