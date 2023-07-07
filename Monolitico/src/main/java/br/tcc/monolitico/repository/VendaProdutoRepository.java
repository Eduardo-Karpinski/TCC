package br.tcc.monolitico.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.tcc.monolitico.domain.VendaProduto;

public interface VendaProdutoRepository extends JpaRepository<VendaProduto, Long> {
	
}