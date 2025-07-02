package com.TimeSheet.Exception;

public class StatusAlreadyExists extends RuntimeException {
    public StatusAlreadyExists(String message) {
        super(message);
    }
}
