package com.jdottori.example.glofox.model;

public class InvalidBookingException extends RuntimeException {

	private static final long serialVersionUID = 1966429553136409895L;

	public InvalidBookingException(String message) {
        super(message);
	}

}
