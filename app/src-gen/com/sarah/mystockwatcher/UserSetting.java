package com.sarah.mystockwatcher;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "USER_SETTING".
 */
public class UserSetting {

    private Long id;
    private Double expectedRate;
    private String stockSymbols;

    public UserSetting() {
    }

    public UserSetting(Long id) {
        this.id = id;
    }

    public UserSetting(Long id, Double expectedRate, String stockSymbols) {
        this.id = id;
        this.expectedRate = expectedRate;
        this.stockSymbols = stockSymbols;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getExpectedRate() {
        return expectedRate;
    }

    public void setExpectedRate(Double expectedRate) {
        this.expectedRate = expectedRate;
    }

    public String getStockSymbols() {
        return stockSymbols;
    }

    public void setStockSymbols(String stockSymbols) {
        this.stockSymbols = stockSymbols;
    }

}
