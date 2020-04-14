package lv.nixx.poc.hazelcast;

import com.hazelcast.config.Config;
import com.hazelcast.config.MultiMapConfig;
import com.hazelcast.core.*;
import com.hazelcast.query.SqlPredicate;
import lv.nixx.poc.hazelcast.model.Person;
import org.junit.Before;
import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

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
	public void predicateSandbox() throws ParseException {
		int entryId = 3;

		personMap.put(1, new Person(1, "Name1", df.parse("06.12.1978")));
		personMap.put(2, new Person(2, "Name2", df.parse("06.12.1980")));
		personMap.put(entryId, new Person(entryId, "Name3", df.parse("06.12.2004")));

		personMap.get(entryId);
		personMap.get(entryId);
		personMap.get(entryId);

		EntryView<Integer, Person> entry = personMap.getEntryView(entryId);
		System.out.println("size in memory  : " + entry.getCost());
		System.out.println("creationTime    : " + entry.getCreationTime());
		System.out.println("expirationTime  : " + entry.getExpirationTime());
		System.out.println("number of hits  : " + entry.getHits());
		System.out.println("lastAccessedTime: " + entry.getLastAccessTime());
		System.out.println("lastUpdateTime  : " + entry.getLastUpdateTime());
		System.out.println("version         : " + entry.getVersion());
		System.out.println("key             : " + entry.getKey());
		System.out.println("value           : " + entry.getValue());
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

	private void executeFilter(String sqlPredicate, Integer... ids) {
		List<Integer> f = personMap.values(new SqlPredicate(sqlPredicate)).stream().map(Person::getId).collect(Collectors.toList());

		System.out.println("Expected: " + Arrays.toString(ids) + " actual: " + f);

		assertThat(f, containsInAnyOrder(ids));
	}

}
