package lv.nixx.poc.features.java12;

import org.junit.Test;

public class StringNewMethods {

    @Test
    public void sandbox() {

        String transform = "String".transform(t -> t + "Transformed");
        System.out.println(transform);



    }

}
