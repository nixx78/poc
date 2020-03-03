package lv.nixx.poc.sandbox.fake;

import lv.nixx.poc.sandbox.singleton.Person;
import org.junit.Test;

import java.util.Arrays;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class PersonsTest {

    @Test
    public void testFakeApproach() {
        Service service = new Service();
        service.setPersons(new Persons.Stub.existing());

        service.processAllPersons();

        service.setPersons(new Persons.Stub.empty());
        service.processAllPersons();
    }

    @Test
    public void mockApproach() {

        Persons p = mock(Persons.class);
        doReturn(Arrays.asList(
                new Person(1, "name1", "surname1", "iid"),
                new Person(2, "name2", "surname2", "iid"),
                new Person(3, "name3", "surname3", "iid")
        )).when(p).get();

        Service service = new Service();
        service.setPersons(p);

        service.processAllPersons();
    }


}
