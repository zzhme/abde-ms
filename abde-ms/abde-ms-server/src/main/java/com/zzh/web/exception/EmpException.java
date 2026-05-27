package com.zzh.web.exception;

public class EmpException extends RuntimeException {
    public EmpException() {
    }

    public EmpException(String message) {
        super(message);
    }

    public EmpException(String message, Throwable cause) {
        super(message, cause);
    }
}
