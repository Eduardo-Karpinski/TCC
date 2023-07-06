package br.tcc.monolitico.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.tcc.monolitico.domain.Produto;
import br.tcc.monolitico.domain.VendaProduto;

public interface VendaProdutoRepository extends JpaRepository<VendaProduto, Long> {
	
	Optional<VendaProduto> findByProduto(Produto produto);

}