package lv.nixx.poc.sandbox.calendar;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

import static java.lang.System.out;
import static java.math.BigDecimal.valueOf;
import static java.math.RoundingMode.HALF_UP;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
        out.println("Start of day +15 seconds:" + startOfDay.plus(15, ChronoUnit.SECONDS));
        out.println("Start of day -20 minutes:" + startOfDay.minus(20, ChronoUnit.MINUTES));
    }

    @Test
    void differenceBetweenTwoDatesCalculation() {
        assertEquals(5000L, ChronoUnit.MILLIS.between(
                LocalDateTime.parse("2023-04-12T12:12:12"),
                LocalDateTime.parse("2023-04-12T12:12:17"))
        );

        assertEquals(6, ChronoUnit.DAYS.between(
                LocalDateTime.parse("2023-04-14T12:12:12"),
                LocalDateTime.parse("2023-04-20T15:12:17"))
        );

        assertEquals(2, ChronoUnit.MONTHS.between(
                LocalDateTime.parse("2023-04-14T12:12:12"),
                LocalDateTime.parse("2023-06-20T15:12:17"))
        );

        assertEquals(1, ChronoUnit.YEARS.between(
                LocalDateTime.parse("2023-04-14T12:12:12"),
                LocalDateTime.parse("2024-05-20T15:12:17"))
        );

    }

    @Test
    void localDatePeriodSandbox() {
        LocalDate d1 = LocalDate.parse("2023-05-21");
        LocalDate d2 = LocalDate.parse("2024-07-20");

        Period p = Period.between(d1, d2);

        out.println("Period:" + p);
        int years = p.getYears();
        int days = p.getDays();
        int months = p.getMonths();

        out.println("Years: " + years);
        out.println("Months: " + months);
        out.println("Days: " + days);

        BigDecimal d = valueOf(years).add(valueOf(months).divide(valueOf(12), 3, HALF_UP)).add(valueOf(days).divide(valueOf(365), 3, HALF_UP));

        out.println("Period in decimals:" + d);
    }

    @Test
    void durationSandbox() {

        LocalDateTime startLocalDateTime = LocalDateTime.parse("2023-04-12T12:12:12");
        LocalDateTime endLocalDateTime = LocalDateTime.parse("2025-06-12T14:10:01");

        long millis = Duration.between(startLocalDateTime, endLocalDateTime).toMillis();

        out.println("Days:" + TimeUnit.MILLISECONDS.toDays(millis));
        out.println("Hours:" + TimeUnit.MILLISECONDS.toHours(millis));
        out.println("Minutes:" + TimeUnit.MILLISECONDS.toMinutes(millis));
        out.println("Seconds:" + TimeUnit.MILLISECONDS.toSeconds(millis));
    }

}
