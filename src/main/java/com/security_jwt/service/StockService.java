package com.security_jwt.service;

import com.security_jwt.user.Stock;
import com.security_jwt.user.StockRepository;
import com.security_jwt.user.User;
import com.security_jwt.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;
    private final UserRepository userRepository;

    public Stock addStock(String email, String ticker, Double price, Integer shares) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Stock stock = Stock.builder()
                .ticker(ticker)
                .price(price)
                .shares(shares)
                .user(user)
                .build();

        return stockRepository.save(stock);
    }

    public List<Stock> getUserStocks(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return stockRepository.findByUser(user);
    }
}

