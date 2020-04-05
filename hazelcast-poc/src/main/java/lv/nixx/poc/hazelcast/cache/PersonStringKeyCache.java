package lv.nixx.poc.hazelcast.cache;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.query.Predicate;
import com.hazelcast.query.PredicateBuilder;
import lv.nixx.poc.hazelcast.model.Person;

import java.util.Collection;

public class PersonStringKeyCache {

    public CrudCacheOperations<String, Person> crud;

    public PersonStringKeyCache(HazelcastInstance hazelcastInstance) {
        this.crud = new CrudCacheOperationsImpl<>(hazelcastInstance, "person.map");
    }

    public Collection<Person> getPersonsByAttributes(Integer id, String name) {

        Predicate<?, ?> p = new PredicateBuilder()
                .getEntryObject()
                .get("id").equal(id);

        return crud.getValues(p);
    }

    static class Stub extends PersonStringKeyCache {
        public Stub() {
            super(null);
            crud = new CrudCacheOperationsStub<>();
        }
    }

}
