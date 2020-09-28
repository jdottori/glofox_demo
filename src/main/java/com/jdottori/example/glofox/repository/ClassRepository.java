package com.jdottori.example.glofox.repository;

import com.jdottori.example.glofox.model.GlofoxClass;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRepository extends CrudRepository<GlofoxClass, Long> {
    
}
