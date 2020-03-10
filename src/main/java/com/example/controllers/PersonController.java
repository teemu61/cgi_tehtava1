package com.example.controllers;

import com.example.domain.Person;
import com.example.domain.Education;
import com.example.services.PersonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.propertyeditors.CustomDateEditor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

//@Transactional
@Controller
public class PersonController {

    private PersonService personService;
    private Logger log = LogManager.getLogger(PersonService.class);

    @Autowired
    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }

    @RequestMapping(value = "/persons", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("persons", personService.listAllPersons());
        log.info("show all persons");
        return "persons";
    }

    @RequestMapping("person/{id}")
    public String showPerson(@PathVariable Integer id, Model model) {
        model.addAttribute("person", personService.getPersonById(id));

        log.info("show persons with specific id");
        return "personshow";
    }

    @RequestMapping("person/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        Person person = personService.getPersonById(id);
        model.addAttribute("person", person);
        log.info("return personform for editing existing person.");
        return "personform";
    }

    @RequestMapping("person/new")
    public String newPerson(Model model) {
        model.addAttribute("person", new Person());
        log.info("return personform for creating new a person.");
        return "personform";
    }

    @RequestMapping("person/education")
    public String education(Model model) {
        model.addAttribute("education", new Education());
        log.info("educationform returned.");
        return "educationform";
    }

    @RequestMapping("person/query")
    public String query(Model model) {
        model.addAttribute("person", new Person());
        log.info("return personform for quering with sotu");
        return "personquery";
    }

    @RequestMapping(value = "/person/find", method = RequestMethod.GET)
    public String findPerson(@RequestParam("sotu") String sotu, Model model) {

        log.info("findPerson called with sotu: " + sotu);
        Person person = personService.getPersonBySotu(sotu);
        if (person != null) {
            log.info("person found with sotu is: " + person.getFirstName());
        } else {
            log.info("null person found with sotu");
            Person emptyPerson = new Person();
            emptyPerson.setSotu(sotu);
            emptyPerson.setFirstName(" no person found");
            model.addAttribute("person", emptyPerson);
            return "personshow";
        }
        model.addAttribute("person", person);
        return "personshow";
    }

    @RequestMapping(value = "person", method = RequestMethod.POST)
    public String savePerson(Person person) {
        personService.savePerson(person);
        log.info("redirect to show person information");
        return "redirect:/person/" + person.getId();
    }
}
