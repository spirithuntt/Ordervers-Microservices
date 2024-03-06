package com.example.apigateway.exception.helper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ExceptionHelper {

    public boolean isLocalException(Exception e) {
        String exceptionMessage = e.getMessage();
        return exceptionMessage == null || !exceptionMessage.contains("Microservice application");
    }
}
