package com.example.repositories;

import com.example.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Integer> {

    public Person findBySotu(String sotu);

    public Person findByLastNameAndFirstName(String lastName, String firstName);

}
