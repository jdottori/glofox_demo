package com.jdottori.example.glofox.rest;

import com.jdottori.example.glofox.model.DuplicateClassException;
import com.jdottori.example.glofox.model.InvalidClassException;
import com.jdottori.example.glofox.model.dto.GlofoxClassDto;
import com.jdottori.example.glofox.service.ClassService;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@RequestMapping("/classes")
public class ClassController {
    
	private final ClassService classService;

    public ClassController(ClassService classService) {
        this.classService = classService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GlofoxClassDto createClass(@RequestBody GlofoxClassDto cDto) {
        return classService.createClass(cDto);
    }

    @ResponseBody
    @ExceptionHandler(DuplicateClassException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String classNotFoundHandler(DuplicateClassException ex) {
        //Specific conflicting dates could be reported in a JSON object with an array 
        return String.format("Duplicated class with same name in range with other %s classes.",ex.getOthers().size());
    }
    
    @ResponseBody
    @ExceptionHandler(InvalidClassException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String classNotFoundHandler(InvalidClassException ex) {
        return ex.getMessage(); //can wrap in json object
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GlofoxClassDto getClass(@PathVariable Long id) throws NotFoundException {
        return classService.getClass(id).orElseThrow(NotFoundException::new);
    }
    
    @ResponseBody
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String classNotFoundHandler(NotFoundException ex) {
        //I would prefer to use an specific object and add class ID to the returned error 
        return "Not found searched class";
    }


}
