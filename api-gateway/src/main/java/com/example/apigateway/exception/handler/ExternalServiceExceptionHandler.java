package com.example.apigateway.exception.handler;

import com.example.apigateway.exception.mapper.ErrorSimpleResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;


@Component
@RequiredArgsConstructor
public class ExternalServiceExceptionHandler {
    private final ErrorSimpleResponse errorSimpleResponse;

    private final Logger logger = LoggerFactory.getLogger(ExternalServiceExceptionHandler.class);

    public ResponseEntity<ErrorSimpleResponse> handleException(Exception exception, HttpServletRequest request) {

        String exceptionBody = exception.getMessage();
//        String exceptionBody = "500 : {\n" +
//                "    \"timestamp\": \"2024-03-06T18:07:49.0641029\",\n" +
//                "    \"message\": \"Resource Not Found \",\n" +
//                "    \"details\": [\n" +
//                "        \"No static resource api/v1/productsc.\"\n" +
//                "    ],\n" +
//                "    \"path\": \"/api/v1/productsc\",\n" +
//                "    \"statusCode\": 404,\n" +
//                "    \"serviceName\": \"product-management-service\"\n" +
//                "}";

        exceptionBody = exceptionBody.substring(exceptionBody.indexOf("{"), exceptionBody.lastIndexOf("}") + 1);

        errorSimpleResponse.setTimestamp(extractTimestamp(exceptionBody));
        errorSimpleResponse.setApiGatewayMessage("Exception occurred while calling external service : " + extractServiceName(exceptionBody));
        errorSimpleResponse.setMessage(extractMessage(exceptionBody));
        errorSimpleResponse.setDetails(extractDetails(exceptionBody));
        errorSimpleResponse.setPath(extractPath(exceptionBody));


        return ResponseEntity.status(extractStatusCode(exceptionBody)).body(errorSimpleResponse);
    }

    public int extractStatusCode(String exceptionBody) {
        ObjectMapper objectMapper = new ObjectMapper();
        int statusCode = 0;
        try {
            statusCode = objectMapper.readTree(exceptionBody).get("statusCode").asInt();
        } catch (Exception e) {
            logger.error("Error occurred while extracting status code from exception body", e);
        }
        return statusCode;

    }

    public String extractMessage(String exceptionBody) {
        ObjectMapper objectMapper = new ObjectMapper();
        String message = "";
        try {
            message = objectMapper.readTree(exceptionBody).get("message").asText();
        } catch (Exception e) {
            logger.error("Error occurred while extracting message from exception body", e);
        }
        return message;

    }

    public String extractPath(String exceptionBody) {
        ObjectMapper objectMapper = new ObjectMapper();
        String path = "";
        try {
            path = objectMapper.readTree(exceptionBody).get("path").asText();
        } catch (Exception e) {
            logger.error("Error occurred while extracting path from exception body", e);
        }
        return path;

    }

    public String extractServiceName(String exceptionBody) {
        ObjectMapper objectMapper = new ObjectMapper();
        String serviceName = "";
        try {
            serviceName = objectMapper.readTree(exceptionBody).get("serviceName").asText();
        } catch (Exception e) {
            logger.error("Error occurred while extracting service name from exception body", e);
        }
        return serviceName;

    }

    public LocalDateTime extractTimestamp(String exceptionBody) {
        ObjectMapper objectMapper = new ObjectMapper();
        String timestamp = "";
        try {
            timestamp = objectMapper.readTree(exceptionBody).get("timestamp").asText();
        } catch (Exception e) {
            logger.error("Error occurred while extracting timestamp from exception body", e);
        }
        return LocalDateTime.parse(timestamp);

    }

    public List<String> extractDetails(String exceptionBody) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> details = null;
        try {
            details = objectMapper.readValue(objectMapper.readTree(exceptionBody).get("details").toString(), List.class);
        } catch (Exception e) {
            logger.error("Error occurred while extracting details from exception body", e);
        }
        return details;
    }


}
