package lv.nixx.poc.inheritance;

public interface MyInterfaceFirst {

    String myVariable = "MyVariable";

    default void myDefMethod(String process) {
        System.out.println("Process - default method:" + process);
        processInside(process);
    }

    private void processInside(String process) {
        System.out.println("Process - private method:" + process);
    }

    static String myStaticMethod() {
        return "MyStaticMethod";
    }

    void methodOne();

}
