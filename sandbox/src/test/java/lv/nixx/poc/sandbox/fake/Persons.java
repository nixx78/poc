package lv.nixx.poc.sandbox.fake;

import lv.nixx.poc.sandbox.singleton.Person;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public interface Persons {

    Collection<Person> get();

    final class Stub {

        static final class existing implements Persons {

            @Override
            public Collection<Person> get() {
                return Arrays.asList(
                        new Person(1, "name1", "surname1", "iid"),
                        new Person(2, "name2", "surname2", "iid"),
                        new Person(3, "name3", "surname3", "iid")
                );
            }
        }

        static final class empty implements Persons {
            @Override
            public Collection<Person> get() {
                return Collections.emptyList();
            }
        }

    }


}
