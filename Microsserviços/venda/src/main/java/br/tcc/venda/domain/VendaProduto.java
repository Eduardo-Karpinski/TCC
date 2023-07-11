package br.tcc.venda.domain;

import java.math.BigDecimal;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class VendaProduto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne(cascade = CascadeType.REFRESH)
	private Produto produto;
	@Column(scale = 2, nullable = false)
	private BigDecimal quantidade;
	@Column(scale = 2, nullable = false)
	private BigDecimal preco;

	public VendaProduto() {

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
		return quantidade;
	}

	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, preco, produto, quantidade);
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
		return Objects.equals(id, other.id) && Objects.equals(preco, other.preco)
				&& Objects.equals(produto, other.produto) && Objects.equals(quantidade, other.quantidade);
	}

	@Override
	public String toString() {
		return "VendaProduto [id=" + id + ", produto=" + produto + ", quantidade=" + quantidade + ", preco=" + preco
				+ "]";
	}

}