package com.manager.smbuilding.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class AmountOverPriceException extends RuntimeException {

    public AmountOverPriceException(String message){
        super(message);
    }
}
