package lv.nixx.poc.hazelcast;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.*;

import org.junit.Before;
import org.junit.Test;

import com.hazelcast.config.Config;
import com.hazelcast.config.MultiMapConfig;
import com.hazelcast.core.DistributedObject;
import com.hazelcast.core.EntryView;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

import com.hazelcast.core.MultiMap;
import com.hazelcast.query.EntryObject;
import com.hazelcast.query.Predicate;
import com.hazelcast.query.PredicateBuilder;
import com.hazelcast.query.SqlPredicate;
import com.hazelcast.test.TestHazelcastInstanceFactory;

import lv.nixx.poc.hazelcast.model.Person;

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

		hz = new TestHazelcastInstanceFactory().newHazelcastInstance(config);

		personMap = hz.getMap("persons.map");
	}

	@Test
	public void predicateSandbox() throws ParseException {
		personMap.put(1, new Person(1, "Name1", df.parse("06.12.1978")));
		personMap.put(2, new Person(2, "Name2", df.parse("06.12.1980")));
		personMap.put(3, new Person(3, "Name3", df.parse("06.12.2004")));
		personMap.put(4, new Person(4, "Name4", df.parse("06.12.2019")));
		personMap.put(5, new Person(5, "ABC", df.parse("06.12.2019")));

		EntryObject e = new PredicateBuilder().getEntryObject();

		Predicate<Integer, Person> equal = e.get("name").equal("ABC");

		Collection<Person> values = personMap.values(equal);
		assertEquals(1, values.size());

		System.out.println(values);

		personMap.get(5);
		personMap.get(5);
		personMap.get(5);

		EntryView<Integer, Person> entry = personMap.getEntryView(5);
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
	public void sqlPredicateTest() throws ParseException {
		personMap.put(1, new Person(1, "Name1", df.parse("06.12.1978")));
		personMap.put(2, new Person(2, "Name2", df.parse("06.12.1980")));
		personMap.put(3, new Person(3, "Name3", df.parse("06.12.2004")));

		Person person4 = new Person(4, "Name4", df.parse("06.12.2019"));
		person4.setState(Collections.emptyList());
		personMap.put(4, person4);

		Person person5 = new Person(5, "ABC", df.parse("06.12.2019"));
		person5.setState(Arrays.asList("st1", "st2", "st3"));
		personMap.put(5, person5);

		Person person6 = new Person(6, "ABC", df.parse("06.12.2019"));
		person6.setState(Arrays.asList("st2", "st3"));
		personMap.put(6, person6);

		Person person7 = new Person(7, "ABC", df.parse("06.12.2019"));
		person7.setState(Arrays.asList("st3"));
		personMap.put(7, person7);

		executeFilter("state[any]==null", 2, 1, 4, 3);
		executeFilter("state[any]==st1", 5);
		executeFilter("state[any]=='st1' OR state[any]==null", 1, 2, 3, 4, 5);
		executeFilter("state[any] in (st2,st1)", 6, 5);
		executeFilter("state[any] not in (st2, st5)", 1, 2, 3, 4, 7);
		executeFilter("name like A%", 5, 6, 7);
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
