package lv.nixx.poc.hazelcast.cache;

import java.util.Collection;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ILock;
import com.hazelcast.query.Predicate;
import com.hazelcast.query.PredicateBuilder;

import lv.nixx.poc.hazelcast.model.*;

public class PersonCompositeKeyCache {

	private final HazelcastInstance hazelcastInstance;
	public CrudCacheOperations<PersonKey, Person> crud;


	public PersonCompositeKeyCache(HazelcastInstance hazelcastInstance) {
		this.crud = new CrudCacheOperationsImpl<>(hazelcastInstance, "person.composite");
		this.hazelcastInstance = hazelcastInstance;
	}

	public void addBulkForSelection(long selectionId, CategoryPersonTuple... tuples) {
		final ILock dLock = hazelcastInstance.getLock(String.valueOf(selectionId));
		dLock.lock();
		try {
			for (CategoryPersonTuple t : tuples) {
				PersonKey pk = new PersonKey(selectionId, t.getCategory());
				crud.add(pk, t.getPerson());
			}
		} finally {
			dLock.unlock();
		}
	}

	public Collection<Person> getPersonsBySelection(long selectionId) {

		Predicate<?, ?> p = new PredicateBuilder().getEntryObject()
				.key()
				.get("selectionId").equal(selectionId);

		return crud.getValues(p);
	}

	public Collection<Person> getPersonsBySelection(Category category) {

		Predicate<?, ?> p = new PredicateBuilder().getEntryObject()
				.key()
				.get("category").equal(category);

		return crud.getValues(p);
	}

}
