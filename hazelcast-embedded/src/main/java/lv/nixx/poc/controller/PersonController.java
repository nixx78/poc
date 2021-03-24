package lv.nixx.poc.controller;

import com.hazelcast.jet.JetInstance;
import lv.nixx.poc.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ConcurrentMap;

@RestController
public class PersonController {

    private final JetInstance jetInstance;

    @Autowired
    public PersonController(JetInstance jetInstance) {
        this.jetInstance = jetInstance;
    }

    private ConcurrentMap<String, Person> retrieveMap() {
        return jetInstance.getMap("personMap");
    }

    @PostMapping("/person")
    public Person add(@RequestBody Person person) {
        retrieveMap().put(person.getId(), person);
        return person;
    }

    @GetMapping("/person/{key}")
    public Person get(@RequestParam(value = "key") String key) {
        return retrieveMap().get(key);
    }
}