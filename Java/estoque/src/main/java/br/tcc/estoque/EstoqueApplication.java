package br.tcc.estoque;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class EstoqueApplication {
	public static void main(String[] args) {
		SpringApplication.run(EstoqueApplication.class, args);
	}
}