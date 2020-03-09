package com.example.services;

import com.example.domain.Education;
import com.example.domain.Person;
import com.example.repositories.EducationRepository;
import com.example.repositories.PersonRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class EducationServiceImpl implements EducationService {
    private EducationRepository educationRepository;
    private PersonRepository personRepository;

    private Logger log = LogManager.getLogger(EducationServiceImpl.class);

    @Autowired
    public void setEducationRepository(EducationRepository educationRepository, PersonRepository personRepository) {
        this.educationRepository = educationRepository;
        this.personRepository = personRepository;
    }

    @Override
    public Iterable<Education> listAllEducations() {
        log.info("educationservice listAllEducations called");
        return educationRepository.findAll();
    }

    @Override
    public Education getEducationById(Integer id) {
        return educationRepository.findById(id).orElse(null);
    }

    @Override
    public Education saveEducation(Education education) {

        Person person = education.getPerson();

        if (person != null)
            log.info("saveEducation. "+person.getFirstName());
        else
            log.info("null person found when saving Education");
        person.getEducations().add(education);
        log.info("after saving educations");

        return educationRepository.saveAndFlush(education);
    }
}
