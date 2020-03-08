package com.example.services;


import com.example.domain.Person;

public interface PersonService {
    Iterable<Person> listAllPersons();

    Person getPersonById(Integer id);

    Person savePerson(Person person);
}
