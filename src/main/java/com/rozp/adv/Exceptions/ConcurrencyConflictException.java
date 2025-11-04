package com.rozp.adv.Exceptions;

public class ConcurrencyConflictException extends RuntimeException {
    private final Integer currentVersion;

    public ConcurrencyConflictException(String message, Integer currentVersion) {
        super(message);
        this.currentVersion = currentVersion;
    }

    public Integer getCurrentVersion() {
        return currentVersion;
    }
}