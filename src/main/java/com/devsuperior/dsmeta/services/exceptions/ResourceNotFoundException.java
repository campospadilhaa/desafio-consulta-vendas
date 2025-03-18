package com.devsuperior.dsmeta.services.exceptions;

public class ResourceNotFoundException extends RuntimeException {

	public ResourceNotFoundException(String mensagem) {

		super(mensagem);
	}
}