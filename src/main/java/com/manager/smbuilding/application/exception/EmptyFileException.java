package com.manager.smbuilding.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class EmptyFileException extends RuntimeException{

    public EmptyFileException(String message) {
        super(message);
    }
}
