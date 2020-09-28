package com.jdottori.example.glofox.model;

import java.util.List;

import com.jdottori.example.glofox.model.dto.GlofoxClassDto;

import lombok.Getter;

@Getter
public class DuplicateClassException extends RuntimeException {

    private static final long serialVersionUID = -6220044077004691576L;

    private GlofoxClassDto classDto;

    private List<GlofoxClass> others;

    public DuplicateClassException(GlofoxClassDto classDto, List<GlofoxClass> others) {
        this.classDto = classDto;
        this.others = others;
	}
    
}
