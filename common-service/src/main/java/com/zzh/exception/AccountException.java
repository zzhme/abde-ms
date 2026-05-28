package com.zzh.exception;

//自定义账户操作异常
public class AccountException extends RuntimeException{
    public AccountException() {
    }

    public AccountException(String message) {
        super(message);
    }
}
