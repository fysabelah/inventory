package com.spring.batch.api.products.utils.exceptions;

import com.spring.batch.api.products.utils.MessageUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerUtil {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<StandardError> genericError(Exception e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        return ResponseEntity.status(status)
                .body(new StandardError(status.value(),
                        "Unknown Error",
                        e.getMessage(),
                        request.getRequestURI()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<StandardError> dataValidations(MethodArgumentNotValidException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;

        String message;

        if (e.getFieldError() != null && e.getFieldError().getDefaultMessage() != null) {
            message = MessageUtil.getMessage(e.getFieldError().getDefaultMessage());

            if (message == null) {
                message = e.getFieldError().getField() + ": " + e.getFieldError().getDefaultMessage();
            }
        } else {
            message = e.getMessage();
        }

        StandardError error = new StandardError(status.value(),
                "Data Error",
                message,
                request.getRequestURI()
        );

        return ResponseEntity.status(status).body(error);
    }

}
