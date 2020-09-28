package com.jdottori.example.glofox.model;

import lombok.Getter;

@Getter
public class InvalidClassException extends RuntimeException {

    private static final long serialVersionUID = 8277172630490293889L;

    public InvalidClassException(String message) {
        super(message);
    }

}
