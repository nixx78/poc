package lv.nixx.poc.hazelcast;

import com.hazelcast.config.Config;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.query.Predicate;
import com.hazelcast.query.Predicates;
import com.hazelcast.query.SqlPredicate;
import com.hazelcast.test.TestHazelcastInstanceFactory;
import lv.nixx.poc.hazelcast.model.Person;
import org.junit.Before;
import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class PredicateSandbox {

	private HazelcastInstance hz  = new TestHazelcastInstanceFactory().newHazelcastInstance(new Config());

	private final DateFormat df = new SimpleDateFormat("dd.MM.yyyy");

	private IMap<Integer, Person> personMap;

	@Before
	public void init() throws ParseException {
		personMap = hz.getMap("persons.map");
		personMap.clear();
		createTestData();
	}

	@Test
	public void sqlPredicateTest() {
		executeFilter("state[any]==null", 2, 1, 4, 3);
		executeFilter("state[any]==st1", 5);
		executeFilter("state[any]=='st1' OR state[any]==null", 1, 2, 3, 4, 5);
		executeFilter("state[any] in (st2,st1)", 6, 5);
		executeFilter("state[any] not in (st2, st5)", 1, 2, 3, 4, 7);
		executeFilter("name like A%", 5, 6, 7);
		executeFilter("name like %B%", 5, 6);
	}

	@Test
	public void predicateTest() {
		Predicate p = Predicates.or(
				Predicates.equal("name", "ABC"),
				Predicates.equal("name", "Name1")
		);

		Predicate p1 = Predicates.and(p, Predicates.in("state[any]", "st2", "st3"));

		System.out.println("Predicate:" + p1);

		List<Person> result = personMap.entrySet(p1)
				.stream()
				.map(Map.Entry::getValue)
				.collect(Collectors.toList());

		System.out.println(result);

		assertEquals(2, result.size());

	}

	private void createTestData() throws ParseException {
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

		Person person7 = new Person(7, "A_C", df.parse("06.12.2019"));
		person7.setState(Collections.singletonList("st3"));
		personMap.put(7, person7);
	}


	private void executeFilter(String sqlPredicate, Integer... ids) {
		List<Integer> f = personMap.values(new SqlPredicate(sqlPredicate)).stream().map(Person::getId).collect(Collectors.toList());

		System.out.println("Expected: " + Arrays.toString(ids) + " actual: " + f);

		assertThat(f, containsInAnyOrder(ids));
	}

}
