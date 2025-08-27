package com.security_jwt.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RF_DTO {

    @JsonProperty("results")
    private List<financialDTO> results;

    public List<financialDTO> getResults() {
        return results;
    }

    public void setResults(List<financialDTO> results) {
        this.results = results;
    }
}
