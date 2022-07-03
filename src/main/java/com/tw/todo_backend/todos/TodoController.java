package com.tw.todo_backend.todos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
}
