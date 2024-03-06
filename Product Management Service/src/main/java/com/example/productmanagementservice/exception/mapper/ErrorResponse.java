package com.example.productmanagementservice.exception.mapper;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Component
@Data
public class ErrorResponse {
    private  LocalDateTime timestamp;
    private  String message;
    private  String path;
    private Integer statusCode;

    private  Map<String, List<String>> details;

    @Value("${spring.application.name}")
    private String serviceName;

    private String location="Microservice application";

}
