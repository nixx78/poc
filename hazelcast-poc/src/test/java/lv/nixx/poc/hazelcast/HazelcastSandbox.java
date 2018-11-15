package lv.nixx.poc.hazelcast;

import static org.junit.Assert.assertEquals;

import java.text.*;
import java.util.Collection;

import org.junit.Test;

import com.hazelcast.core.*;
import com.hazelcast.map.listener.EntryAddedListener;
import com.hazelcast.query.SqlPredicate;
import com.hazelcast.test.*;

public class HazelcastSandbox {
	
	HazelcastInstance hazelcastInstance = new TestHazelcastInstanceFactory().newHazelcastInstance();

	@Test
	public void testInstance() {
		IMap<String, String> map = hazelcastInstance.getMap("abc");
		map.put("One", "One.value");
		
		String one = String.valueOf(hazelcastInstance.getMap("abc").get("One"));
		assertEquals("One.value", one);
	}
	
	@Test
	public void entryListenerTest() {
		// https://docs.hazelcast.org/docs/latest-development/manual/html/Distributed_Events/Event_Listener_for_Members/Listening_for_Map_Events.html
		
		IMap<String, String> map = hazelcastInstance.getMap("abc");
		map.addEntryListener(new MyEntryListener(), true);
		map.put("One", "One.value");
	}
	
	@Test
	public void sqlPredicateTest() throws ParseException {
		
		// https://docs.hazelcast.org/docs/latest-development/manual/html/Distributed_Query/How_Distributed_Query_Works/Querying_with_SQL.html
		
		final String mapName = "persons";
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		
		IMap<Integer, Person> personMap = hazelcastInstance.getMap(mapName);
		personMap.put(1, new Person(1, "Name1", df.parse("06.12.1978") ));
		personMap.put(2, new Person(2, "Name2", df.parse("06.12.1980") ));
		personMap.put(3, new Person(3, "Name3", df.parse("06.12.2004") ));
		personMap.put(4, new Person(4, "Name4", df.parse("06.12.2019") ));
		personMap.put(5, new Person(5, "ABC", df.parse("06.12.2019") ));
		
		
		IMap<Integer, Person> map = hazelcastInstance.getMap(mapName);
		Collection<Person> persons = map.values(new SqlPredicate("name like N%"));
		
		assertEquals(4, persons.size());
	}
	
	class MyEntryListener implements EntryAddedListener<String, String> {
		@Override
		public void entryAdded(EntryEvent<String, String> entryEvent) {
			System.out.println(entryEvent);
		}
	}

}
