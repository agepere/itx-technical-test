package com.itx.technicalTest.infrastructure.controllers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = Throwable.class)
    public ResponseEntity<ErrorDto> genericException(Throwable exception) {

        ErrorDto error = new ErrorDto();
        error.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        error.setErrorMessage(exception.getMessage());

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    //FIXME: Borrar
    @ExceptionHandler(value = NoSuchElementException.class)
    public ResponseEntity<ErrorDto> noSuchElementException(Throwable exception) {

        ErrorDto error = new ErrorDto();
        error.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        error.setErrorMessage(exception.getMessage());

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public class ErrorDto {
        private String code;
        private String errorMessage;
    }
}
