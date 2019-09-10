package lv.nixx.poc.archunit.controller;

import lv.nixx.poc.archunit.dao.PersonDao;
import lv.nixx.poc.archunit.domain.Person;
import lv.nixx.poc.archunit.hazelcast.HazelcastService;
import lv.nixx.poc.archunit.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/persons")
public class PersonController {

    private PersonService personService;
    private PersonDao personDao;
    private HazelcastService hazelcastService;

    @Autowired
    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }

    @Autowired
    public void setPersonDao(PersonDao personDao) {
        this.personDao = personDao;
    }

    @Autowired
    public void setHazelcastService(HazelcastService hazelcastService) {
        this.hazelcastService = hazelcastService;
    }

    @GetMapping(value = "/")
    public Collection<Person> getAllPersons() {
        return personService.getAllPersons();
    }

    @GetMapping(value = "/dao")
    public Collection<Person> getAllPersonsFromDao() {
        return personDao.getAllPersons();
    }

}
