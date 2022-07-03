package com.tw.todo_backend.todos;

import com.tw.todo_backend.todos.exceptions.TodoNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    @Test
    public void shouldCreateTodo() {
        Todo todo = new Todo("Example Todo", false);
        when(todoRepository.save(todo)).thenReturn(todo);
        TodoService todoService = new TodoService(todoRepository);

        Todo createdTodo = todoService.createTodo(todo);

        assertThat(createdTodo, is(todo));
    }

    @Test
    public void shouldGetTodoGivenId() throws TodoNotFoundException {
        long id = 1;
        Todo todo = new Todo(id, "Example Todo", false);
        when(todoRepository.findById(id)).thenReturn(Optional.of(todo));
        TodoService todoService = new TodoService(todoRepository);

        Todo fetchedTodo = todoService.getTodo(id);

        assertThat(fetchedTodo, is(todo));
    }

    @Test
    public void shouldRaiseExceptionWhenTryingToFetchTodoThatDoesNotExist() {
        long id = 1;
        Todo todo = new Todo(2, "Example Todo", false);
        when(todoRepository.findById(id)).thenReturn(Optional.empty());
        TodoService todoService = new TodoService(todoRepository);

        assertThrows(TodoNotFoundException.class,()->todoService.getTodo(id));
    }
}
