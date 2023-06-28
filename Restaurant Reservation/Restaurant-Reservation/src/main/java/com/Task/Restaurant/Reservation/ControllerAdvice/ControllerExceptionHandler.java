package com.Task.Restaurant.Reservation.ControllerAdvice;

import com.Task.Restaurant.Reservation.Exception.ExistedException;
import com.Task.Restaurant.Reservation.Exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFound(NotFoundException notFoundException){
        return new ResponseEntity<>(notFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ExistedException.class)
    public ResponseEntity<Object> handleExisted(ExistedException existedException){
        return new ResponseEntity<>(existedException.getMessage(),HttpStatus.ALREADY_REPORTED);
    }
}
