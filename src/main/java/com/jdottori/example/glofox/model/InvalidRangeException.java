package com.jdottori.example.glofox.model;

import java.time.LocalDate;

import lombok.Getter;

@Getter
public class InvalidRangeException extends RuntimeException {

    private static final long serialVersionUID = 8277172630490293889L;

    private LocalDate startDate;

    private LocalDate endDate;

    public InvalidRangeException(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

}
