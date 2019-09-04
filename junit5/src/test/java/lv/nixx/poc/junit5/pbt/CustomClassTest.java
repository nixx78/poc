package lv.nixx.poc.junit5.pbt;

import net.jqwik.api.*;

public class CustomClassTest {

    @Provide
    private Arbitrary<String> createTestValues() {
        return Arbitraries.of("A", "B", "C", "D");
    }

    @Property
    boolean checkProcessString(@ForAll("createTestValues") String input) {
        ClassToTest classToTest = new ClassToTest();
        return classToTest.process(input).equals(input + ".output");
    }

    class ClassToTest {
        String process(String input) {
            System.out.println("Process:" + input);
            if (input.equalsIgnoreCase("D")) {
                throw new IllegalArgumentException("Input value 'D' is not valid");
            }
            return input + ".output";
        }
    }

}
