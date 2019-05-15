package lv.nixx.poc.jmx;

public interface JMXController {

    void sendMessage(String message);
    String getMessage();
    void sendNotification();


}
