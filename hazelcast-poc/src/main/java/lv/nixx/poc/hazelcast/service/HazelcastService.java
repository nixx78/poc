package lv.nixx.poc.hazelcast.service;

import java.util.Collection;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.Set;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.query.EntryObject;
import com.hazelcast.query.Predicate;
import com.hazelcast.query.PredicateBuilder;

import lombok.Setter;
import lv.nixx.poc.hazelcast.model.*;

@Setter
public class HazelcastService {
	
	private HazelcastInstance hazelcastInstance;
	
	private IMap<PersonKey,Person> getMap() {
		return hazelcastInstance.getMap("personMap");
	}
	
	public Person getPerson(PersonKey key) {
		return getMap().get(key);
	}

	public void addPerson(PersonKey key, Person person) {
		getMap().set(key, person);
	}
	
	public Person updatePerson(PersonKey key, Person person) {
		return getMap().replace(key, person);
	}
	
	public Person deletePerson(PersonKey key) {
		return getMap().remove(key);
	}
	
	public Collection<Person> getPersonsBySelection(long selectionId) {
		
		Predicate<PersonKey, PersonKey> equal = new PredicateBuilder()
				.getEntryObject()
				.key()
				.get("selectionId").equal(selectionId);

			return getMap().entrySet(equal)
				.stream()
				.map(Entry::getValue)
				.collect(Collectors.toList());
	}
	
	public Collection<Person> getPersonsBySelection(Category category) {
		
		Predicate<PersonKey, PersonKey> equal = new PredicateBuilder()
				.getEntryObject()
				.key()
				.get("category").equal(category);

			return getMap().entrySet(equal)
				.stream()
				.map(Entry::getValue)
				.collect(Collectors.toList());
	}

}
