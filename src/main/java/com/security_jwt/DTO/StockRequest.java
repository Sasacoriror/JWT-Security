package com.security_jwt.DTO;

import lombok.Data;

@Data
public class StockRequest {
    private String ticker;
    private Double price;
    private Integer shares;
}
