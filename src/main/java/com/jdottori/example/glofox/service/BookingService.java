package com.jdottori.example.glofox.service;

import com.jdottori.example.glofox.model.Booking;
import com.jdottori.example.glofox.model.InvalidBookingException;
import com.jdottori.example.glofox.model.dto.BookingDto;
import com.jdottori.example.glofox.repository.BookingRepository;

import org.springframework.stereotype.Service;

@Service
public class BookingService {

	private final BookingRepository bookingRepository;

	public BookingService(BookingRepository bookingRepository) {
		this.bookingRepository = bookingRepository;
	}

	public BookingDto createBooking(BookingDto newBookingDto) {
		if (newBookingDto.getName() == null)
			throw new InvalidBookingException("Must have person name.");
		if (newBookingDto.getDate() == null)
			throw new InvalidBookingException("Must have date.");
		var booking = new Booking();
		booking.setPersonName(newBookingDto.getName());
		booking.setDate(newBookingDto.getDate());

		booking = bookingRepository.save(booking);
		newBookingDto.setId(booking.getId());
		return newBookingDto;
	}

}
