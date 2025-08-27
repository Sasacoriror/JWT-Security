package com.security_jwt.demo;

import com.security_jwt.DTO.StockRequest;
import com.security_jwt.Link.API_Links;
import com.security_jwt.service.GetData;
import com.security_jwt.service.JwtService;
import com.security_jwt.service.StockService;
import com.security_jwt.service.addDatabase;
import com.security_jwt.user.Stock;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/stocks")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    private final API_Links apiLinks;

    private final addDatabase addDatabase;

    private final GetData getData;

    @PostMapping("/add")
    public ResponseEntity<?> addStock(
            @RequestHeader("Authorization") String authHeader,
            @Valid @RequestBody Stock stockRequest,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }

        String stockName = stockRequest.getStockTicker().toUpperCase();
        apiLinks.setFinancialAPI(stockName, 1);

        getData.getFinancialData();


        Stock savedStock = stockService.saveStock(authHeader, stockRequest);
        addDatabase.addToDatabase(savedStock.getId());

        return ResponseEntity.ok(savedStock);
    }

    @GetMapping
    public ResponseEntity<?> getUserStocks(
            @RequestHeader("Authorization") String authHeader
    ) {
        return ResponseEntity.ok(stockService.getUserStocks(authHeader));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteStock(@RequestHeader("Authorization") String auth, @PathVariable("id") Long id) {
        stockService.delete(auth, id);
        return ResponseEntity.noContent().build();
    }
}
