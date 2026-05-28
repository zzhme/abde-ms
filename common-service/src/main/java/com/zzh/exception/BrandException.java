package com.zzh.exception;

public class BrandException extends RuntimeException {
    public BrandException() {
    }

    public BrandException(String message) {
        super(message);
    }

    public BrandException(String message, Throwable cause) {
        super(message, cause);
    }
}
