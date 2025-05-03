package com.wipro.order_service.exception;

public class APIException extends RuntimeException {
    public APIException(String message) {
        super(message);
    }
}