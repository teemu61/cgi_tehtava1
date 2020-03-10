package com.example.bootstrap;

import com.example.domain.Person;
import com.example.domain.Address;

import com.example.domain.Education;
import com.example.repositories.EducationRepository;
import com.example.repositories.PersonRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;


import java.text.ParseException;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.text.SimpleDateFormat;

@Component
public class ProductLoader implements ApplicationListener<ContextRefreshedEvent> {

    private PersonRepository personRespository;
    private EducationRepository educationRepository;
    private Logger log = LogManager.getLogger(ProductLoader.class);

    @Autowired
    public void setProductRepository(PersonRepository personRepository, EducationRepository educationRepository) {
        this.personRespository = personRepository;
        this.educationRepository = educationRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        Education edu = new Education();
        edu.setDescription("ylioppilas");

        Person jussi = new Person();
        jussi.setFirstName("Jussi");
        jussi.setLastName("Rantanen");
        Address addressJussi = new Address();
        addressJussi.setStreetAddress("Ruukinti 5");
        addressJussi.setCity("Espoo");
        addressJussi.setPostalCode("02780");
        jussi.setAddress(addressJussi);
        jussi.setSotu("9865-983456");
        jussi.setLanguage("Suomi");
        jussi.setDateOfBirth(this.getDate("06/01/1998"));

        Person jussiSaved1 = personRespository.saveAndFlush(jussi);

        edu.setPerson(jussiSaved1);
        edu.setPid(jussiSaved1.getId());

        Set<Education> readEcucations = jussiSaved1.getEducations();
        if (readEcucations != null) {
            readEcucations.add(edu);
            jussiSaved1.setEducations(readEcucations);
        } else {
          Set<Education> newEdus = new HashSet<>();
          newEdus.add(edu);
            jussiSaved1.setEducations(newEdus);
        }
        Person jussiSaved2 = personRespository.saveAndFlush(jussiSaved1);

        Iterable edus = jussiSaved2.getEducations();
        Iterator it = edus.iterator();
        while(it.hasNext()){
            Education ed = (Education)it.next();
            log.info("education: " +ed.getDescription() + ", id: " +ed.getId() + ", pid: " +ed.getPid());
        }


        log.info("Saved Jussi - id: " +jussi.getId());

        Person ilmo = new Person();
        ilmo.setFirstName("Ilmo");
        ilmo.setLastName("Rantanen");
        Address addressIlmo = new Address();
        addressIlmo.setStreetAddress("Kalevankatu 1");
        addressIlmo.setCity("Helsinki");
        addressIlmo.setPostalCode("00100");
        ilmo.setAddress(addressIlmo);
        ilmo.setSotu("0345-567891");
        ilmo.setLanguage("Suomi");
        ilmo.setDateOfBirth(this.getDate("03/07/1963"));

        personRespository.save(ilmo);


        Person ilpo = new Person();
        ilpo.setFirstName("Ilpo");
        ilpo.setLastName("Rantanen");
        Address addressIlpo = new Address();
        addressIlpo.setStreetAddress("Osuuskunnantie 5");
        addressIlpo.setCity("Espoo");
        addressIlpo.setPostalCode("02780");
        ilpo.setAddress(addressIlpo);

        ilpo.setSotu("0672-598651");
        ilpo.setLanguage("Suomi");
        ilpo.setDateOfBirth(this.getDate("06/03/2001"));
        Set<Person> ilpoKids = new HashSet<>();
        ilpoKids.add(jussi);
        ilpoKids.add(ilmo);
        ilpo.setParentTo(ilpoKids);

        personRespository.save(ilpo);

        log.info("Saved Ilpo - id: " +ilpo.getId());

        Set<Person> jussiParents = new HashSet<>();
        jussiParents.add(ilpo);
        jussi.setParentFrom(jussiParents);
        personRespository.save(jussi);


        log.info("Ilpo's kids are: ");
        for (Person p : ilpo.getParentTo()) {
            log.info("- " +p.getFirstName());
        }

        log.info("Jussi's parents are:");
        for (Person p : jussi.getParentFrom()) {
            if (p != null)
                log.info("- " +p.getFirstName());
        }

        Person fetchIlpo = personRespository.findByLastNameAndFirstName("Rantanen", "Ilpo");
        log.info("fetchIlpo is: " +fetchIlpo.getFirstName());
    }

    private Date getDate(String date) {
        Date date2 = null;
        try {
            date2 = new SimpleDateFormat("dd/MM/yyyy").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date2;
    }
}
