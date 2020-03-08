package com.example.repositories;

import com.example.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends JpaRepository<Person, Integer> {
}
