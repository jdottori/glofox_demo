package com.jdottori.example.glofox.service;

import java.util.Optional;

import javax.transaction.Transactional;

import com.jdottori.example.glofox.model.DuplicateClassException;
import com.jdottori.example.glofox.model.GlofoxClass;
import com.jdottori.example.glofox.model.InvalidRangeException;
import com.jdottori.example.glofox.model.dto.GlofoxClassDto;
import com.jdottori.example.glofox.repository.ClassRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClassService {

    private final ClassRepository classRepository;

    @Autowired
    public ClassService(ClassRepository classRepository) {
        this.classRepository = classRepository;
    }

    Optional<GlofoxClassDto> getClass(Long id) {
        return classRepository.findById(id)
                .map(c -> new GlofoxClassDto(c.getId(), c.getName(), c.getStartDate(), c.getEndDate(), c.getCapacity()));
    }

    @Transactional
	public void createClass(GlofoxClassDto classDto) {
        if(classDto.getStartDate().isAfter(classDto.getEndDate()))
            throw new InvalidRangeException(classDto.getStartDate(), classDto.getEndDate());
        var others = classRepository.findByNameAndPeriod(classDto.getName(), classDto.getStartDate(), classDto.getEndDate());
        if(others.size() != 0)
            throw new DuplicateClassException(classDto, others);
        var c = new GlofoxClass();
        c.setName(classDto.getName());
        c.setStartDate(classDto.getStartDate());
        c.setEndDate(classDto.getEndDate());
        c.setCapacity(classDto.getCapacity());

        c = classRepository.save(c);
        classDto.setId(c.getId());
	}

}
