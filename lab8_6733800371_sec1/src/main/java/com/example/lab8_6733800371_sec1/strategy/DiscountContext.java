package com.example.lab8_6733800371_sec1.strategy;

public class DiscountContext {

    public double calculate(String discountType, double price) {
        DiscountStrategy strategy = switch (discountType == null ? "NONE" : discountType) {
            case "MEMBER" -> new MemberDiscountStrategy();
            case "SEASONAL" -> new SeasonalSaleStrategy();
            default -> new NoDiscountStrategy();
        };
        return strategy.applyDiscount(price);
    }
}