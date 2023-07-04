package br.tcc.monolitico.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ExceptionMessage handleInvalidParameterException(MethodArgumentNotValidException ex) {
		return sendResponse(HttpStatus.BAD_REQUEST, ex);
	}

	private ExceptionMessage sendResponse(HttpStatus status, MethodArgumentNotValidException ex) {
		return new ExceptionMessage(
				status.value(),
				status.getReasonPhrase(),
				ex.getClass().toString(),
				ex.getMessage());
	}
}