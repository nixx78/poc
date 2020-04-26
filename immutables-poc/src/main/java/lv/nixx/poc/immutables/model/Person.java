package lv.nixx.poc.immutables.model;

import org.immutables.value.Value;

import java.util.Date;
import java.util.Set;

@Value.Immutable
abstract class Person {
    abstract String name();
    abstract Date dateOfBirth();
    abstract Set<String> roles();
}
