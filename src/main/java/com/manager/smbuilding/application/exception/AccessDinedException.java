package com.manager.smbuilding.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class AccessDinedException extends RuntimeException{
    public AccessDinedException(String message) {
        super(message);
    }
}
