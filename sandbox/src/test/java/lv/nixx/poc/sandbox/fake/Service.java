package lv.nixx.poc.sandbox.fake;

import lombok.Setter;

@Setter
public class Service {

    private Persons persons;

    public void processAllPersons() {
        persons.get().forEach(System.out::println);
    }
}
