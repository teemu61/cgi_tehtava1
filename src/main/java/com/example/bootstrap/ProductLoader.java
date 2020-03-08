package com.example.bootstrap;

import com.example.domain.Person;
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
import java.util.Set;
import java.text.SimpleDateFormat;

@Component
public class ProductLoader implements ApplicationListener<ContextRefreshedEvent> {

    private PersonRepository personRespository;
    private Logger log = LogManager.getLogger(ProductLoader.class);

    @Autowired
    public void setProductRepository(PersonRepository personRepository) {
        this.personRespository = personRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        Person jussi = new Person();
        jussi.setFirstName("Jussi");
        jussi.setLastName("Rantanen");
        jussi.setStreetAddress("Mäkirinne 5");
        jussi.setCity("Vantaa");
        jussi.setSotu("9865-983456");
        jussi.setLanguage("Suomi");
        jussi.setDateOfBirth(this.getDate("06/01/1998"));
        personRespository.save(jussi);

        log.info("Saved Jussi - id: " +jussi.getId());

        Person ilmo = new Person();
        ilmo.setFirstName("Ilmo");
        ilmo.setLastName("Rantanen");
        ilmo.setStreetAddress("Koivukuja 2");
        ilmo.setCity("Espoo");
        ilmo.setSotu("0345-567891");
        ilmo.setLanguage("Suomi");
        ilmo.setDateOfBirth(this.getDate("03/07/1963"));
        personRespository.save(ilmo);

        log.info("Saved Ilmo - id: " +ilmo.getId());

        Person ilpo = new Person();
        ilpo.setFirstName("Ilpo");
        ilpo.setLastName("Rantanen");
        ilpo.setStreetAddress("Lintukuja 7");
        ilpo.setCity("Helsinki");
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
