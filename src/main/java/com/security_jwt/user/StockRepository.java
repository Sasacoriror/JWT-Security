package com.security_jwt.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long> {
    List<Stock> findByUser(User user);

    Optional<Stock> findByStockNameAndUser(String stockName, User user);
}
