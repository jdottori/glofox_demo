package com.jdottori.example.glofox.model.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class BookingDto {
    
    Long id = -1l;

    String name;

    LocalDate date;
}
