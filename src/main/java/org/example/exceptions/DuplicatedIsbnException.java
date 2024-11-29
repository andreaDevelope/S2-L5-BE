package org.example.exceptions;

public class DuplicatedIsbnException extends RuntimeException{
    public DuplicatedIsbnException(String message) {
        super(message);
    }
}
