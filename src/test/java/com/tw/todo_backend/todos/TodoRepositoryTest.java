package com.tw.todo_backend.todos;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
public class TodoRepositoryTest {

    private final List<Todo> mockTodos = new ArrayList<Todo>(
            List.of(
                    new Todo(1L, "Eat thrice", true),
                    new Todo(2L, "Sleep Twice", true)
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
    public void getAllTodos() {
        List<Todo> todoList = todoRepository.findAll();
        assertThat(todoList, is(this.mockTodos));
    }
}
