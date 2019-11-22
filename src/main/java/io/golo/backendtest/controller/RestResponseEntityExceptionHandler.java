package io.golo.backendtest.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import io.golo.backendtest.model.MonitoringApiMessage;

@ControllerAdvice
public class RestResponseEntityExceptionHandler 
  extends ResponseEntityExceptionHandler {
 
    @ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class })
    protected ResponseEntity<Object> handleConflict(
      RuntimeException ex, WebRequest request) {
    	MonitoringApiMessage bodyOfResponse = new MonitoringApiMessage(ex.getMessage());
        return handleExceptionInternal(
        		ex, 
        		bodyOfResponse,
        		new HttpHeaders(),
        		HttpStatus.CONFLICT,
        		request);
    }
}