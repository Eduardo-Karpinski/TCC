package br.tcc.venda.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.tcc.venda.domain.VendaProduto;

public interface VendaProdutoRepository extends JpaRepository<VendaProduto, Long> {
	
}