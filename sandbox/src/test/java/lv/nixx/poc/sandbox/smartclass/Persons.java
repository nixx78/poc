package lv.nixx.poc.sandbox.smartclass;

import lv.nixx.poc.sandbox.singleton.Person;

import java.util.Collection;

public interface Persons {

    Collection<Person> getAll();

    final class Operations {

        private Persons persons;

        public Operations(Persons persons) {
            this.persons = persons;
        }

        public Person getById(String id) {
            persons.getAll();
            return null;
        }

        public Person getByName(String name) {
            return null;
        }
    }
}
