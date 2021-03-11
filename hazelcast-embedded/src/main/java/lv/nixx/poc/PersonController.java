package lv.nixx.poc;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ConcurrentMap;

@RestController
public class PersonController {

    @Autowired
    private HazelcastInstance hazelcastInstance;

    private ConcurrentMap<String, Person> retrieveMap() {
        return hazelcastInstance.getMap("personMap");
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