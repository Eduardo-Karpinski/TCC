package br.tcc.evento;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class EventoApplication {
	public static void main(String[] args) {
		SpringApplication.run(EventoApplication.class, args);
	}
}