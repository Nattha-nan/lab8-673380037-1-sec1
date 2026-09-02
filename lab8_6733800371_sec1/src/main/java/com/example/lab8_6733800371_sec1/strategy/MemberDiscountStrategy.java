package com.example.lab8_6733800371_sec1.strategy;

import org.springframework.stereotype.Component;

@Component("MEMBER")
public class MemberDiscountStrategy implements DiscountStrategy {
    @Override
    public double applyDiscount(double price) {
        return price * 0.90; // ลด 10%
    }
}