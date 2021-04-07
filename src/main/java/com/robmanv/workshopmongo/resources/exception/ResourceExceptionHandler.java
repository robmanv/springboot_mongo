package com.robmanv.workshopmongo.resources.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.robmanv.workshopmongo.services.exception.ObjectNotFoundException;

@ControllerAdvice         //Indica que a classe vai tratar possiveis erros em nossas requisições        // OBJETO em caso de ERRO que será retornado
public class ResourceExceptionHandler {

	@ExceptionHandler(ObjectNotFoundException.class)        // Vou monitorar a exception ObjectNotFound!!!
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError standardError = new StandardError(System.currentTimeMillis(), status.value(), "Não encontrado", e.getMessage(), request.getRequestURI());
		
		return ResponseEntity.status(status).body(standardError);
	}

}
