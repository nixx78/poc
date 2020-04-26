package lv.nixx.poc.immutables.model;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class PersonTest {

    //TODO https://immutables.github.io/immutable.html

    @Test
    public void test() {
        final Person p = ImmutablePerson.builder()
                .name("Name1")
                .dateOfBirth(new Date())
                .addRoles("ROLE1", "ROLE2")
                .build();

        System.out.println(p.toString());

        assertEquals("Name1", p.name());
    }

}
