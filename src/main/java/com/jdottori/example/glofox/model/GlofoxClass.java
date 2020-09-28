package com.jdottori.example.glofox.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class (GlofoxClass used to avoid duplicate with Java.Class)
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GlofoxClass {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    Long id;

    String name;
    
    LocalDate startDate;
    
    LocalDate endDate;
    
    int capacity;
    
}