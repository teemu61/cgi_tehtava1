package com.example.services;

import com.example.domain.Person;
import com.example.repositories.PersonRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements PersonService {
    private PersonRepository personRepository;

    private Logger log = LogManager.getLogger(PersonServiceImpl.class);

    @Autowired
    public void setPersonRepository(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Iterable<Person> listAllPersons() {
        log.info("personservice listAllPersons called");
        return personRepository.findAll();
    }

    @Override
    public Person getPersonById(Integer id) {
        return personRepository.findById(id).orElse(null);
    }

    @Override
    public Person savePerson(Person person) {
        log.info("PersonServiceImpl save for "+person.getFirstName());
        return personRepository.saveAndFlush(person);
    }
}
