package lv.nixx.poc.hazelcast.cache;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.test.TestHazelcastInstanceFactory;

import lv.nixx.poc.hazelcast.model.Person;

public class PersonStringKeyManagerTest {
	
	private PersonStringKeyCache service;
	private final Date date = new Date();

	@Before
	public void init() {
		HazelcastInstance inst = new TestHazelcastInstanceFactory().newHazelcastInstance();
		service = new PersonStringKeyCache(inst);
	}
	
	@Test
	public void crudTest() {
		String id1 = "id1";
		String id2 = "id2";
		String id100 = "id100";

		Person p102 = new Person(102, "Name2", date);
		Person p103 = new Person(103, "Name3", date);

		service.crud.add(id1, p102);
		service.crud.add(id2, p103);
		
		Person p1 = new Person(101, "Name1", date);
		
		service.crud.add(id100, p1);
		
	
		p1.setName("Name.Changed");
		
		service.crud.update(id100, p1);
		
		assertEquals("Name.Changed", service.crud.get(id100).getName());
		
		service.crud.remove(id100);
		
		assertNull(service.crud.get(id100));
		
		assertNotNull(service.crud.get(id1));
		assertNotNull(service.crud.get(id2));

	}

	@Test
	public void getAllBySelectionIdTest() {
		
		
		service.crud.add("id1", new Person(101, "Name1", date));
		service.crud.add("id2", new Person(102, "Name2", date));
		service.crud.add("id3", new Person(103, "Name3", date));
		
		service.crud.add("id4", new Person(104, "Name4", date));
		service.crud.add("id5", new Person(106, "Name5", date));
		
		service.crud.add("id6", new Person(106, "Name5", date));

		assertThat(getPersonsByAttributes(106, "Name4"), containsInAnyOrder(106, 106));

	}

	private Collection<Integer> getPersonsByAttributes(Integer id, String name) {
		return service.getPersonsByAttributes(id, name)
				.stream()
				.map(Person::getId)
				.collect(Collectors.toList());
	}
	
}
