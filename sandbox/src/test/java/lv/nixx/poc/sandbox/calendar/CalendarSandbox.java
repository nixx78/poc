package lv.nixx.poc.sandbox.calendar;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static java.lang.System.out;

class CalendarSandbox {

    @Test
    void dateTimePlayground() {
        LocalDateTime initial = LocalDateTime.parse("2023-04-12T12:12:12");

        LocalDateTime from = LocalDateTime.of(initial.getYear(), initial.getMonth(), initial.getDayOfMonth(), 0, 0, 0);
        LocalDateTime to = LocalDateTime.of(initial.getYear(), initial.getMonth(), initial.getDayOfMonth(), 23, 59, 59);

        out.println(from + "  - " + to);

        out.println("toLocalDate():" + initial.toLocalDate());
        out.println("toLocalTime():" + initial.toLocalTime());

        out.println(initial.toLocalDate().atStartOfDay());
        out.println(initial.toLocalDate().atTime(LocalTime.MAX));

        out.println(initial.toLocalDate().atTime(23, 59, 59));

        LocalDate initialDate = LocalDate.parse("2023-04-12");

        LocalDateTime startOfDay = initialDate.atStartOfDay();
        LocalDateTime endOfDay = initialDate.atTime(LocalTime.MAX);
        out.println(startOfDay + "  - " + endOfDay);

        out.println("Start of day +1 hour:" + startOfDay.plusHours(1));
        out.println("Start of day +15 seconds:" + startOfDay.plus(15, ChronoUnit.SECONDS) );
        out.println("Start of day -20 minutes:" + startOfDay.minus(20, ChronoUnit.MINUTES) );
    }


}
