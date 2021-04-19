package lv.nixx.poc;

import com.hazelcast.map.MapLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public class PersonLoader implements MapLoader<String, Person> {

    //TODO Create distributed sample for two nodes

    private static final Logger LOG = LoggerFactory.getLogger(PersonLoader.class);

    private static final Map<String, Person> externalResource = Map.of(
            "p1", new Person().setId("p1").setName("Name1").setSurname("Surname1"),
            "p2", new Person().setId("p2").setName("Name2").setSurname("Surname2"),
            "p3", new Person().setId("p3").setName("Name3").setSurname("Surname3"),
            "p4", new Person().setId("p4").setName("Name4").setSurname("Surname4"),
            "p5", new Person().setId("p5").setName("Name5").setSurname("Surname5")
    );

    @Override
    public Person load(String key) {
        LOG.info("Load for key [{}]", key);
        return null;
    }

    @Override
    public Map<String, Person> loadAll(Collection<String> keys) {
        LOG.info("LoadAll for keys: {}", keys);

        return externalResource.entrySet()
                .stream()
                .filter(e -> keys.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    }

    @Override
    public Iterable<String> loadAllKeys() {
        LOG.info("LoadAllKeys");
        return externalResource.keySet();
    }

}
