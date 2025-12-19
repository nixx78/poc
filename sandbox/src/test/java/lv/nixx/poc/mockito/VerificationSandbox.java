package lv.nixx.poc.mockito;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class VerificationSandbox {

    Notificator notificator = mock(Notificator.class);
    final Service service;

    public VerificationSandbox() {
        this.service = new Service(notificator);
    }

    @BeforeEach
    void setup() {
        reset(notificator);
    }

    @Test
    void expectedNotificationTest() {

        service.process("m1");
        service.process("m2");
        service.process("m3");

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        verify(notificator, times(3)).notify(captor.capture());

        assertThat(captor.getAllValues()).containsExactly("s_m1", "s_m2", "s_m3");
    }

    @Test
    void expectedOneByOne() {

        service.process("m1");
        assertNotificationExpected("s_m1");

        service.process("m2");
        assertNotificationExpected("s_m2");

        service.process("m3");
        assertNotificationExpected("s_m3");

    }

    void assertNotificationExpected(String expectedNotification) {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        verify(notificator, atLeast(1)).notify(captor.capture());
        List<String> allValues = captor.getAllValues();
        String lastMessage = allValues.get(allValues.size() - 1);

        assertEquals(expectedNotification, lastMessage);
    }

    static class Notificator {

        void notify(String param) {
            System.out.println("param:" + param);
        }
    }

    static class Service {

        private final Notificator notificator;

        Service(Notificator notificator) {
            this.notificator = notificator;
        }

        void process(String message) {
            System.out.println("Service:" + message);
            notificator.notify("s_" + message);
        }
    }

}
