package com.example.apigateway.exception.handler;

import com.example.apigateway.exception.mapper.ErrorSimpleResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class LocalExceptionHandler {

    private final ErrorSimpleResponse errorSimpleResponse;
    private HttpServletRequest request;
    private final Logger logger = LoggerFactory.getLogger(LocalExceptionHandler.class);

    public ResponseEntity<ErrorSimpleResponse> handleException(Exception exception, HttpServletRequest request) {
        this.request = request;

        errorSimpleResponse.setTimestamp(LocalDateTime.now());

        if (exception instanceof NoSuchElementException) {
            return handleNoSuchElementException((NoSuchElementException) exception);
        } else if (exception instanceof DataIntegrityViolationException) {
            return handleDataIntegrityViolationException((DataIntegrityViolationException) exception);
        } else if (exception instanceof IllegalArgumentException) {
            return handleIllegalArgumentException((IllegalArgumentException) exception);
        } else if (exception instanceof HttpMessageNotReadableException) {
            return handleHttpMessageNotReadableException((HttpMessageNotReadableException) exception);
        } else if (exception instanceof NoResourceFoundException) {
            return handleNoResourceFoundException((NoResourceFoundException) exception);
        } else return handleDefaultException(exception);
    }

    private ResponseEntity<ErrorSimpleResponse> handleDefaultException(Exception exception) {
        errorSimpleResponse.setMessage("Internal Server Error");
        errorSimpleResponse.setDetails(Collections.singletonList(exception.getMessage()));
        errorSimpleResponse.setPath(request.getRequestURI());

        logger.error("Internal Server Error", exception);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorSimpleResponse);
    }

    private ResponseEntity<ErrorSimpleResponse> handleNoResourceFoundException(NoResourceFoundException exception) {
        errorSimpleResponse.setMessage("Resource Not Found");
        errorSimpleResponse.setDetails(Collections.singletonList(exception.getMessage()));
        errorSimpleResponse.setPath(request.getRequestURI());

        logger.warn("Resource Not Found", exception);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorSimpleResponse);
    }

    private ResponseEntity<ErrorSimpleResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        errorSimpleResponse.setMessage("Bad Request Body check details");
        errorSimpleResponse.setDetails(Arrays.asList("Request body is missing", "Malformed JSON request", "Invalid request body", "input type mismatch"));
        errorSimpleResponse.setPath(request.getRequestURI());

        logger.error("Bad Request Body", exception);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorSimpleResponse);
    }

    private ResponseEntity<ErrorSimpleResponse> handleIllegalArgumentException(IllegalArgumentException exception) {
        errorSimpleResponse.setMessage("Illegal Argument");
        errorSimpleResponse.setDetails(Collections.singletonList(exception.getMessage()));
        errorSimpleResponse.setPath(request.getRequestURI());

        logger.warn("Illegal Argument", exception);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorSimpleResponse);
    }

    private ResponseEntity<ErrorSimpleResponse> handleDataIntegrityViolationException(DataIntegrityViolationException exception) {
        errorSimpleResponse.setMessage("Data Integrity Violation");
        errorSimpleResponse.setDetails(Arrays.asList(exception.getMessage().split(";")));
        errorSimpleResponse.setPath(request.getRequestURI());

        logger.error("Data Integrity Violation", exception);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorSimpleResponse);
    }

    private ResponseEntity<ErrorSimpleResponse> handleNullPointerException(NullPointerException exception) {
        errorSimpleResponse.setMessage("Null Pointer Exception");
        errorSimpleResponse.setDetails(Collections.singletonList(exception.getMessage()));
        errorSimpleResponse.setPath(request.getRequestURI());

        logger.error("Null Pointer Exception", exception);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorSimpleResponse);
    }

    private ResponseEntity<ErrorSimpleResponse> handleNoSuchElementException(NoSuchElementException e) {





        errorSimpleResponse.setMessage("Resource Not Found");
        errorSimpleResponse.setDetails(Collections.singletonList(e.getMessage()));
        errorSimpleResponse.setPath(request.getRequestURI());
        logger.warn("Resource Not Found", e);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorSimpleResponse);
    }
}