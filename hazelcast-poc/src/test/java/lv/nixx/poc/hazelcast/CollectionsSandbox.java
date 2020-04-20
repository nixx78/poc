package lv.nixx.poc.hazelcast;

import com.hazelcast.config.Config;
import com.hazelcast.config.MultiMapConfig;
import com.hazelcast.core.DistributedObject;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.core.MultiMap;
import lv.nixx.poc.hazelcast.model.Person;
import org.junit.Before;
import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

public class CollectionsSandbox {

	private HazelcastInstance hz;
	private final DateFormat df = new SimpleDateFormat("dd.MM.yyyy");

	private final String personsForCompanyIdMap = "personsByCompanyId";
	private IMap<Integer, Person> personMap;

	@Before
	public void init() {
		Config config = new Config();

		MultiMapConfig mmConfig = new MultiMapConfig();
		mmConfig.setName(personsForCompanyIdMap);
		mmConfig.setBinary(false); // Decrease performance, but compare objects using equals/hashCode
		mmConfig.setValueCollectionType("SET"); // No duplicates for one company

		config.addMultiMapConfig(mmConfig);

		hz = HazelcastTestInstance.get(config);

		personMap = hz.getMap("persons.map");
		personMap.clear();
	}

	@Test
	public void multiMapSample() throws ParseException {

		MultiMap<Integer, Person> multiMap = hz.getMultiMap(personsForCompanyIdMap);

		multiMap.put(1, new Person(1, "Name1", df.parse("06.12.1978")));
		multiMap.put(1, new Person(2, "Name1", df.parse("06.12.1978")));
		multiMap.put(1, new Person(2, "Name1", df.parse("06.12.1978")));

		multiMap.put(3, new Person(3, "Name1", df.parse("06.12.1978")));

		Collection<Person> one = multiMap.get(1);
		System.out.println(one);
		assertEquals(2, one.size());

		System.out.println("---------------------");

		multiMap.remove(1, new Person().setId(2));
		one = multiMap.get(1);
		System.out.println(multiMap.get(1));
		assertEquals("Should be removed", 1, one.size());
	}

	@Test
	public void distributedObjectsListTest() throws ParseException {

		hz.getMap("mapName1");
		hz.getMap("mapName2");
		hz.getMap("mapName3");

		Collection<DistributedObject> distributedObjects = hz.getDistributedObjects();

		for (DistributedObject distributedObject : distributedObjects) {
			System.out.println(distributedObject.getName() + "#" + distributedObject.getServiceName());
		}

	}

}
