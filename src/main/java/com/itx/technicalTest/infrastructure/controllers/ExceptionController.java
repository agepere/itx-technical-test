package com.itx.technicalTest.infrastructure.controllers;

import com.itx.technicalTest.infrastructure.entities.ErrorEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = Throwable.class)
    public ResponseEntity<ErrorEntity> genericException(Throwable exception) {

        ErrorEntity error = new ErrorEntity();
        error.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        error.setErrorMessage(exception.getMessage());

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    //FIXME: Borrar
    @ExceptionHandler(value = NoSuchElementException.class)
    public ResponseEntity<ErrorEntity> noSuchElementException(Throwable exception) {

        ErrorEntity error = new ErrorEntity();
        error.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        error.setErrorMessage(exception.getMessage());

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
