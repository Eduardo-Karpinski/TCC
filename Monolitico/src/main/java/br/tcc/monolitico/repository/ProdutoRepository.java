package br.tcc.monolitico.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.tcc.monolitico.domain.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
	
	@Query("select p from Produto p where (p.custo + p.custo/5) >= p.preco")
	Optional<List<Produto>> getAllByValorBaixo();
	
}