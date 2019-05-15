package com.sapozhnikov.organizationmanagement.web.controller.handler;

import com.sapozhnikov.organizationmanagement.utils.exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ApiException.class})
    public ResponseEntity handleApiException(ApiException exception) {
        return new ResponseEntity<>(exception.getHttpStatus());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity handleOtherException(Exception exception) {
        logger.error("handle exception: ", exception);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
