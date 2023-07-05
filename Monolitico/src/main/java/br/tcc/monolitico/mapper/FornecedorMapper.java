package br.tcc.monolitico.mapper;

import br.tcc.monolitico.domain.Fornecedor;
import br.tcc.monolitico.record.FornecedorRecord;

public class FornecedorMapper {
	
	public static Fornecedor input(Long id, FornecedorRecord input) {
		Fornecedor fornecedor = new Fornecedor();
		fornecedor.setId(id);
		fornecedor.setNome(input.nome());
		fornecedor.setCnpj(input.cnpj());	
		fornecedor.setTelefone(input.telefone());
		fornecedor.setCep(input.cep());
		fornecedor.setEndereco(input.endereco());
		fornecedor.setComplemento(input.complemento());
		fornecedor.setBairro(input.bairro());
		fornecedor.setMunicipio(input.municipio());
		fornecedor.setEstado(input.estado());
		return fornecedor;
	}
	
	public static FornecedorRecord output(Fornecedor output) {
		return new FornecedorRecord(
				output.getId(),
				output.getNome(),
				output.getCnpj(),
				output.getTelefone(),
				output.getCep(),
				output.getEndereco(),
				output.getComplemento(),
				output.getBairro(),
				output.getMunicipio(),
				output.getEstado());
	}
	
}