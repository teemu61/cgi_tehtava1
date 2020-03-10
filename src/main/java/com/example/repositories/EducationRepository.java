package com.example.repositories;

import com.example.domain.Education;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.Set;

public interface EducationRepository extends JpaRepository<Education, Integer> {

    public Iterable<Education> findEducationByPid(Integer id);
    
}
