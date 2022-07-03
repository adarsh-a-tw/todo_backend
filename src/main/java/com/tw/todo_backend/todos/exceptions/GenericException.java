package com.tw.todo_backend.todos.exceptions;

import org.springframework.http.HttpStatus;

public class GenericException extends Exception {

    private final HttpStatus status;

    public GenericException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return this.status;
    }
}
