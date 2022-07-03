package com.tw.todo_backend.todos;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tw.todo_backend.todos.exceptions.TodoNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest
public class TodoControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Captor
    ArgumentCaptor<Long> argumentCaptor;

    @MockBean
    private TodoService toDoService;

    @Test
    void shouldGetAllTodos() throws Exception {
        List<Todo> toDoList = new ArrayList<>();
        toDoList.add(new Todo(1L, "Eat thrice", true));
        toDoList.add(new Todo(2L, "Sleep Twice", true));
        when(toDoService.getAllTodos()).thenReturn(toDoList);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/todos")
                .contentType(MediaType.APPLICATION_JSON)
        );

        result.andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void shouldCreateTodo() throws Exception {
        Todo todo = new Todo("Example Todo", false);
        when(toDoService.createTodo(todo)).thenReturn(todo);
        ObjectMapper objectMapper = new ObjectMapper();
        String todoJson = objectMapper.writeValueAsString(todo);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/todos")
                .contentType(MediaType.APPLICATION_JSON).content(todoJson));

        result.andExpect(status().isCreated()).andExpect(jsonPath("$").value(todo));
    }

    @Test
    void shouldGetTodoGivenId() throws Exception {
        long id = 1L;
        Todo sampleTodo = new Todo(id, "Sample Todo", false);
        when(this.toDoService.getTodo(id)).thenReturn(sampleTodo);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get(String.format("/todos/%s", id))
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(jsonPath("$").value(sampleTodo));
    }

    @Test
    void shouldRespondWithBadRequestWhenTryingToFetchTodoWithInvalidId() throws Exception {
        long id = 100000L;
        when(this.toDoService.getTodo(id)).thenThrow(new TodoNotFoundException(id));

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get(String.format("/todos/%s", id))
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isBadRequest());
    }

    @Test
    void shouldUpdateTodo() throws Exception {
        long id = 1L;
        Todo sampleTodo = new Todo(id, "Sample Todo", false);
        when(this.toDoService.updateTodo(sampleTodo, id)).thenReturn(sampleTodo);
        ObjectMapper objectMapper = new ObjectMapper();
        String todoJson = objectMapper.writeValueAsString(sampleTodo);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.put(String.format("/todos/%s", id))
                .contentType(MediaType.APPLICATION_JSON).content(todoJson));

        result.andExpect(status().isAccepted()).andExpect(jsonPath("$").value(sampleTodo));
    }

    @Test
    void shouldRespondWithBadRequestWhenTryingToUpdateTodoWithInvalidId() throws Exception {
        long id = 100000L;
        Todo sampleTodo = new Todo(1L, "Sample Todo", false);
        when(this.toDoService.updateTodo(sampleTodo, id)).thenThrow(new TodoNotFoundException(id));
        ObjectMapper objectMapper = new ObjectMapper();
        String todoJson = objectMapper.writeValueAsString(sampleTodo);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.put(String.format("/todos/%s", id))
                .contentType(MediaType.APPLICATION_JSON).content(todoJson));

        result.andExpect(status().isBadRequest());
    }

    @Test
    void shouldDeleteTodo() throws Exception {
        long id = 1L;

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.delete(String.format("/todos/%s", id))
                .contentType(MediaType.APPLICATION_JSON));

        verify(this.toDoService, times(1)).deleteTodo(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue(), is(1L));
        result.andExpect(status().isNoContent());
    }

    @Test
    void shouldRespondWithBadRequestWhenTryingToDeleteTodoWithInvalidId() throws Exception {
        long id = 100000L;
        Mockito.doThrow(new TodoNotFoundException(id)).when(this.toDoService).deleteTodo(id);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.delete(String.format("/todos/%s", id))
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isBadRequest());
    }
}
