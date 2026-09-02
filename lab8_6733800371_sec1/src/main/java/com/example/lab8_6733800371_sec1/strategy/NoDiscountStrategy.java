package com.example.lab8_6733800371_sec1.strategy;

import org.springframework.stereotype.Component;

@Component("NONE")
public class NoDiscountStrategy implements DiscountStrategy {
    @Override
    public double applyDiscount(double price) {
        return price;
    }
}