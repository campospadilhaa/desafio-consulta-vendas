package com.devsuperior.dsmeta.controllers.handlers;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.devsuperior.dsmeta.dto.CustomError;
import com.devsuperior.dsmeta.services.exceptions.ResourceNotFoundException;

@ControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<CustomError> validationException(ResourceNotFoundException e, HttpServletRequest request) {

		HttpStatus status = HttpStatus.BAD_REQUEST;

		CustomError err = new CustomError(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());

		return ResponseEntity.status(status).body(err);
	}
}