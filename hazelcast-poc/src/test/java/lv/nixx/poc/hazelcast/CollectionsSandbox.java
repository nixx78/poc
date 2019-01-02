package lv.nixx.poc.hazelcast;

import static org.junit.Assert.assertEquals;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
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

public class CollectionsSandbox {
	
	private HazelcastInstance hz;
	private final DateFormat df = new SimpleDateFormat("dd.MM.yyyy");

	private final String personsForCompanyIdMap = "personsByCompanyId";
	private final String mapName = "persons";


	@Before
	public void init() {
		Config config = new Config();
		
		MultiMapConfig mmConfig = new MultiMapConfig();
		mmConfig.setName(personsForCompanyIdMap);
		mmConfig.setBinary(false);					//	Decrease performance, but compare objects using equals/hashCode
		mmConfig.setValueCollectionType( "SET" );	//  No duplicates for one company 
		
		config.addMultiMapConfig(mmConfig);
		
		hz = new TestHazelcastInstanceFactory().newHazelcastInstance(config);
	}
	

	@Test
	public void predicateSandbox() throws ParseException {
		IMap<Integer, Person> personMap = hz.getMap(mapName);
		personMap.put(1, new Person(1, "Name1", df.parse("06.12.1978") ));
		personMap.put(2, new Person(2, "Name2", df.parse("06.12.1980") ));
		personMap.put(3, new Person(3, "Name3", df.parse("06.12.2004") ));
		personMap.put(4, new Person(4, "Name4", df.parse("06.12.2019") ));
		personMap.put(5, new Person(5, "ABC", df.parse("06.12.2019") ));
		
        EntryObject e = new PredicateBuilder().getEntryObject();

        Predicate<Integer, Person> equal = e.get("name").equal("ABC");
		
		IMap<Integer, Person> map = hz.getMap(mapName);
		Collection<Person> values = map.values(equal);
		assertEquals(1, values.size());
		
		System.out.println(values);
		
		map.get(5);
		map.get(5);
		map.get(5);
		
		EntryView<Integer, Person> entry = map.getEntryView(5);
		System.out.println ( "size in memory  : " + entry.getCost() );
		System.out.println ( "creationTime    : " + entry.getCreationTime() );
		System.out.println ( "expirationTime  : " + entry.getExpirationTime() );
		System.out.println ( "number of hits  : " + entry.getHits() );
		System.out.println ( "lastAccessedTime: " + entry.getLastAccessTime() );
		System.out.println ( "lastUpdateTime  : " + entry.getLastUpdateTime() );
		System.out.println ( "version         : " + entry.getVersion() );
		System.out.println ( "key             : " + entry.getKey() );
		System.out.println ( "value           : " + entry.getValue() );
	}
	
	@Test
	public void collectionContainsSample() throws ParseException {
		IMap<Integer, Person> personMap = hz.getMap(mapName);
		
		personMap.put(1, new Person(1, "Name1", df.parse("06.12.1978") ));
		personMap.put(2, new Person(2, "Name2", df.parse("06.12.1980") ));
		personMap.put(3, new Person(3, "Name3", df.parse("06.12.2004") ));
		
		Person person4 = new Person(4, "Name4", df.parse("06.12.2019") );
		person4.setState(Collections.emptyList());
		personMap.put(4, person4);
		
		Person person5 = new Person(5, "ABC", df.parse("06.12.2019") );
		person5.setState(Arrays.asList("st1", "st2", "st3"));
		personMap.put(5, person5);
		
		List<Person> filteredPersons = personMap.entrySet(new SqlPredicate("state[any]==null"))
				.stream()
				.map(Map.Entry::getValue)
				.collect(Collectors.toList());
		
		assertEquals(4, filteredPersons.size());
		
		filteredPersons = personMap.entrySet(new SqlPredicate("state[any]=='st1'"))
				.stream()
				.map(Map.Entry::getValue)
				.collect(Collectors.toList());
		
		assertEquals(1, filteredPersons.size());
		
		filteredPersons = personMap.entrySet(new SqlPredicate("state[any]=='st1' OR state[any]==null"))
				.stream()
				.map(Map.Entry::getValue)
				.collect(Collectors.toList());
		
		assertEquals(5, filteredPersons.size());


	}
	
	@Test
	public void multiMapSample() throws ParseException {
		
		MultiMap<Integer, Person > map = hz.getMultiMap( personsForCompanyIdMap );
		
		map.put(1, new Person(1, "Name1", df.parse("06.12.1978")));
		map.put(1, new Person(2, "Name1", df.parse("06.12.1978")));
		map.put(1, new Person(2, "Name1", df.parse("06.12.1978")));
		
		map.put(3, new Person(3, "Name1", df.parse("06.12.1978")));
		
 		Collection<Person> one = map.get(1);
		System.out.println( one );
		assertEquals(2, one.size());
		
		System.out.println("---------------------");
 		
		map.remove(1,  new Person().setId(2));
		one = map.get(1);
		System.out.println(map.get(1) );
		assertEquals("Should be removed", 1, one.size());
	}
	
	
	@Test
	public void sqlPredicateTest() throws ParseException {
		
		// https://docs.hazelcast.org/docs/latest-development/manual/html/Distributed_Query/How_Distributed_Query_Works/Querying_with_SQL.html
		
		final String mapName = "persons";
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		
		IMap<Integer, Person> personMap = hz.getMap(mapName);
		personMap.put(1, new Person(1, "Name1", df.parse("06.12.1978") ));
		personMap.put(2, new Person(2, "Name2", df.parse("06.12.1980") ));
		personMap.put(3, new Person(3, "Name3", df.parse("06.12.2004") ));
		personMap.put(4, new Person(4, "Name4", df.parse("06.12.2019") ));
		personMap.put(5, new Person(5, "ABC", df.parse("06.12.2019") ));
		
		
		IMap<Integer, Person> map = hz.getMap(mapName);
		Collection<Person> persons = map.values(new SqlPredicate("name like N%"));
		
		assertEquals(4, persons.size());
	}
	
	
	@Test
	public void distributedObjectsListTest() throws ParseException {

		hz.getMap("mapName1");
		hz.getMap("mapName2");
		hz.getMap("mapName3");
		
	    Collection<DistributedObject> distributedObjects = hz.getDistributedObjects();
	    
	    for (DistributedObject distributedObject : distributedObjects) {
	      System.out.println(distributedObject.getName() +"#" + distributedObject.getServiceName());
	    }
		
	}
	
	
	

}
