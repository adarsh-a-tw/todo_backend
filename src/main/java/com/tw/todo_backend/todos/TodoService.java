package com.tw.todo_backend.todos;

import com.tw.todo_backend.todos.exceptions.TodoNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {
    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    public Todo createTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    public Todo getTodo(long id) throws TodoNotFoundException {
        Optional<Todo> todoOptional = todoRepository.findById(id);
        if (todoOptional.isPresent()) {
            return todoOptional.get();
        }
        throw new TodoNotFoundException(id);
    }

    public Todo updateTodo(Todo todo, long id) throws TodoNotFoundException {
        Todo fetchedTodo = getTodo(id);
        todo.setId(fetchedTodo.getId());
        return todoRepository.save(todo);
    }

    public void deleteTodo(long id) throws TodoNotFoundException {
        Todo fetchedTodo = getTodo(id);
        todoRepository.delete(fetchedTodo);
    }
}
