package ru.prod.Cloud_service.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.prod.Cloud_service.dto.ErrorInResponse;
import ru.prod.Cloud_service.exeptions.AuthorizationException;
import ru.prod.Cloud_service.exeptions.BadCredentialsException;

import java.io.IOException;

@RestControllerAdvice
@Slf4j
public class ControllerExceptionHandler {


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {BindException.class, BadCredentialsException.class, IOException.class})
    ErrorInResponse handleBindException(Exception e) {
        log.error(e.getMessage());
        return new ErrorInResponse(e.getMessage(), -1);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthorizationException.class)
    ErrorInResponse handleAuthorizationException(AuthorizationException e) {
        log.error(e.getMessage());
        return new ErrorInResponse(e.getMessage(), -1);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    ErrorInResponse handleRuntimeException(RuntimeException e) {
        log.error(e.getMessage());
        return new ErrorInResponse(e.getMessage(), -1);
    }
}
