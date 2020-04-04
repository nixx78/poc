package lv.nixx.poc.hazelcast.cache;

import com.hazelcast.query.Predicate;
import lv.nixx.poc.hazelcast.model.Person;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public interface PersonStringKeyCacheOperations extends CacheOperations<String, Person> {

    Collection<Person> getPersonsByAttributes(Integer id, String name);

    class Stub implements PersonStringKeyCacheOperations {

        Map<String, Person> map = new HashMap<>();

        @Override
        public Collection<Person> getPersonsByAttributes(Integer id, String name) {
            return null;
        }

        @Override
        public void add(String key, Person value) {
            map.put(key, value);
        }

        @Override
        public Person get(String key) {
            return null;
        }

        @Override
        public Person update(String key, Person person) {
            return null;
        }

        @Override
        public Person remove(String key) {
            return null;
        }

        @Override
        public Collection<Person> getValues(Predicate<?, ?> p) {
            return null;
        }

        @Override
        public int size() {
            return map.size();
        }
    }

}
