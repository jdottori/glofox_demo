package com.jdottori.example.glofox.model.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GlofoxClassDto {
    Long id = -1l;

    String name;
    
    LocalDate startDate;
    
    LocalDate endDate;
    
    int capacity;
    
}