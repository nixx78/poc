import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

public class Sanbox {

    @Test
    void test() throws ParseException {

        // 1. Создаем Date в UTC
        String utcDateString = "2024-11-21 12:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC")); // Указываем, что строка в UTC
        Date date = sdf.parse(utcDateString); // Date точно в UTC

        System.out.println("Date: " + date); // Выводит системную интерпретацию (неважно)
        //        Этот вывод показывает интерпретацию объекта Date в системной временной зоне. Поскольку ваша система настроена на EET (Eastern European Time, UTC+2),
        //        время отображается как 14:00, хотя сам Date хранит абсолютное время UTC.

        // 2. Переводим Date в Instant (всегда UTC)
        Instant instant = date.toInstant();

        // 3. Создаем ZonedDateTime из Instant в UTC
        ZonedDateTime utcDateTime = instant.atZone(ZoneId.of("UTC"));

        // 4. Конвертируем в временную зону America/New_York
        ZonedDateTime newYorkDateTime = utcDateTime.withZoneSameInstant(ZoneId.of("America/New_York"));

        // 5. Форматируем в строку
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
        String formattedUtcDate = utcDateTime.format(formatter);
        String formattedNewYorkDate = newYorkDateTime.format(formatter);

        System.out.println("Original Date (UTC): " + formattedUtcDate);
        System.out.println("Formatted Date (America/New_York): " + formattedNewYorkDate);
    }

}
