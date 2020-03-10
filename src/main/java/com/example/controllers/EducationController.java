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

    @RequestMapping(value = "/educations/{pid}", method = RequestMethod.GET)
    public String list(@PathVariable Integer pid, Model model){
        Iterable<Education> i = educationService.listEducationsByPersonId(pid);
        Iterator<Education> it = i.iterator();
        if (it.hasNext() == false)
            log.info("no education found from DB");
        model.addAttribute("educations", i);
        model.addAttribute("pid", pid);
        log.info("show educations with pid");
        return "educations";
    }

    @RequestMapping("education/{id}")
    public String showEducation(@PathVariable Integer id, Model model ){
        model.addAttribute("education", educationService.getEducationById(id));
        log.info("educationshow returned with education feched from DB by id");
        return "educationshow";
    }

    @RequestMapping("education/edit")
    public String showEducation(Education education, Model model ){
        model.addAttribute("education", education);
        log.info("redirect to education edit with pid: " +education.getPid());
        return "redirect:/education/edit/" +education.getPid();
    }

    @RequestMapping("education/edit/{id}")
    public String editEducation(@PathVariable Integer id, Model model){
        Education education = educationService.getEducationById(id);
        log.info("fetch existing education using id from form: " +id);
        Person person = education.getPerson();
        if (person == null) {
            log.info("null person found within Education");
        } else {
            log.info("person found within Education. pid: " +person.getId());
            education.setPid(person.getId());
        }
        model.addAttribute("education", education);
        log.info("educationformedit returned for editing existing education.");
        return "educationformedit";
    }

    @RequestMapping("education/new/{pid}")
    public String newEducation(@PathVariable Integer pid, Model model){

        log.info("create new education. pid:"+pid);
        Education education = new Education();
        education.setPid(pid);
        model.addAttribute("education", education);
        log.info("educationform returned for creating new education.");
        return "educationform";
    }

    @RequestMapping(value = "education", method = RequestMethod.POST)
    public String saveEducation(Education education){

        log.info("POST new Education. pid: " +education.getPid());
        Person person = personService.getPersonById(education.getPid());
        education.setPerson(person);
        person.getEducations().add(education);
        educationService.saveEducation(education);
        log.info("redirect returned for saving education");
        return  "redirect:/educations/" + person.getId()  ;
    }


    @RequestMapping(value = "educationedit", method = RequestMethod.POST)
    public String saveEducationEdit(Education education){

        log.info("POST edited Education. id: " +education.getId() + " pid: " +education.getPid() );
        Person person = personService.getPersonById(education.getPid());
        Education fetchedEducation = educationService.getEducationById(education.getId());
        fetchedEducation.setDescription(education.getDescription());
        fetchedEducation.setDate(education.getDate());
        educationService.saveEducation(fetchedEducation);
        log.info("redirect returned for saving modified education");
        return "redirect:/educations";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

}
