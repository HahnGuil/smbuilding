package com.manager.smbuilding.application.excption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidDocumentTypeException extends RuntimeException {
    public InvalidDocumentTypeException(String message) {
        super(message);
    }
}
