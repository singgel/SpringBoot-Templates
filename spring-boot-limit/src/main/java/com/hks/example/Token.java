package com.hks.example;

public enum Token {
    SUCCESS,
    FAILED;

    public boolean isSuccess() {
        return this.equals(SUCCESS);
    }

    public boolean isFailed() {
        return this.equals(FAILED);
    }
}

