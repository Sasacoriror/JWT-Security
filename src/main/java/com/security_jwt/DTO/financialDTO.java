package com.security_jwt.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class financialDTO {

    @JsonProperty("company_name")
    private String companyName;

    public String getCompanyName() {
        return companyName;
    }
}
