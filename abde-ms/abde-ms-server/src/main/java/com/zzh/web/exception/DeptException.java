package com.zzh.web.exception;

public class DeptException extends RuntimeException {
    public DeptException() {
        super();
    }

    public DeptException(String message) {
        super(message);
    }

    public DeptException(String message, Throwable cause) {
        super(message, cause);
    }
}