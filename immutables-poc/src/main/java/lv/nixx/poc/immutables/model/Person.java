package lv.nixx.poc.immutables.model;

import org.immutables.value.Value;

import java.util.Date;
import java.util.Set;

@Value.Immutable
abstract class Person {
    abstract String name();

    @Value.Default
    String secondName() {
        return "SecondName.Default";
    }


    abstract Date dateOfBirth();

    abstract Set<String> roles();
}
