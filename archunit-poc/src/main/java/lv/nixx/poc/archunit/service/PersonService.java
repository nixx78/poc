package lv.nixx.poc.archunit.service;

import lv.nixx.poc.archunit.dao.PersonDao;
import lv.nixx.poc.archunit.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;

@Service
public class PersonService {

    private PersonDao personDao;

    @Autowired
    public void setPersonDao(PersonDao personDao) {
        this.personDao = personDao;
    }

    public Collection<Person> getAllPersons() {
        return personDao.getAllPersons();
    }

}
