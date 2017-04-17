package lv.nixx.poc.sandbox.singleton;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class InstanceSingleton {

	final Map<Integer, Person> persInstances = new HashMap<>();

	@Test
	public void instanceTest() {
		Map<String, Person> m = new HashMap<>();

		m.put("key1", getPersonInstance(10, "n1", "s1", "iid1"));
		m.put("key2", getPersonInstance(20, "n2", "s2", "iid2"));
		m.put("key3", getPersonInstance(30, "n3", "s3", "iid3"));
		m.put("key4", getPersonInstance(40, "n1", "s1", "iid4"));
		m.put("key5", getPersonInstance(50, "n5", "s5", "iid5"));

		m.values().forEach(System.out::println);
	}

	private Person getPersonInstance(int id, String name, String surname, String iid) {
		return persInstances.computeIfAbsent(calculateHashCode(id, name, surname), 
				t -> new Person(id, name, surname, iid));
	}

	public static int calculateHashCode(int id, String name, String surname) {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((surname == null) ? 0 : surname.hashCode());

		return result;
	}

}
