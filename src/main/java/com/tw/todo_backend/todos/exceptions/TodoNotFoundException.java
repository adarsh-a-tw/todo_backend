package com.tw.todo_backend.todos.exceptions;

import org.springframework.http.HttpStatus;


public class TodoNotFoundException extends GenericException {

    public TodoNotFoundException(long id) {
        super("Todo does not exist with id " + id,HttpStatus.BAD_REQUEST);
    }
}
