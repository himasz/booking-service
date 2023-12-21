package com.statista.code.challenge.common.error.exception;


import com.statista.code.challenge.common.error.ErrorCode;

public class MissingDataException extends ServiceException {

    public MissingDataException(final String message) {
        super(message);
    }

    @Override
    public ErrorCode getApiErrorCode() {
        return ErrorCode.MISSING_DATA_VALUE;
    }

}
