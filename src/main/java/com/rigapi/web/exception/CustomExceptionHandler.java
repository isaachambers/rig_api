package com.rigapi.web.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.rigapi.exception.CustomerNotFoundException;
import com.rigapi.exception.OrderNotFoundException;
import com.rigapi.exception.OutOfStockException;
import com.rigapi.exception.ProductNotFoundException;
import com.rigapi.web.response.ErrorResponse;
import java.util.ArrayList;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

  private static final Logger LOG = LoggerFactory.getLogger(CustomExceptionHandler.class);

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request
  ) {
    var bindingResult = ex.getBindingResult();
    var errors = new ArrayList<Map<String, Object>>();

    for (var fieldError : bindingResult.getFieldErrors()) {
      errors.add(Map.of(
          "field", fieldError.getField(),
          "message", fieldError.getDefaultMessage()));
    }

    for (var objectError : bindingResult.getGlobalErrors()) {
      errors.add(Map.of("message", objectError.getDefaultMessage()));
    }

    var error = new ErrorResponse();
    error.setErrors(errors);
    error.setStatus(status.getReasonPhrase());

    LOG.warn("Response: {} Uuid: {}.", error.getStatus(), error.getUuid(), ex);
    return new ResponseEntity<>(error, status);
  }

  @ExceptionHandler
  public ResponseEntity<ErrorResponse> handle(CustomerNotFoundException ex) {
    var status = NOT_FOUND;
    return getErrorResponseResponseEntity(ex, status, ex.getMessage());
  }

  @ExceptionHandler
  public ResponseEntity<ErrorResponse> handle(OutOfStockException ex) {
    var status = BAD_REQUEST;
    return getErrorResponseResponseEntity(ex, status, ex.getMessage());
  }

  @ExceptionHandler
  public ResponseEntity<ErrorResponse> handle(ProductNotFoundException ex) {
    var status = NOT_FOUND;
    return getErrorResponseResponseEntity(ex, status, ex.getMessage());
  }

  @ExceptionHandler
  public ResponseEntity<ErrorResponse> handle(OrderNotFoundException ex) {
    var status = NOT_FOUND;
    return getErrorResponseResponseEntity(ex, status, ex.getMessage());
  }

  private ResponseEntity<ErrorResponse> getErrorResponseResponseEntity(Exception ex, HttpStatus status, String message) {
    var error = new ErrorResponse();
    error.setStatus(status.getReasonPhrase());
    error.setMessage(message);
    LOG.info("Response: {} Uuid: {}.", error.getStatus(), error.getUuid(), ex);
    return new ResponseEntity<>(error, status);
  }
}
