package com.tw.todo_backend.todos;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TodoServiceTest {

    @MockBean
    private TodoRepository todoRepository;

    @Test
    public void shouldGetAllTodos() {
        List<Todo> toDoList = new ArrayList<Todo>();
        toDoList.add(new Todo(1L, "Eat thrice", true));
        toDoList.add(new Todo(2L, "Sleep Twice", true));
        when(todoRepository.findAll()).thenReturn(toDoList);
        TodoService toDoService = new TodoService(todoRepository);

        List<Todo> allTodos = toDoService.getAllTodos();

        assertThat(allTodos, is(toDoList));
    }
}
