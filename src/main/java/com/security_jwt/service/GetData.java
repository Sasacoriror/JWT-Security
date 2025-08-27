package com.security_jwt.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.security_jwt.DTO.RF_DTO;
import com.security_jwt.Link.API_Links;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GetData {

    private final RestTemplate restTemplate;
    private final API_Links endpoints;
    private final ObjectMapper objectMapper;


    public GetData(RestTemplate restTemplate, API_Links endpoints, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.endpoints = endpoints;
        this.objectMapper = objectMapper;
    }

    public RF_DTO getFinancialData(){
        ResponseEntity<String> response = restTemplate
                .getForEntity(endpoints.getFinancialAPI(), String.class);

        if (response.getBody() == null) {
            throw new RuntimeException("No Response");
        }

        try {
            return objectMapper.readValue(response.getBody(), RF_DTO.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error parsing JSON response");
        }
    }
}
