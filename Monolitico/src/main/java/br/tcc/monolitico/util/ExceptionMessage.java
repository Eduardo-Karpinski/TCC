package br.tcc.monolitico.util;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ExceptionMessage {
	
	public LocalDateTime time;
	public int status;
	public String error;
	public String exception;
	public String message;
	
	public ExceptionMessage(int status, String error, String exception, String message) {
		this.time = LocalDateTime.now();
		this.status = status;
		this.error = error;
		this.exception = exception;
		this.message = message;
	}
	
	public static ResponseEntity<Object> returnError(HttpStatus httpStatus, Exception e) {
		ExceptionMessage error = new ExceptionMessage(
				httpStatus.value(),
				httpStatus.getReasonPhrase(),
				e.getClass().toString(),
				e.getMessage());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

}