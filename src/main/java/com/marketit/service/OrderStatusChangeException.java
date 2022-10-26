package com.marketit.service;

public class OrderStatusChangeException extends RuntimeException {
    public OrderStatusChangeException(String message) {
        super(message);
    }
}
