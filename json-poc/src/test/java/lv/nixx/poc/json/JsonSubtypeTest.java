package lv.nixx.poc.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import lv.nixx.poc.domain.subclass.Child1;
import lv.nixx.poc.domain.subclass.Child2;
import lv.nixx.poc.domain.subclass.Parent;
import org.junit.Test;

public class JsonSubtypeTest {

    @Test
    public void jsonWithChildTest() throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        Child1 child1 = new Child1("parent.value", "child1.value");
        Child2 child2 = new Child2("parent.value", "child2.value");

        String s1 = mapper.writeValueAsString(child1);
        System.out.println(s1);

        String s2 = mapper.writeValueAsString(child2);
        System.out.println(s2);

        Child1 child1FromJson = (Child1) mapper.readValue(s1, Parent.class);
        System.out.println(child1FromJson);

        Child2 child2FromJson = (Child2) mapper.readValue(s2, Parent.class);
        System.out.println(child2FromJson);

    }


}
