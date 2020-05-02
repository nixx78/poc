package lv.nixx.poc.immutables.model;

import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PersonTest {

    //TODO https://immutables.github.io/immutable.html

    @Test
    public void sandbox() {
        final Person p = ImmutablePerson.builder()
                .name("Name1")
                .dateOfBirth(new Date())
                .addRoles("ROLE1", "ROLE2")
                .addAllRoles(List.of("R1", "R2"))
                .build();

        System.out.println(p.toString());

        assertEquals("Name1", p.name());
        assertEquals("SecondName.Default", p.secondName());

        final Person p1 = ImmutablePerson.builder()
                .name("Name1")
                .secondName("SecondName.Value")
                .dateOfBirth(new Date())
                .addRoles("ROLE1", "ROLE2")
                .build();

        assertEquals("SecondName.Value", p1.secondName());
    }

    @Test
    public void objectCopy() {

        final Person p = ImmutablePerson.builder()
                .name("Name1")
                .dateOfBirth(new Date())
                .addRoles("ROLE1", "ROLE2")
                .build();

        final Person p1 = ImmutablePerson.builder().from(p).name("New Name").build();
        System.out.println(p1.toString());
        assertEquals("New Name", p1.name());
    }

}
