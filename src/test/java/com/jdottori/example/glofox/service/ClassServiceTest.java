package com.jdottori.example.glofox.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDate;

import com.jdottori.example.glofox.model.DuplicateClassException;
import com.jdottori.example.glofox.model.GlofoxClass;
import com.jdottori.example.glofox.model.InvalidClassException;
import com.jdottori.example.glofox.model.dto.GlofoxClassDto;
import com.jdottori.example.glofox.repository.ClassRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ClassServiceTest {

    @Autowired
    ClassRepository classRepository;

    @Autowired
    ClassService classService;


    @BeforeEach
    void cleanRepository() {
        classRepository.deleteAll();
    }

    @Test
	public void shouldCreateClass() {
        final String name = "Test class name";
        final LocalDate date = LocalDate.of(2020, 3, 2);
        final int capacity = 10;
        GlofoxClassDto classDto = new GlofoxClassDto();
        classDto.setName(name);
        classDto.setStartDate(date);
        classDto.setEndDate(date);
        classDto.setCapacity(capacity);

        classService.createClass(classDto);

		Assertions.assertNotEquals(classDto.getId(), -1l);
    }

    @Test
	public void shouldFailWithDuplicateClassSameDay() {
        Assertions.assertThrows(DuplicateClassException.class, () -> {

            //Arrange: create previous class same day and name
            GlofoxClass c = new GlofoxClass();
            final String name = "Same name";
            final LocalDate date = LocalDate.of(2020, 2, 2);
            final int capacity = 10;
            c.setName(name);
            c.setCapacity(capacity);
            c.setStartDate(date);
            c.setEndDate(date);
            classRepository.save(c);
            
            //Act
            GlofoxClassDto classDto = new GlofoxClassDto();
            classDto.setName(name);
            classDto.setStartDate(date);
            classDto.setEndDate(date);
            classDto.setCapacity(capacity);

            classService.createClass(classDto);
            
        });
    }
    
    @Test
	public void shouldFailWithNullName() {
        Assertions.assertThrows(InvalidClassException.class, () -> {

            //Arrange
            final LocalDate date = LocalDate.of(2020, 2, 2);
            final int capacity = 10;
            
            //Act
            GlofoxClassDto classDto = new GlofoxClassDto();
            classDto.setStartDate(date);
            classDto.setEndDate(date);
            classDto.setCapacity(capacity);
            classService.createClass(classDto);
        });
    }

    @Test
	public void shouldFailWithNullDate() {
        Assertions.assertThrows(InvalidClassException.class, () -> {

            //Arrange
            final String name = "Same name";
            final int capacity = 10;
            
            //Act
            GlofoxClassDto classDto = new GlofoxClassDto();
            classDto.setName(name);
            classDto.setCapacity(capacity);
            classService.createClass(classDto);
        });
    }


    @Test
	public void shouldFailWithNonPositiveCapacity() {
        Assertions.assertThrows(InvalidClassException.class, () -> {

            //Arrange
            final String name = "Same name";
            final LocalDate date = LocalDate.of(2020, 2, 2);
            final int capacity = 10;
            
            //Act
            GlofoxClassDto classDto = new GlofoxClassDto();
            classDto.setName(name);
            classDto.setStartDate(date);
            classDto.setEndDate(date);
            classDto.setCapacity(0);
            classService.createClass(classDto);
        });
    }
    
    @Test
    public void shouldReturnCreatedClass() {
        GlofoxClass c = new GlofoxClass();
        final String name = "Test class name";
        final LocalDate date = LocalDate.of(2020, 2, 2);
        final int capacity = 10;
        c.setName(name);
        c.setCapacity(capacity);
        c.setStartDate(date);
        c.setEndDate(date);
        classRepository.save(c);

        GlofoxClassDto classDto = classService.getClass(c.getId()).get();

        Assertions.assertNotNull(classDto, "Class shouldn't be null");
        Assertions.assertEquals(classDto.getName(), name);
		Assertions.assertEquals(classDto.getStartDate(), date);
        Assertions.assertEquals(classDto.getEndDate(), date);
		Assertions.assertEquals(classDto.getCapacity(), capacity);
    }
    
    @Test
	public void shouldReturnNullForNotExistingClass() {
		GlofoxClassDto classDto = classService.getClass(123l).orElse(null);

		Assertions.assertNull(classDto);
	}


}
