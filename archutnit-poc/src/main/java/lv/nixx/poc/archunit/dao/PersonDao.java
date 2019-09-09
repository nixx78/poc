package lv.nixx.poc.dao;

import lv.nixx.poc.domain.Person;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Collection;

@Repository
public class PersonDao {

    public Collection<Person> getAllPersons() {
        return Arrays.asList(
                new Person(100, "Name1"),
                new Person(200, "Name2")
        );
    }

}
