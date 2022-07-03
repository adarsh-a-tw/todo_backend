package com.tw.todo_backend.todos;

import com.tw.todo_backend.todos.exceptions.TodoNotFoundException;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TodoServiceTest {

    @MockBean
    private TodoRepository todoRepository;

    @Captor
    ArgumentCaptor<Todo> todoArgumentCaptor;

    @Captor
    ArgumentCaptor<Long> idArgumentCaptor;

    @Test
    public void shouldGetAllTodos() {
        List<Todo> toDoList = new ArrayList<>();
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
        when(todoRepository.findById(id)).thenReturn(Optional.empty());
        TodoService todoService = new TodoService(todoRepository);

        assertThrows(TodoNotFoundException.class, () -> todoService.getTodo(id));
    }

    @Test
    public void shouldUpdateTodoGivenId() throws TodoNotFoundException {
        long id = 1;
        Todo todo = new Todo(id, "Example Todo", false);
        when(todoRepository.save(todo)).thenReturn(todo);
        when(todoRepository.findById(id)).thenReturn(Optional.of(todo));
        TodoService todoService = new TodoService(todoRepository);

        Todo updatedTodo = todoService.updateTodo(todo, id);

        assertThat(updatedTodo, is(todo));
    }

    @Test
    public void shouldRaiseExceptionWhenTryingToUpdateTodoThatDoesNotExist() {
        long id = 1;
        Todo todo = new Todo(2, "Example Todo", false);
        when(todoRepository.findById(id)).thenReturn(Optional.empty());
        TodoService todoService = new TodoService(todoRepository);

        assertThrows(TodoNotFoundException.class, () -> todoService.updateTodo(todo, id));
    }

    @Test
    public void shouldDeleteTodoGivenId() throws TodoNotFoundException {
        long id = 10;
        Todo todo = new Todo(id, "Example Todo", false);
        when(todoRepository.findById(id)).thenReturn(Optional.of(todo));
        TodoService todoService = new TodoService(todoRepository);

        todoService.deleteTodo(id);

        verify(this.todoRepository, times(1)).findById(idArgumentCaptor.capture());
        verify(this.todoRepository, times(1)).delete(todoArgumentCaptor.capture());
        assertThat(todoArgumentCaptor.getValue(), Is.is(todo));
        assertThat(idArgumentCaptor.getValue(), Is.is(id));
    }

    @Test
    public void shouldRaiseExceptionWhenTryingToDeleteTodoThatDoesNotExist() {
        long id = 100000L;
        when(todoRepository.findById(id)).thenReturn(Optional.empty());
        TodoService todoService = new TodoService(todoRepository);

        assertThrows(TodoNotFoundException.class, () -> todoService.deleteTodo(id));
    }
}
