package com.security_jwt.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Min;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "stocks")
public class Stock {

    @Id
    @GeneratedValue
    private Long id;

    @JsonProperty("stockTicker")
    @Column(nullable = false)
    private String stockTicker;

    @JsonProperty("stockFullName")
    private String stockName;

    @JsonProperty("priceInn")
    @Min(value = 0, message = "The price cannot be lower than 0 (zero)")
    private int stockPrice;

    @JsonProperty("numberOfShares")
    @Min(value = 1, message = "The number of shares cannot be lower than 1 (one)")
    private int stockQuantity;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
}

