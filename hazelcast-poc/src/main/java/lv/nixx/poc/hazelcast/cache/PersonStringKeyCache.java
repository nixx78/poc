package lv.nixx.poc.hazelcast.cache;

import com.hazelcast.query.Predicate;
import com.hazelcast.query.PredicateBuilder;
import lv.nixx.poc.hazelcast.model.Person;

import java.util.Collection;

public class PersonStringKeyCache extends AbstractEntityCache<String, Person> implements PersonStringKeyCacheOperations {

    public PersonStringKeyCache() {
        super("person.map");
    }

    public Collection<Person> getPersonsByAttributes(Integer id, String name) {

        Predicate<?, ?> p = new PredicateBuilder()
                .getEntryObject()
                .get("id").equal(id);

        return getValues(p);
    }

}
