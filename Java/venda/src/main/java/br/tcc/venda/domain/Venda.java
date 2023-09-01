package br.tcc.venda.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Venda {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@OneToMany(cascade = CascadeType.REFRESH)
	private List<VendaProduto> produtos;
	@Column(nullable = false)
	private LocalDateTime data;
	@Column(nullable = false)
	private Boolean isFinalizada;

	public Venda() {
		this.data = LocalDateTime.now();
		this.isFinalizada = false;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<VendaProduto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<VendaProduto> produtos) {
		this.produtos = produtos;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}

	public Boolean getIsFinalizada() {
		return isFinalizada;
	}

	public void setIsFinalizada(Boolean isFinalizada) {
		this.isFinalizada = isFinalizada;
	}

	@Override
	public int hashCode() {
		return Objects.hash(data, id, isFinalizada, produtos);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Venda other = (Venda) obj;
		return Objects.equals(data, other.data) && Objects.equals(id, other.id)
				&& Objects.equals(isFinalizada, other.isFinalizada) && Objects.equals(produtos, other.produtos);
	}

	@Override
	public String toString() {
		return "Venda [id=" + id + ", produtos=" + produtos + ", data=" + data + ", isFinalizada=" + isFinalizada + "]";
	}

}