package com.example.controllers;

import com.example.domain.Person;
import com.example.services.PersonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.beans.propertyeditors.CustomDateEditor;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class PersonController {

    private PersonService personService;

    private Logger log = LogManager.getLogger(PersonService.class);

    @Autowired
    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }

    @RequestMapping(value = "/persons", method = RequestMethod.GET)
    public String list(Model model){
        model.addAttribute("persons", personService.listAllPersons());
        log.info("persons returned");
        return "persons";
    }

    @RequestMapping("person/{id}")
    public String showPerson(@PathVariable Integer id, Model model ){
        model.addAttribute("person", personService.getPersonById(id));
        log.info("personshow returned");
        return "personshow";
    }

    @RequestMapping("person/edit/{id}")
    public String edit(@PathVariable Integer id, Model model){
        model.addAttribute("person", personService.getPersonById(id));
        log.info("personform1 returned");
        return "personform";
    }

    @RequestMapping("person/new")
    public String newProduct(Model model){
        model.addAttribute("person", new Person());
        log.info("personform2 returned");
        return "personform";
    }

    @RequestMapping(value = "person", method = RequestMethod.POST)
    public String savePerson(Person person){
        personService.savePerson(person);
        log.info("redirect returned");
        return "redirect:/person/" + person.getId();
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
}
