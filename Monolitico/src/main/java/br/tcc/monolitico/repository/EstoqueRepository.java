package br.tcc.monolitico.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.tcc.monolitico.domain.Estoque;
import br.tcc.monolitico.domain.Produto;

public interface EstoqueRepository extends JpaRepository<Estoque, Long> {

	Optional<Estoque> findByProduto(Produto produto);
	@Query("select e from Estoque e where e.quantidade <= e.quantidadeMinima")
	Optional<List<Estoque>> findAllEstoqueBaixo();
	
}