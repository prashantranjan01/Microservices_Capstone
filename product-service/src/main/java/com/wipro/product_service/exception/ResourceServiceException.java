package com.wipro.product_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ResourceServiceException extends RuntimeException {
    public ResourceServiceException(String message) {
        super(message);
    }
}