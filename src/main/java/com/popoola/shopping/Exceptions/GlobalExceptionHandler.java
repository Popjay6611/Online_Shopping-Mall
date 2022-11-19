package com.popoola.shopping.Exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestController
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllException(Exception ex, WebRequest request){
       ExceptionResponse exception = new ExceptionResponse(ex.getMessage(),
                request.getDescription(false), new Date());
        return new ResponseEntity<Object>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ShoppingNotFound.class)
    public ResponseEntity<Object> handleShoppingNotFoundException(Exception ex, WebRequest request){
        ExceptionResponse exception = new ExceptionResponse(ex.getMessage(),
                request.getDescription(false), new Date());
        return new ResponseEntity<Object>(exception, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        ExceptionResponse exception = new ExceptionResponse("Invalid Input",
                ex.getBindingResult().toString(), new Date());
        return new ResponseEntity<Object>(exception, HttpStatus.BAD_REQUEST);
    }
}
