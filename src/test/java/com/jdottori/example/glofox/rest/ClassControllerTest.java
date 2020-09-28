package com.jdottori.example.glofox.rest;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;

import com.jdottori.example.glofox.model.DuplicateClassException;
import com.jdottori.example.glofox.model.InvalidClassException;
import com.jdottori.example.glofox.model.dto.GlofoxClassDto;
import com.jdottori.example.glofox.service.ClassService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ClassController.class)
public class ClassControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    ClassService classService;

    
    @Test
    public void shouldCreateClass() throws Exception {
        // Arrange
        String newClass =  "{\"name\":\"Pilates\", \"startDate\":\"2020-09-28\", \"endDate\":\"2020-09-28\", \"capacity\":\"10\"}";
        
        final LocalDate date = LocalDate.of(2020, 9, 28);
        final GlofoxClassDto c = new GlofoxClassDto();
        c.setName("Pilates");
        c.setStartDate(date);
        c.setEndDate(date);
        c.setCapacity(10);

        when(classService.createClass(any())).thenReturn(c);

        // Act and Assert
        mockMvc.perform(post("/classes")
                            .content(newClass)
                            .accept(APPLICATION_JSON)
                            .contentType(APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Pilates")));
    }

    @Test
    public void shouldReturnDuplicationError() throws Exception {
        // Arrange
        String newClass =  "{\"name\":\"Pilates\", \"startDate\":\"2020-09-28\", \"endDate\":\"2020-09-28\", \"capacity\":\"10\"}";
        
        when(classService.createClass(any())).thenThrow(new DuplicateClassException(new GlofoxClassDto(), new ArrayList<>()));

        // Act and Assert
        mockMvc.perform(post("/classes")
                            .content(newClass)
                            .accept(APPLICATION_JSON)
                            .contentType(APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    public void shouldReturnInvalidClassError() throws Exception {
        // Arrange
        // missing name in this json
        String newClass =  "{\"startDate\":\"2020-09-28\", \"endDate\":\"2020-09-28\", \"capacity\":\"10\"}"; 
        
        when(classService.createClass(any())).thenThrow(new InvalidClassException("Invalid class."));

        // Act and Assert
        mockMvc.perform(post("/classes")
                            .content(newClass)
                            .accept(APPLICATION_JSON)
                            .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
