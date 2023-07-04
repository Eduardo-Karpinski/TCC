package br.tcc.monolitico.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.tcc.monolitico.domain.Fornecedor;
import br.tcc.monolitico.records.FornecedorRecord;
import br.tcc.monolitico.repository.FornecedorRepository;
import br.tcc.monolitico.util.ExceptionMessage;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/fornecedor")
public class FornecedorController {
	
	public final FornecedorRepository repository;
	
	public FornecedorController(FornecedorRepository repository) {
		this.repository = repository;
	}
	
	@PostMapping
	public ResponseEntity<Object> save(@RequestBody @Valid final FornecedorRecord dto) {
		
		Fornecedor fornecedor = new Fornecedor();
		fornecedor.setNome(dto.nome());
		fornecedor.setCnpj(dto.cnpj());
		fornecedor.setTelefone(dto.telefone());
		fornecedor.setCep(dto.cep());
		fornecedor.setEndereco(dto.endereco());
		fornecedor.setComplemento(dto.complemento());
		fornecedor.setBairro(dto.bairro());
		fornecedor.setMunicipio(dto.municipio());
		fornecedor.setEstado(dto.estado());
		
		try {
			repository.save(fornecedor);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			
			ExceptionMessage error = new ExceptionMessage(
					HttpStatus.INTERNAL_SERVER_ERROR.value(),
					HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
					e.getClass().toString(),
					e.getMessage());
			
			return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
}