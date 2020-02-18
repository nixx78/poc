package lv.nixx.poc.sandbox.core;

import org.junit.Test;

public class StaticInnerClassSample {

    private String value = "Value";

    @Test
    public void innerClassSample() {
        StaticInnerClassSample.InnerClass innerClass = new StaticInnerClassSample().new InnerClass("IValue");
        innerClass.process();
    }

    @Test
    public void staticClassSample() {
        StaticInnerClassSample.StaticClass sc1 = new StaticInnerClassSample.StaticClass("StaticClass1");
        StaticClass sc2 = new StaticClass("StaticClass2");

        sc1.process();
        sc2.process();
    }

    class InnerClass {
        private String iv;

        InnerClass(String iv) {
            this.iv = iv;
        }

        void process() {
            System.out.println("Inner value: " + iv + ": class value: " + value);
        }
    }

    static class StaticClass {
        private String iv;

        StaticClass(String iv) {
            this.iv = iv;
        }

        void process() {
            // Possible access only to static variable from Parent class
            System.out.println("Static value: " + iv);
        }
    }

}
