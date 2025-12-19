package lv.nixx.poc.sandbox.core;


import org.junit.jupiter.api.Test;

public class StaticInnerClassSample {

    @Test
    public void innerClassSample() {
        StaticInnerClassSample.InnerClass innerClass = new InnerClass("IValue");
        innerClass.process();
    }

    @Test
    public void staticClassSample() {
        StaticInnerClassSample.StaticClass sc1 = new StaticInnerClassSample.StaticClass("StaticClass1");
        StaticClass sc2 = new StaticClass("StaticClass2");

        sc1.process();
        sc2.process();
    }

    static class InnerClass {
        private final String iv;

        InnerClass(String iv) {
            this.iv = iv;
        }

        void process() {
            String value = "Value";
            System.out.println("Inner value: " + iv + ": class value: " + value);
        }
    }

    static class StaticClass {
        private final String iv;

        StaticClass(String iv) {
            this.iv = iv;
        }

        void process() {
            // Possible access only to static variable from Parent class
            System.out.println("Static value: " + iv);
        }
    }

}
