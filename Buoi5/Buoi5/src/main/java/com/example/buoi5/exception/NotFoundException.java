package com.example.buoi5.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class NotFoundException extends RuntimeException {
    private String message;

    private HttpStatus status;

    public NotFoundException(String message) {
        this.message = message;
        status = HttpStatus.NOT_FOUND;
    }
}
