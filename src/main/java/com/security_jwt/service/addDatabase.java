package com.security_jwt.service;

import com.security_jwt.DTO.RF_DTO;
import com.security_jwt.user.Stock;
import com.security_jwt.user.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class addDatabase {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private GetData getData;

    public void addToDatabase(Long id) {
        Optional<Stock> portfolio = stockRepository.findById(id);

        RF_DTO rf = getData.getFinancialData();

        String stockName = rf.getResults().get(0).getCompanyName();

        portfolio.get().setStockName(stockName);

        //stockRepository.save(portfolio.get());
    }
}
