package com.example.controllers;

import com.example.domain.Education;
import com.example.domain.Person;
import com.example.services.EducationService;
import com.example.services.PersonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

//@Transactional
@Controller
public class EducationController {

    private EducationService educationService;
    private PersonService personService;
    private Logger log = LogManager.getLogger(EducationService.class);

    @Autowired
    public void setEducationService(EducationService educationService, PersonService personService) {
        this.educationService = educationService;
        this.personService = personService;
    }

    @RequestMapping(value = "/educations", method = RequestMethod.GET)
    public String list(Model model){
        model.addAttribute("educations", educationService.listAllEducations());

        Iterable<Education> i = educationService.listAllEducations();
        Iterator<Education> it = i.iterator();
        while (it.hasNext()){
            Person person = it.next().getPerson();
            if (person == null)
                log.info("null person within Education from DB");
            else
                log.info("person found within Education from DB");
        }

        log.info("educations returned - show educations");
        return "educations";
    }

    @RequestMapping("education/{id}")
    public String showPerson(@PathVariable Integer id, Model model ){
        model.addAttribute("education", educationService.getEducationById(id));
        log.info("educationshow returned - fetch education from DB by id");
        return "educationshow";
    }

    @RequestMapping("education/edit/{id}")
    public String edit(@PathVariable Integer id, Model model){
        Education education = educationService.getEducationById(id);

        Person person = education.getPerson();
        if (person == null) {
            log.info("null person found within Education");
        } else {
            log.info("person found witin Education from DB 2");
            education.setPid(person.getId());
        }
        model.addAttribute("education", education);
        log.info("educationform returned. editing existing education.");
        return "educationform";
    }

    @RequestMapping("education/new")
    public String newEducation(Model model){
        model.addAttribute("education", new Education());
        log.info("educationform returned. creating new education.");
        return "educationform";
    }

    @RequestMapping(value = "education", method = RequestMethod.POST)
    public String saveEducation(Education education){

        Person person = personService.getPersonById(education.getPid());
        education.setPerson(person);
        educationService.saveEducation(education);
        log.info("redirect returned - save education");
        return "redirect:/education/" + education.getId();
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
}
