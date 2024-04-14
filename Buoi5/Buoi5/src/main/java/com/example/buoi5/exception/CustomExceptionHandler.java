package com.example.buoi5.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(NotFoundException ex, WebRequest request) {
        return new ErrorResponse(ex.getMessage(), ex.getStatus());
    }

    @ExceptionHandler(IndexOutOfBoundsException.class)
    public ErrorResponse handleIndexOutOfBound(IndexOutOfBoundsException ex, WebRequest request) {
        return new ErrorResponse("Vuot qua gia tri mang", HttpStatus.BAD_REQUEST);
    }
}
