package com.tw.todo_backend.todos;

import com.tw.todo_backend.todos.exceptions.GenericException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class TodoController {

    @Autowired
    private TodoService todoService;

    @RequestMapping(value = "/todos", method = RequestMethod.GET)
    public ResponseEntity<List<Todo>> getAllTodos() {
        return new ResponseEntity<>(this.todoService.getAllTodos(), HttpStatus.OK);
    }

    @RequestMapping(value = "/todos", method = RequestMethod.POST)
    public ResponseEntity<Todo> createTodo(@RequestBody Todo todo) {
        return new ResponseEntity<>(this.todoService.createTodo(todo), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/todos/{id}", method = RequestMethod.GET)
    public ResponseEntity<Todo> getTodo(@PathVariable long id) {
        try {
            return new ResponseEntity<>(this.todoService.getTodo(id), HttpStatus.OK);
        } catch (GenericException exception) {
            throw new ResponseStatusException(exception.getStatus(), exception.getMessage(), exception);
        }
    }

    @RequestMapping(value = "/todos/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Todo> updateTodo(@PathVariable long id, @RequestBody Todo todo) {
        try {
            return new ResponseEntity<>(this.todoService.updateTodo(todo, id), HttpStatus.ACCEPTED);
        } catch (GenericException exception) {
            throw new ResponseStatusException(exception.getStatus(), exception.getMessage(), exception);
        }
    }
}
