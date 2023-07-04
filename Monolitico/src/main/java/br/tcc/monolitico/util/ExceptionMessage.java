package br.tcc.monolitico.util;

import java.time.LocalDateTime;

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

}