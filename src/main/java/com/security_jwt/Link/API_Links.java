package com.security_jwt.Link;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
public class API_Links {


    private String financialAPI;

    private String APIkey = "7k0sCkhL4aw1lSb0OhKRLibal5qpHV85";

    // Setting the HTTP get calls to the API

    public void setFinancialAPI(String ticker, int limit) {
        String url = "https://api.polygon.io/vX/reference/financials?ticker="+ticker+"&order=desc&limit="+limit+"&sort=filing_date&apiKey="+APIkey;
        this.financialAPI = url;
    }


    // Getting the HTTP get calls to the API

    public String getFinancialAPI() {
        return financialAPI;
    }
}
