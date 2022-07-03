package com.tw.todo_backend.todos;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@WebMvcTest
public class TodoControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private TodoService toDoService;

    @Test
    void shouldGetAllTodos() throws Exception {
        List<Todo> toDoList = new ArrayList<Todo>();
        toDoList.add(new Todo(1L, "Eat thrice", true));
        toDoList.add(new Todo(2L, "Sleep Twice", true));
        when(toDoService.getAllTodos()).thenReturn(toDoList);

        mockMvc.perform(MockMvcRequestBuilders.get("/todos")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(jsonPath("$", hasSize(2)));
    }
}