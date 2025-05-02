package com.wipro.cart_service.exception;

public class PermissionDeniedException extends RuntimeException {

    public PermissionDeniedException() {
        super("Permission denied.");
    }

    public PermissionDeniedException(String message) {
        super(message);
    }

    public PermissionDeniedException(String message, Throwable cause) {
        super(message, cause);
    }

    public PermissionDeniedException(Throwable cause) {
        super(cause);
    }
}