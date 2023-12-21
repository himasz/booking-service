package com.statista.code.challenge.common.error;

import com.statista.code.challenge.common.error.ControllerError;
import lombok.Getter;
import org.springframework.http.HttpStatus;


public enum ErrorCode {
    MISSING_BODY_FIELD(1, HttpStatus.BAD_REQUEST, "Invalid body field"),
    MISSING_DATA_VALUE(2, HttpStatus.BAD_REQUEST, "Missing data for value"),
    EMAIL_AUTH_FAILED(3, HttpStatus.INTERNAL_SERVER_ERROR, "Authentication failed");

    @Getter
    private final int code;

    @Getter
    private final HttpStatus httpStatus;

    @Getter
    private final String message;

    ErrorCode(int code, HttpStatus httpStatus, String message) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public ControllerError toResponseEntity() {
        return new ControllerError(code, name(), message);
    }

    public ControllerError toResponseEntity(String detailedMessage) {
        return new ControllerError(code, name(), message + ": " + detailedMessage);
    }
}
