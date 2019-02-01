package lv.nixx.poc.hazelcast;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.test.TestHazelcastInstanceFactory;

import lv.nixx.poc.hazelcast.model.Category;
import lv.nixx.poc.hazelcast.model.Person;
import lv.nixx.poc.hazelcast.model.PersonKey;
import lv.nixx.poc.hazelcast.service.HazelcastService;

public class HazelcastServiceTest {
	
	private HazelcastService service = new HazelcastService();
	private final Date date = new Date();

	@Before
	public void init() {
		HazelcastInstance inst = new TestHazelcastInstanceFactory().newHazelcastInstance();
		service.setHazelcastInstance(inst);
	}
	
	@Test
	public void crudTest() {
		service.addPerson(new PersonKey(10L, new Category(2L, false, "RED")), new Person(102, "Name2", date));
		service.addPerson(new PersonKey(10L, new Category(3L, false, "RED")), new Person(103, "Name3", date));
		
		Person p1 = new Person(101, "Name1", date);
		PersonKey key1 = new PersonKey(10L, new Category(1L, false, "RED"));
		
		service.addPerson(key1, p1);
		
		assertEquals(3, service.getPersonsBySelection(10L).size());
		
		p1.setName("Name.Changed");
		
		service.updatePerson(key1, p1);
		
		assertEquals("Name.Changed", service.getPerson(key1).getName());
		
		service.deletePerson(key1);
		
		assertNull(service.getPerson(key1));
		assertEquals(2, service.getPersonsBySelection(10L).size());
	}

	@Test
	public void getAllBySelectionIdTest() {
		
		
		service.addPerson(new PersonKey(10L, new Category(1L, false, "RED")), new Person(101, "Name1", date));
		service.addPerson(new PersonKey(10L, new Category(2L, false, "WHITE")), new Person(102, "Name2", date));
		service.addPerson(new PersonKey(10L, new Category(3L, false, "RED")), new Person(103, "Name3", date));
		
		service.addPerson(new PersonKey(12L, new Category(4L, false, "BLUE")), new Person(104, "Name4", date));
		service.addPerson(new PersonKey(12L, new Category(1L, false, "RED")), new Person(105, "Name5", date));
		
		service.addPerson(new PersonKey(1L,  new Category(5L, false, "RED")), new Person(106, "Name5", date));

		assertThat(getPersonsBySelection(10L), containsInAnyOrder(101, 102, 103));
		assertThat(getPersonsBySelection(12L), containsInAnyOrder(104, 105));
		assertTrue(getPersonsBySelection(777L).isEmpty());
		
		assertThat(getPersonsByCategory(new Category(1L, false, "RED")), containsInAnyOrder(101, 105));
	
	}
	
	private Collection<Integer> getPersonsBySelection(long selectionId) {
		return service.getPersonsBySelection(selectionId)
				.stream()
				.map(Person::getId)
				.collect(Collectors.toList());
	}
	
	private Collection<Integer> getPersonsByCategory(Category category) {
		return service.getPersonsBySelection(category)
				.stream()
				.map(Person::getId)
				.collect(Collectors.toList());
	}
	
}
