package br.tcc.venda.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.tcc.venda.domain.Venda;

public interface VendaRepository extends JpaRepository<Venda, Long> {

	Optional<List<Venda>> findAllByIsFinalizadaTrueAndDataBetween(LocalDateTime data1, LocalDateTime data2);

}