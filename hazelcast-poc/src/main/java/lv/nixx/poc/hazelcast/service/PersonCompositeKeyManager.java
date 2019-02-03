package lv.nixx.poc.hazelcast.service;

import java.util.Collection;

import com.hazelcast.core.ILock;
import com.hazelcast.query.Predicate;
import com.hazelcast.query.PredicateBuilder;

import lv.nixx.poc.hazelcast.model.*;

public class PersonCompositeKeyManager extends GenericEntityManager<PersonKey, Person> {

	public PersonCompositeKeyManager() {
		super("person.compositekey.map");
	}

	public void addBulkForSelection(long selectionId, CategoryPersonTuple... tuples) {
		final ILock dLock = hazelcastInstance.getLock(String.valueOf(selectionId));
		dLock.lock();
		try {
			for (CategoryPersonTuple t : tuples) {
				PersonKey pk = new PersonKey(selectionId, t.getCategory());
				add(pk, t.getPerson());
			}
		} finally {
			dLock.unlock();
		}
	}

	public Collection<Person> getPersonsBySelection(long selectionId) {

		Predicate<?, ?> p = new PredicateBuilder().getEntryObject()
				.key()
				.get("selectionId").equal(selectionId);

		return collectAndMap(p);
	}

	public Collection<Person> getPersonsBySelection(Category category) {

		Predicate<?, ?> p = new PredicateBuilder().getEntryObject()
				.key()
				.get("category").equal(category);

		return collectAndMap(p);
	}

}
