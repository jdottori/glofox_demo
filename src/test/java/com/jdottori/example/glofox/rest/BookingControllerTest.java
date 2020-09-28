package com.jdottori.example.glofox.rest;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import com.jdottori.example.glofox.model.InvalidBookingException;
import com.jdottori.example.glofox.model.dto.BookingDto;
import com.jdottori.example.glofox.service.BookingService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(BookingController.class)
public class BookingControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    BookingService bookingService;

    
    @Test
    public void shouldCreateClass() throws Exception {
        // Arrange
        final String newBooking =  "{\"name\":\"Javier\", \"date\":\"2020-09-28\"}";
        
        final LocalDate date = LocalDate.of(2020, 9, 28);
        final BookingDto booking = new BookingDto();
        booking.setName("Javier");
        booking.setDate(date);

        when(bookingService.createBooking(any())).thenReturn(booking);

        // Act and Assert
        mockMvc.perform(post("/bookings")
                            .content(newBooking)
                            .accept(APPLICATION_JSON)
                            .contentType(APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Javier")));
    }

    @Test
    public void shouldReturnInvalidBookingError() throws Exception {
        // Arrange
        // missing name in this json
        final String newClass =  "{\"startDate\":\"2020-09-28\", \"endDate\":\"2020-09-28\", \"capacity\":\"10\"}"; 
        
        when(bookingService.createBooking(any())).thenThrow(new InvalidBookingException("Invalid booking."));

        // Act and Assert
        mockMvc.perform(post("/bookings")
                            .content(newClass)
                            .accept(APPLICATION_JSON)
                            .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
