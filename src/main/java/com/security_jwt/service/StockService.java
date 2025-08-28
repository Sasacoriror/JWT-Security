package com.security_jwt.service;

import com.security_jwt.user.Stock;
import com.security_jwt.user.StockRepository;
import com.security_jwt.user.User;
import com.security_jwt.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public Stock saveStock(String token, Stock stockRequest) {
        String email = jwtService.extractUsername(token.substring(7));
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        stockRequest.setUser(user);

        return stockRepository.save(stockRequest);
    }

    public List<Stock> getUserStocks(String token) {
        String email = jwtService.extractUsername(token.substring(7));
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return stockRepository.findByUser(user);
    }

    public User VerifyUser(String token) {
        String email = jwtService.extractUsername(token.substring(7));
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public void updateStock(String token, Long Id, Map<String, Integer> data) {
        String email = jwtService.extractUsername(token.substring(7));

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Stock stock = stockRepository.findById(Id)
                .orElseThrow(() -> new RuntimeException("Stock not found"));

        if (stock.getUser().getId() != user.getId()) {
            throw new RuntimeException("Stock doesn't belong to user");
        }

        if (data.containsKey("priceInn")){
            stock.setStockPrice(data.get("priceInn"));
        }

        if (data.containsKey("numberOfShares")){
            stock.setStockQuantity(data.get("numberOfShares"));
        }

        stockRepository.save(stock);
    }



    public void delete(String token, Long Id) {
        String email = jwtService.extractUsername(token.substring(7));

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Stock stock = stockRepository.findById(Id)
                .orElseThrow(() -> new RuntimeException("Stock not found"));

        if (stock.getUser().getId() != user.getId()) {
            throw new RuntimeException("Stock doesn't belong to user");
        }

        stockRepository.delete(stock);
    }
}

