package com.kiroule.ocpupgradejava8.topic8_3;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * @author Igor Baiborodine
 */
public class DateTimeFormattingExample {

    public static void main(String... args) {

        LocalDate nowLocalDate = LocalDate.now();
        System.out.printf("Now date in ISO date format: %s %n",
                nowLocalDate.format(DateTimeFormatter.ISO_DATE));
        System.out.printf("Now date/time in ISO date/time format: %s %n",
                LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));

        String ddMMyyyyPattern = "dd/MM/yyyy";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ddMMyyyyPattern);
        String formattedDate = nowLocalDate.format(formatter);
        System.out.printf("Now date in [%s] format: %s %n", ddMMyyyyPattern, formattedDate);
        System.out.printf("Parsed formatted now date[%s]: %s %n", formattedDate,
                LocalDate.parse(formattedDate, formatter));

        // http://www.localeplanet.com/java/fr-CA/
        String dMMMMyyyyPattern = "d MMMM yyyy";
        DateTimeFormatter canadianFrenchFormatter =
                DateTimeFormatter.ofPattern(dMMMMyyyyPattern, Locale.CANADA_FRENCH);
        System.out.printf("Now date in [%s] format and [%s] locale: %s %n",
                dMMMMyyyyPattern, Locale.CANADA_FRENCH, nowLocalDate.format(canadianFrenchFormatter));
    }
}
