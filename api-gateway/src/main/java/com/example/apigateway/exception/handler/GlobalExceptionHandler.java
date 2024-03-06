package com.example.apigateway.exception.handler;


import com.example.apigateway.exception.helper.ExceptionHelper;
import com.example.apigateway.exception.mapper.ErrorSimpleResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.NoSuchElementException;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final ExternalServiceExceptionHandler externalServiceExceptionHandler;
    private final ExceptionHelper exceptionHelper;
    private final ErrorSimpleResponse errorSimpleResponse;
    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorSimpleResponse> handleNoSuchElementException(
            NoSuchElementException exception, HttpServletRequest request) {

        if (!exceptionHelper.isLocalException(exception)) {
            return externalServiceExceptionHandler.handleException(exception, request);
        }

        errorSimpleResponse.setTimestamp(LocalDateTime.now());
        errorSimpleResponse.setMessage("Resource Not Found");
        errorSimpleResponse.setDetails(Collections.singletonList(exception.getMessage()));
        errorSimpleResponse.setPath(request.getRequestURI());

        logger.warn("Resource Not Found ", exception);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorSimpleResponse);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorSimpleResponse> handleDataIntegrityViolationException(
            DataIntegrityViolationException exception, HttpServletRequest request) {

        if (!exceptionHelper.isLocalException(exception)) {
            return externalServiceExceptionHandler.handleException(exception, request);
        }

        errorSimpleResponse.setTimestamp(LocalDateTime.now());
        errorSimpleResponse.setMessage("Data Integrity Violation");
        errorSimpleResponse.setDetails(Arrays.asList(exception.getMessage().split(";")));
        errorSimpleResponse.setPath(request.getRequestURI());

        logger.error("Data Integrity Violation", exception);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorSimpleResponse);
    }


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorSimpleResponse> handleIllegalArgumentException(
            IllegalArgumentException exception, HttpServletRequest request) {

        if (!exceptionHelper.isLocalException(exception)) {
            return externalServiceExceptionHandler.handleException(exception, request);
        }

        errorSimpleResponse.setTimestamp(LocalDateTime.now());
        errorSimpleResponse.setMessage("Illegal Argument");
        errorSimpleResponse.setDetails(Collections.singletonList(exception.getMessage()));
        errorSimpleResponse.setPath(request.getRequestURI());

        logger.warn("Illegal Argument", exception);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorSimpleResponse);
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorSimpleResponse> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException exception, HttpServletRequest request) {


        if (!exceptionHelper.isLocalException(exception)) {
            return externalServiceExceptionHandler.handleException(exception, request);
        }

        errorSimpleResponse.setTimestamp(LocalDateTime.now());
        errorSimpleResponse.setMessage("Bad Request Body check details");
        errorSimpleResponse.setDetails(Arrays.asList("Request body is missing", "Malformed JSON request", "Invalid request body", "input type mismatch"));
        errorSimpleResponse.setPath(request.getRequestURI());

        logger.error("Bad Request Body", exception);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorSimpleResponse);
    }


    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorSimpleResponse> handleNoResourceFoundException(
            NoResourceFoundException exception, HttpServletRequest request) {

        if (!exceptionHelper.isLocalException(exception)) {
            return externalServiceExceptionHandler.handleException(exception, request);
        }

        errorSimpleResponse.setTimestamp(LocalDateTime.now());
        errorSimpleResponse.setMessage("Resource Not Found ");
        errorSimpleResponse.setDetails(Collections.singletonList(exception.getMessage()));
        errorSimpleResponse.setPath(request.getRequestURI());


        logger.warn("Resource Not Found", exception);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorSimpleResponse);
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorSimpleResponse> handleException(
            Exception exception, HttpServletRequest request) {


        if (!exceptionHelper.isLocalException(exception)) {
            return externalServiceExceptionHandler.handleException(exception, request);
        }

        logger.error("Exception occurred", exception);

        errorSimpleResponse.setTimestamp(LocalDateTime.now());
        errorSimpleResponse.setMessage("Internal Server Error");
        errorSimpleResponse.setDetails(Collections.singletonList(exception.getMessage()));
        errorSimpleResponse.setPath(request.getRequestURI());

        logger.error("Internal Server Error", exception);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorSimpleResponse);
    }


}
