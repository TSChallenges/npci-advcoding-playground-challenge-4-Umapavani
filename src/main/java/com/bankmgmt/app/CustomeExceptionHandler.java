package com.bankmgmt.app;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class CustomeExceptionHandler {

    @ExceptionHandler(exception = MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleException(MethodArgumentNotValidException exception){
        Map<String,String> map = new HashMap<>();
       BindingResult bindingResult =  exception.getBindingResult();
       for(FieldError error :bindingResult.getFieldErrors()){
          map.put(error.getField(),error.getDefaultMessage());
        }
       return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);

    }
}
