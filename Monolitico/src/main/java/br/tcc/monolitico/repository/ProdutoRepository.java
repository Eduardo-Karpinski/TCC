package br.tcc.monolitico.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.tcc.monolitico.domain.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}