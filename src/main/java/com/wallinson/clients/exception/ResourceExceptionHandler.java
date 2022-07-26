package com.wallinson.clients.exception;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResourceExceptionHandler {

 @ExceptionHandler(NotFoundException.class)
 public ResponseEntity<StandardError> notFound(NotFoundException e, HttpServletRequest request){
     String error = "Resoucer not found";
     HttpStatus status = HttpStatus.NOT_FOUND;
     StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
     return ResponseEntity.status(status).body(err);
 }

 @ExceptionHandler(DatabaseException.class)
 public ResponseEntity<StandardError> database(DatabaseException e, HttpServletRequest request){
     String error = "Database error";
     HttpStatus status = HttpStatus.BAD_REQUEST;
     StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
     return ResponseEntity.status(status).body(err);
 }
}