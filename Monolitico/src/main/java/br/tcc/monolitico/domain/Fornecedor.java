package br.tcc.monolitico.domain;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Fornecedor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false, length = 100)
	private String nome;
	@Column(unique = true, nullable = false, length = 100)
	private String cnpj;
	@Column(nullable = false, length = 100)
	private String telefone;
	@Column(nullable = false, length = 100)
	private String cep;
	@Column(nullable = false, length = 100)
	private String endereco;
	@Column(nullable = false, length = 100)
	private String complemento;
	@Column(nullable = false, length = 100)
	private String bairro;
	@Column(nullable = false, length = 100)
	private String municipio;
	@Column(nullable = false, length = 100)
	private String estado;

	public Fornecedor() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	@Override
	public int hashCode() {
		return Objects.hash(bairro, cep, cnpj, complemento, endereco, estado, id, municipio, nome, telefone);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Fornecedor other = (Fornecedor) obj;
		return Objects.equals(bairro, other.bairro) && Objects.equals(cep, other.cep)
				&& Objects.equals(cnpj, other.cnpj) && Objects.equals(complemento, other.complemento)
				&& Objects.equals(endereco, other.endereco) && Objects.equals(estado, other.estado)
				&& Objects.equals(id, other.id) && Objects.equals(municipio, other.municipio)
				&& Objects.equals(nome, other.nome) && Objects.equals(telefone, other.telefone);
	}

	@Override
	public String toString() {
		return "Fornecedor [id=" + id + ", nome=" + nome + ", cnpj=" + cnpj + ", telefone=" + telefone + ", cep=" + cep
				+ ", endereco=" + endereco + ", complemento=" + complemento + ", bairro=" + bairro + ", municipio="
				+ municipio + ", estado=" + estado + "]";
	}

}