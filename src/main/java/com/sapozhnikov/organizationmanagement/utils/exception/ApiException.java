package com.sapozhnikov.organizationmanagement.utils.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiException extends RuntimeException {

    private HttpStatus httpStatus;

    public ApiException(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
