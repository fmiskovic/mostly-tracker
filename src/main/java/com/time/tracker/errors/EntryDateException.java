package com.time.tracker.errors;

public class EntryDateException extends RuntimeException {

    public EntryDateException() {
        super("Incorrect entry date. It is restricted by project start and end date.");
    }

    public EntryDateException(String message) {
        super(message);
    }
}
