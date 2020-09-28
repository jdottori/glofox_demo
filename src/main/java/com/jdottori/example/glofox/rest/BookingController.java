package com.jdottori.example.glofox.rest;

import com.jdottori.example.glofox.model.InvalidBookingException;
import com.jdottori.example.glofox.model.dto.BookingDto;
import com.jdottori.example.glofox.service.BookingService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bookings")
public class BookingController {

	private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookingDto createClass(@RequestBody BookingDto bookingDto) {
        return bookingService.createBooking(bookingDto);
    }

    @ResponseBody
    @ExceptionHandler(InvalidBookingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String classNotFoundHandler(InvalidBookingException ex) {
        return ex.getMessage();
    }


}
