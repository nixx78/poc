package lv.nixx.poc.hazelcast.service;

import java.util.Collection;

import com.hazelcast.query.Predicate;
import com.hazelcast.query.PredicateBuilder;

import lv.nixx.poc.hazelcast.model.*;

public class PersonCompositeKeyManager extends GenericEntityManager<PersonKey, Person> {
	
	@Override
	String getMapName() {
		return "person.map";
	}

	public Collection<Person> getPersonsBySelection(long selectionId) {
		
		Predicate<?,?> p = new PredicateBuilder()
				.getEntryObject()
				.key()
				.get("selectionId").equal(selectionId);

			return collectAndMap(p);
	}
	
	public Collection<Person> getPersonsBySelection(Category category) {
		
		Predicate<?, ?> p = new PredicateBuilder()
				.getEntryObject()
				.key()
				.get("category").equal(category);

			return collectAndMap(p);
	}
	

}
