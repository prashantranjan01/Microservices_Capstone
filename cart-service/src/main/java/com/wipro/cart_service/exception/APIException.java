package com.wipro.cart_service.exception;

public class APIException extends RuntimeException {
    public APIException(String message) {
        super(message);
    }
}