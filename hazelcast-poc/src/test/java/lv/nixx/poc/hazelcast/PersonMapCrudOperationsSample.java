package lv.nixx.poc.hazelcast;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.map.EntryBackupProcessor;
import com.hazelcast.map.EntryProcessor;
import com.hazelcast.test.TestHazelcastInstanceFactory;
import lv.nixx.poc.hazelcast.model.Person;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class PersonMapCrudOperationsSample {

    private HazelcastInstance hazelcastInstance = new TestHazelcastInstanceFactory().newHazelcastInstance();
    private Service service = new Service();

    @Before
    public void init() {
        service.clearAll();
    }

    @Test
    public void crudOperations() {

        Long groupId = 100L;

        service.addPerson(groupId, new Person(10, "Name.v0", new Date()));
        service.addPerson(groupId, new Person(20, "Name.v1", new Date()));

        System.out.println("After add");
        System.out.println(service.map.entrySet());

        assertEquals(1, service.map.size());

        service.updatePerson(groupId, new Person(10, "Name.v11", new Date()));

        System.out.println("After update");
        System.out.println(service.map.entrySet());

        service.deletePerson(groupId, 20);

        System.out.println("After delete");
        System.out.println(service.map.entrySet());
        assertEquals(1, service.map.size());
    }

    class Service {
        IMap<Long, Set<Person>> map = hazelcastInstance.getMap("person.test");

        Collection<Person> addPerson(Long group, Person person) {
            return (Collection<Person>) map.executeOnKey(group, new PersonUpdater(person));
        }

        Collection<Person> updatePerson(Long group, Person person) {
            return (Collection<Person>) map.executeOnKey(group, new PersonUpdater(person));
        }

        Collection<Person> deletePerson(Long group, int personId) {
            return (Collection<Person>) map.executeOnKey(group, new PersonRemover(personId));
        }

        void clearAll() {
            map.clear();
        }

    }

    class PersonUpdater implements EntryProcessor<String, Set<Person>> {

        private Person person;

        PersonUpdater(Person person) {
            this.person = person;
        }

        @Override
        public Collection<Person> process(Map.Entry<String, Set<Person>> entry) {
            Set<Person> personSet = entry.getValue();
            if (personSet == null) {
                personSet = new HashSet<>();
                personSet.add(person);
            } else {
                personSet.remove(person);
                personSet.add(person);
            }
            entry.setValue(personSet);
            return personSet;
        }

        @Override
        public EntryBackupProcessor<String, Set<Person>> getBackupProcessor() {
            return null;
        }

    }

    class PersonRemover implements EntryProcessor<String, Set<Person>> {

        private int personId;

        PersonRemover(int personId) {
            this.personId = personId;
        }

        @Override
        public Collection<Person> process(Map.Entry<String, Set<Person>> entry) {
            Set<Person> personSet = entry.getValue();
            if (personSet!= null) {
                Person o = new Person();
                o.setId(personId);
                personSet.remove(o);
            }
            entry.setValue(personSet);
            return personSet;
        }

        @Override
        public EntryBackupProcessor<String, Set<Person>> getBackupProcessor() {
            return null;
        }

    }

}
