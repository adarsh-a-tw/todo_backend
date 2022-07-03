package com.tw.todo_backend.todos;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
public class TodoRepositoryTest {

    private final List<Todo> mockTodos = new ArrayList<>(
            List.of(
                    new Todo("Eat thrice", true),
                    new Todo("Sleep Twice", true)
            ));

    @Autowired
    private TodoRepository todoRepository;

    @AfterEach
    public void destroy() {
        todoRepository.deleteAll();
    }

    @BeforeEach
    public void initialise() {
        todoRepository.saveAll(this.mockTodos);
    }

    @Test
    public void shouldGetAllTodos() {
        List<Todo> todoList = todoRepository.findAll();

        assertThat(todoList, is(this.mockTodos));
    }

    @Test
    public void shouldSaveTodo() {
        Todo todo = new Todo("Example todo", false);

        Todo savedTodo = todoRepository.save(todo);

        assertThat(savedTodo, is(todo));
    }

    @Test
    public void shouldGetTodoForValidId() {
        Todo todo = new Todo("Example todo", false);
        Todo savedTodo = todoRepository.save(todo);

        Todo fetchedTodo = todoRepository.findById(savedTodo.getId()).get();

        assertThat(fetchedTodo, is(savedTodo));
    }

    @Test
    public void shouldReturnEmptyForInvalidId() {
        Optional<Todo> optionalTodo = todoRepository.findById(1000000L);

        assertThat(optionalTodo.isEmpty(), is(true));
    }
}
