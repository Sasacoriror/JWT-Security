package com.security_jwt.demo;

import com.security_jwt.DTO.StockRequest;
import com.security_jwt.service.JwtService;
import com.security_jwt.service.StockService;
import com.security_jwt.user.Stock;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stocks")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;
    private final JwtService jwtService;

    @PostMapping("/add")
    public ResponseEntity<Stock> addStock(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody StockRequest request
    ) {
        String token = authHeader.substring(7);
        String email = jwtService.extractUsername(token);

        Stock stock = stockService.addStock(email, request.getTicker(), request.getPrice(), request.getShares());
        return ResponseEntity.ok(stock);
    }

    @GetMapping
    public ResponseEntity<List<Stock>> getUserStocks(
            @RequestHeader("Authorization") String authHeader
    ) {
        String token = authHeader.substring(7);
        String email = jwtService.extractUsername(token);

        return ResponseEntity.ok(stockService.getUserStocks(email));
    }
}
