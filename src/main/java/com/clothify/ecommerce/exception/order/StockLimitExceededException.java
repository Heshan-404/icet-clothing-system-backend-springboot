package com.clothify.ecommerce.exception.order;

public class StockLimitExceededException extends RuntimeException {
    public StockLimitExceededException(String message) {
        super(message);
    }
}
