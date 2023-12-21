package com.statista.code.challenge.common.error.exception;


import com.statista.code.challenge.common.error.ErrorCode;

public class EmailAuthenticationException extends ServiceException {

    public EmailAuthenticationException(final String message) {
        super(message);
    }

    @Override
    public ErrorCode getApiErrorCode() {
        return ErrorCode.EMAIL_AUTH_FAILED;
    }

}
