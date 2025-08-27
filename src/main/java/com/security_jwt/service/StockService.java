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
    private final JwtService jwtService;

    public Stock saveStock(String token, Stock stockRequest) {
        String email = jwtService.extractUsername(token.substring(7)); // remove "Bearer "
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
}

