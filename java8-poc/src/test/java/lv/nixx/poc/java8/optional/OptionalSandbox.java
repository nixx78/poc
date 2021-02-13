package lv.nixx.poc.java8.optional;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.junit.Test;

public class OptionalSandbox {

    @Test
    public void ifPresentSample() {
        final Optional<String> optX = Optional.of("x");
        final Optional<Object> empty = Optional.empty();

        optX.ifPresent(t -> System.out.println("Presented:" + t));
        empty.ifPresent(t -> System.out.println("Not Presented:" + t));

        optX.ifPresentOrElse(t -> System.out.println("Presented:" + t), () -> System.out.println("Not Presented:"));

        empty.ifPresentOrElse(t -> System.out.println("Presented:" + t), () -> System.out.println("Not Presented:"));
    }

    @Test
    public void or() {
        // Return another Optional when no value is present

        assertEquals("x", Optional.of("x")
                .or(() -> Optional.of("Not started yet."))
                .get());

        assertEquals("Not started yet.", Optional.empty()
                .or(() -> Optional.of("Not started yet."))
                .get());

    }

    @Test
    public void orElseSample() {
        // Don’t use orElse() for returning a computed value.
        assertEquals("else.value", Optional.empty().orElse("else.value"));
        assertEquals("present.value", Optional.of("present.value").orElse("else.value"));

        assertEquals("else.value", Optional.empty().orElseGet(() -> "else.value"));
    }


    @Test
    public void optionalFilter() {

        assertEquals("xyz", Optional.of("xyz")
                .filter(t -> t.equalsIgnoreCase("xyz"))
                .orElse("Not found")
        );

        assertEquals("Not found", Optional.of("xyz")
                .filter(t -> t.equalsIgnoreCase("A"))
                .orElse("Not found")
        );

    }

    @Test
    public void optionalMap() {

        final Optional<Integer> optNotPresent = Optional.of(new Person("name.value", new Address("street.value", null)))
                .map(Person::getAddress)
                .map(Address::getHouseNum);

        assertFalse(optNotPresent.isPresent());

        assertEquals(Integer.valueOf(100), Optional.of(new Person("name.value", new Address("street.value", 100)))
                .map(Person::getAddress)
                .map(Address::getHouseNum)
                .orElse(-1)
        );
;

        Collection<Person> persons = List.of(
                new Person("name.value0", new Address("street.value0", 100)),
                new Person("name.value1", new Address("street.value1", null)),
                new Person("name.value2", new Address("street.value2", 200)),
                new Person("name.value3", new Address("street.value3", 300))
        );

        final List<Integer> collect = persons.stream()
                .map(Person::getAddress)
                .map(Address::getHouseNum)
                .filter(Objects::nonNull)
                .filter(t -> t > 200)
                .collect(Collectors.toList());

        collect.forEach(System.out::println);
    }

    @Test
    public void mapSample() {
        Person person = new Person("name.value0", new Address("street.value0", 100));
        Person personWithNullAddress = new Person("name.value0", null);
        Person personWithNullNum = new Person("name.value0", new Address("street.value0", null));

        assertEquals("100mapped", Optional.of(person)
                .map(Person::getAddress)
                .map(Address::getHouseNum)
                .map(this::mapName)
                .orElse("NotValue"));


        assertEquals("NotValue", Optional.of(personWithNullAddress)
                .map(Person::getAddress)
                .map(Address::getHouseNum)
                .map(this::mapName)
                .orElse("NotValue"));

        assertEquals("NotValue", Optional.of(personWithNullNum)
                .map(Person::getAddress)
                .map(Address::getHouseNum)
                .map(this::mapName)
                .orElse("NotValue"));


    }


    @Test
    public void stringToBigDecimalMap() {

        assertEquals(BigDecimal.ZERO, getSalary(new Person("name1")));

        Person person = new Person("name2");
        person.setSalary("100.22");

        assertEquals(BigDecimal.valueOf(100.22), getSalary(person));
        assertEquals(BigDecimal.ZERO, getSalary(null));
    }


    private String mapName(Integer n) {
        return n + "mapped";
    }

    private BigDecimal getSalary(Person person) {
        return Optional.ofNullable(person)
                .map(Person::getSalary)
                .map(BigDecimal::new)
                .orElse(BigDecimal.ZERO);
    }


    @Getter
    @Setter
    static class Person {
        String name;
        Address address;
        String salary;

        Person(String name) {
            this.name = name;
        }

        Person(String name, Address address) {
            this.name = name;
            this.address = address;
        }
    }

    @AllArgsConstructor
    @Getter
    static class Address {
        String street;
        Integer houseNum;
    }

}
