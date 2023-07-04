package br.tcc.monolitico.domain;

import java.math.BigDecimal;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Estoque {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@OneToOne(cascade = CascadeType.REFRESH)
	private Produto produto;
	private BigDecimal Quantidade;
	private BigDecimal QuantidadeMinima;

	public Estoque() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public BigDecimal getQuantidade() {
		return Quantidade;
	}

	public void setQuantidade(BigDecimal quantidade) {
		Quantidade = quantidade;
	}

	public BigDecimal getQuantidadeMinima() {
		return QuantidadeMinima;
	}

	public void setQuantidadeMinima(BigDecimal quantidadeMinima) {
		QuantidadeMinima = quantidadeMinima;
	}

	@Override
	public int hashCode() {
		return Objects.hash(Quantidade, QuantidadeMinima, id, produto);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Estoque other = (Estoque) obj;
		return Objects.equals(Quantidade, other.Quantidade) && Objects.equals(QuantidadeMinima, other.QuantidadeMinima)
				&& Objects.equals(id, other.id) && Objects.equals(produto, other.produto);
	}

}