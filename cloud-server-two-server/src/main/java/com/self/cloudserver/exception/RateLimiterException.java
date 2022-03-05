package com.self.cloudserver.exception;

public class RateLimiterException extends RuntimeException {

    public RateLimiterException(String message) {
        super(message);
    }

}
