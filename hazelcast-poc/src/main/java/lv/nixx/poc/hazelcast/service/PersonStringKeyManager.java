package lv.nixx.poc.hazelcast.service;

import java.util.Collection;

import com.hazelcast.query.Predicate;
import com.hazelcast.query.PredicateBuilder;

import lv.nixx.poc.hazelcast.model.*;

public class PersonStringKeyManager extends GenericEntityManager<String, Person> {
	
	@Override
	String getMapName() {
		return "person.map";
	}

	public Collection<Person> getPersonsByAttributes(Integer id, String name) {
		
		Predicate<?,?> p = new PredicateBuilder()
				.getEntryObject()
				.get("id").equal(id);

			return collectAndMap(p);
	}
	
	
	

}
