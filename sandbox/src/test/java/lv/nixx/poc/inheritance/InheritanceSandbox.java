package lv.nixx.poc.inheritance;

import org.junit.jupiter.api.Test;

public class InheritanceSandbox {
    @Test
    void test() {
        MyClass myClass = new MyClass();

        myClass.myDefMethod("process");

        System.out.println(MyInterfaceFirst.myStaticMethod());
        System.out.println(MyInterfaceFirst.myVariable);

        MyClassInheritance myClassInheritance = new MyClassInheritance();
        myClassInheritance.methodOne();
    }

    static class MyClass implements MyInterfaceFirst {
        @Override
        public void methodOne() {
            System.out.println("MyClass: Method one");
        }
    }

    static class MyClassInheritance implements MyInterfaceFirst, MyInterfaceSecond {

        @Override
        public void methodOne() {
            System.out.println("MyClassInheritance: Method one");
        }

    }

}
