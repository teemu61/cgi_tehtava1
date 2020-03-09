package com.example.repositories;

import com.example.configuration.RepositoryConfiguration;
import com.example.domain.Person;
import com.example.services.PersonServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {RepositoryConfiguration.class})
public class PersonRepositoryTest {

    private PersonRepository personRepository;

    private Logger log = LogManager.getLogger(PersonRepositoryTest.class);

    @Autowired
    public void setPersonRepository(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Test
    public void testSavePerson(){
        //setup personk
        Person person = new Person();
        person.setFirstName("Jussi");
        person.setLastName("Rantanen");
        person.setSotu("1111");

        //save person, verify has ID value after save
        assertNull(person.getId()); //null before save
        personRepository.save(person);
        assertNotNull(person.getId()); //not null after save
        //fetch from DB
        Person fetchedPerson = personRepository.findById(person.getId()).orElse(null);

        //check fetching by sotu
        Person sotuPerson = personRepository.findBySotu("1111");
        assertEquals("1111", sotuPerson.getSotu());

        //should not be null
        assertNotNull(fetchedPerson);

        //should equal
        assertEquals(person.getId(), fetchedPerson.getId());
        assertEquals(person.getFirstName(), fetchedPerson.getFirstName());

        //update description and save
        fetchedPerson.setFirstName("Lauri");
        personRepository.save(fetchedPerson);

        //get from DB, should be updated
        Person fetchedUpdatedPerson = personRepository.findById(fetchedPerson.getId()).orElse(null);
        assertEquals(fetchedPerson.getFirstName(), fetchedUpdatedPerson.getFirstName());


        //verify count of persons in DB
        long personCount = personRepository.count();
        assertEquals(personCount, 1);

        //get all persons, list should only have one
        Iterable<Person> persons = personRepository.findAll();

        int count = 0;

        for(Person p : persons){
            count++;
        }

        assertEquals(count, 1);

    }
}
