package com.jdottori.example.glofox.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.jdottori.example.glofox.model.GlofoxClass;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface ClassRepository extends CrudRepository<GlofoxClass, Long> {

    
    @Transactional(readOnly = true)
    @Query("SELECT c " +
            "FROM GlofoxClass c " +
            "WHERE  c.name = :name " +
            "  AND  c.startDate <= :endDate " + 
            "  AND  c.endDate >= :startDate")
    List<GlofoxClass> findByNameAndPeriod(String name, LocalDate startDate, LocalDate endDate);
    
}
