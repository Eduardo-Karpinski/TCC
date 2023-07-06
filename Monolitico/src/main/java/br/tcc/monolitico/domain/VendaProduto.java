package br.tcc.monolitico.domain;

import java.math.BigDecimal;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class VendaProduto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@OneToOne(cascade = CascadeType.REFRESH)
	private Produto produto;
	@Column(scale = 2, nullable = false)
	private BigDecimal quantidade;

	public VendaProduto() {

	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public BigDecimal getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, produto, quantidade);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VendaProduto other = (VendaProduto) obj;
		return Objects.equals(id, other.id) && Objects.equals(produto, other.produto)
				&& Objects.equals(quantidade, other.quantidade);
	}

}