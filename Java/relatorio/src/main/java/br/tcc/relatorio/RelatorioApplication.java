package br.tcc.relatorio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class RelatorioApplication {
	public static void main(String[] args) {
		SpringApplication.run(RelatorioApplication.class, args);
	}
}