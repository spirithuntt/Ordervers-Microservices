package com.example.ordermanagementservice.exception.mapper;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Data
public class ErrorSimpleResponse {
    private LocalDateTime timestamp;
    private String message;
    private List<String> details;
    private String path;
    private Integer statusCode;


    @Value("${spring.application.name}")
    private String serviceName;

    private String location="Microservice application";

}
