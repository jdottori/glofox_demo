package com.jdottori.example.glofox.service;

import java.time.LocalDate;

import com.jdottori.example.glofox.model.InvalidBookingException;
import com.jdottori.example.glofox.model.dto.BookingDto;
import com.jdottori.example.glofox.repository.BookingRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BookingServiceTest {

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    BookingService bookingService;


    @BeforeEach
    void cleanRepository() {
        bookingRepository.deleteAll();
    }

    @Test
	public void shouldCreateBooking() {
        final String name = "Javier";
        final LocalDate date = LocalDate.of(2020, 9, 29);
        BookingDto bookingDto = new BookingDto();
        bookingDto.setName(name);
        bookingDto.setDate(date);

        bookingService.createBooking(bookingDto);

		Assertions.assertNotEquals(bookingDto.getId(), -1l);
    }

    @Test
	public void shouldFailWithNullName() {
        Assertions.assertThrows(InvalidBookingException.class, () -> {
            final LocalDate date = LocalDate.of(2020, 9, 29);
            BookingDto bookingDto = new BookingDto();
            bookingDto.setDate(date);
    
            bookingService.createBooking(bookingDto);
        });
    }

    @Test
	public void shouldFailWithNullDate() {
        Assertions.assertThrows(InvalidBookingException.class, () -> {
            final String name = "Javier";
            BookingDto bookingDto = new BookingDto();
            bookingDto.setName(name);
    
            bookingService.createBooking(bookingDto);
        });
    }
}
