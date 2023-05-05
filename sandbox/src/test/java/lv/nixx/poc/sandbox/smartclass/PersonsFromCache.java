package lv.nixx.poc.sandbox.smartclass;

import lv.nixx.poc.domain.Person;

import java.util.Collection;
import java.util.Collections;

public class PersonsFromCache implements Persons {

    @Override
    public Collection<Person> getAll() {
        return Collections.emptyList();
    }

}
