package com.example.lab8_6733800371_sec1.strategy;

import org.springframework.stereotype.Component;

@Component("SEASONAL")
public class SeasonalSaleStrategy implements DiscountStrategy {
    @Override
    public double applyDiscount(double price) {
        return price * 0.80; // ลด 20%
    }
}