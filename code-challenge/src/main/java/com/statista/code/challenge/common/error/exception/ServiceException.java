package com.statista.code.challenge.common.error.exception;


import com.statista.code.challenge.common.error.ErrorCode;

public abstract class ServiceException extends RuntimeException{

    ServiceException(String message) {
        super(message);
    }

    ServiceException() {
        super();
    }

    public abstract ErrorCode getApiErrorCode();

}
