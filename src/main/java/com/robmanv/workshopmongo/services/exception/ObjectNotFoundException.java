package com.robmanv.workshopmongo.services.exception;

public class ObjectNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ObjectNotFoundException(String msg) {      /// Sobrepor o construtor
		super(msg);
	}
	

}
