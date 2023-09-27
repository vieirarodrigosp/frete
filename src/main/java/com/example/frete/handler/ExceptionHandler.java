package com.example.frete.handler;

import com.example.frete.exception.DisponibilidadeEstoqueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

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
//  @org.springframework.web.bind.annotation.ExceptionHandler(ConstraintViolationException.class)
//  public ResponseEntity handle(ConstraintViolationException constraintViolationException) {
//    Set<ConstraintViolation<?>> violations = constraintViolationException.getConstraintViolations();
//    String errorMessage = "";
//    if (!violations.isEmpty()) {
//      StringBuilder builder = new StringBuilder();
//      violations.forEach(violation -> builder.append(" " + violation.getMessage()));
//      errorMessage = builder.toString();
//    } else {
//      errorMessage = "ConstraintViolationException occured.";
//    }
//    return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
//  }
//  @org.springframework.web.bind.annotation.ExceptionHandler(UnexpectedTypeException.class)
//  public ResponseEntity validadedException(UnexpectedTypeException e, HttpServletRequest request) {
//    var teste = e.getLocalizedMessage();
//    var teste01 = e.getMessage();
//    var teste02 = e.getCause().getSuppressed();
//    var teste03 = e.getCause().getStackTrace();
//    var teste04 = e.getCause().fillInStackTrace();
//    StandardError err = new StandardError(
//        HttpStatus.BAD_REQUEST.value()
//        , e.getLocalizedMessage()
//        , e.getMessage()
//        , request.getRequestURI());
//    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
//  }

//  @org.springframework.web.bind.annotation.ExceptionHandler(SQLGrammarException.class)
//  public ResponseEntity handle(SQLGrammarException sQLGrammarException, HttpServletRequest request) {
//    String errorMessage = "";
//    String message = sQLGrammarException.getLocalizedMessage() + " invalid argument(s). Message: "
//        + sQLGrammarException.getSQLException();
//    var err = new StandardError(HttpStatus.BAD_REQUEST.value(), "Argument(s) not valid"
//        , message, request.getRequestURI());
//    return ResponseEntity.status(err.getStatus()).body(err);
//  }
//  @org.springframework.web.bind.annotation.ExceptionHandler(PollException.class)
//  public ResponseEntity PollException(PollException e, HttpServletRequest request) {
//    StandardError err = new StandardError();
//    err.setTimestamp(Instant.now());
//    err.setStatus(HttpStatus.CONFLICT.value());
//    err.setError(e.getLocalizedMessage());
//    err.setMessage(e.getMessage());
//    err.setPath(request.getRequestURI());
//    return ResponseEntity.status(HttpStatus.CONFLICT).body(err);
//  }
//  @org.springframework.web.bind.annotation.ExceptionHandler(UserVoterException.class)
//  public ResponseEntity UserVoterException(UserVoterException e, HttpServletRequest request) {
//    StandardError err = new StandardError();
//    err.setTimestamp(Instant.now());
//    err.setStatus(HttpStatus.CONFLICT.value());
//    err.setError(e.getLocalizedMessage());
//    err.setMessage(e.getMessage());
//    err.setPath(request.getRequestURI());
//    return ResponseEntity.status(HttpStatus.CONFLICT).body(err);
//  }
//  @org.springframework.web.bind.annotation.ExceptionHandler(UserException.class)
//  public ResponseEntity UserVoterException(UserException e, HttpServletRequest request) {
//    StandardError err = new StandardError();
//    err.setTimestamp(Instant.now());
//    err.setStatus(HttpStatus.CONFLICT.value());
//    err.setError(e.getLocalizedMessage());
//    err.setMessage(e.getMessage());
//    err.setPath(request.getRequestURI());
//    return ResponseEntity.status(HttpStatus.CONFLICT).body(err);
//  }
//  @org.springframework.web.bind.annotation.ExceptionHandler(AssemblyException.class)
//  public ResponseEntity UserVoterException(AssemblyException e, HttpServletRequest request) {
//    StandardError err = new StandardError();
//    err.setTimestamp(Instant.now());
//    err.setStatus(HttpStatus.CONFLICT.value());
//    err.setError(e.getLocalizedMessage());
//    err.setMessage(e.getMessage());
//    err.setPath(request.getRequestURI());
//    return ResponseEntity.status(HttpStatus.CONFLICT).body(err);
//  }
}