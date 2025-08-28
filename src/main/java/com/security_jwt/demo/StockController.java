package com.security_jwt.demo;

import com.security_jwt.DTO.RF_DTO;
import com.security_jwt.DTO.StockRequest;
import com.security_jwt.Link.API_Links;
import com.security_jwt.service.GetData;
import com.security_jwt.service.JwtService;
import com.security_jwt.service.StockService;
import com.security_jwt.service.addDatabase;
import com.security_jwt.user.Stock;
import com.security_jwt.user.StockRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/stocks")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    private final API_Links apiLinks;

    private final addDatabase addDatabase;

    private final GetData getData;

    private final StockRepository stockRepository;

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

    @GetMapping("searchFinancialData/{ticker}")
    public RF_DTO searchFinancialData(@RequestHeader("Authorization") String auth, @PathVariable("ticker") String ticker){

        stockService.VerifyUser(auth);

        String tickerStr = ticker.toUpperCase();
        apiLinks.setFinancialAPI(tickerStr, 1);
        return getData.getFinancialData();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteStock(@RequestHeader("Authorization") String auth, @PathVariable("id") Long id) {
        stockService.delete(auth, id);
        return ResponseEntity.ok("Deleted");
    }

    @PutMapping("/updateData/{id}")
    public ResponseEntity<String> updateStock(@RequestHeader("Authorization") String auth, @PathVariable("id") Long Id,
                                         @Valid @RequestBody Map<String, Integer> data){

        stockService.updateStock(auth, Id, data);

        return ResponseEntity.ok("DONE, portfolio updated");
    }
}
