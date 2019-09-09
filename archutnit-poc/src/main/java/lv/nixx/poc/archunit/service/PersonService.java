package lv.nixx.poc.service;

import lv.nixx.poc.dao.PersonDao;
import lv.nixx.poc.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
