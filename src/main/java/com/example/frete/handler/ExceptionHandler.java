package com.example.frete.handler;

import com.example.frete.exception.DisponibilidadeEstoqueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionHandler {
  @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
  protected ResponseEntity<StandardError> methodArgumentNotValid(MethodArgumentNotValidException ex, HttpServletRequest request) {
    String message = ex.getBindingResult().getFieldErrorCount() + " invalid argument(s). Message: " + ex.getBindingResult().getFieldErrors();
    var err = new StandardError(
        HttpStatus.BAD_REQUEST.value()
        , "Argument(s) not valid"
        , message
        , request.getRequestURI());
    return ResponseEntity.status(err.getStatus()).body(err);
  }
  @org.springframework.web.bind.annotation.ExceptionHandler(DisponibilidadeEstoqueException.class)
  public ResponseEntity UserVoterException(DisponibilidadeEstoqueException e, HttpServletRequest request) {
    StandardError err = new StandardError(
        HttpStatus.NOT_FOUND.value()
        , HttpStatus.NOT_FOUND.name()
        , e.getMessage()
        , request.getRequestURI());
    return ResponseEntity.status(err.getStatus()).body(err);
  }
}