package com.sapozhnikov.organizationmanagement.utils.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiException extends RuntimeException {

    private final HttpStatus httpStatus;

    public ApiException(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public ApiException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public boolean haveMessage() {
        return super.getMessage() != null;
    }
}
