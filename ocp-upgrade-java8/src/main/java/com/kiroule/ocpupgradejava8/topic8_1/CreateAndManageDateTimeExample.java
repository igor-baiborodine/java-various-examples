package com.kiroule.ocpupgradejava8.topic8_1;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.temporal.ChronoField;

/**
 * @author Igor Baiborodine
 */
public class CreateAndManageDateTimeExample {

    public static void main(String... args) {

        LocalDate specificDate = LocalDate.of(2016, 2, 15);
        System.out.printf("Specific date[2016-02-15]: year[%d], month[%s], day[%s], length of month[%d], leap year[%b]%n",
                specificDate.getYear(),
                specificDate.getMonth(),
                specificDate.getDayOfWeek(),
                specificDate.lengthOfMonth(),
                specificDate.isLeapYear());

        LocalDate todayDate = LocalDate.now();
        System.out.printf("Today date: year[%d], month[%d], day[%d]%n",
                todayDate.get(ChronoField.YEAR),
                todayDate.get(ChronoField.MONTH_OF_YEAR),
                todayDate.get(ChronoField.DAY_OF_MONTH));

        LocalTime specificTime = LocalTime.of(21, 16, 59);
        System.out.printf("Specific time[21:16:59]: hour[%d], minute[%d], second[%d]%n",
                specificTime.getHour(),
                specificTime.getMinute(),
                specificTime.getSecond());

        LocalTime nowTime = LocalTime.now();
        System.out.printf("Now time: hour[%d], minute[%d], second[%d]%n",
                nowTime.get(ChronoField.HOUR_OF_DAY),
                nowTime.get(ChronoField.MINUTE_OF_HOUR),
                nowTime.get(ChronoField.SECOND_OF_MINUTE));

        System.out.printf("Parsing specific date[2015-02-24]: %s %n", LocalDate.parse("2015-02-24"));
        System.out.printf("Parsing specific time[23:54:08]: %s %n", LocalTime.parse("23:54:08"));

        System.out.printf("Specific date/time 1: %s %n", LocalDateTime.of(1940, Month.JANUARY, 20, 0, 0, 1));
        System.out.printf("Specific date/time 2: %s %n", LocalDateTime.of(specificDate, specificTime));
        System.out.printf("Specific date/time 3: %s %n", specificDate.atTime(specificTime));
        System.out.printf("Specific date/time 4: %s %n", specificTime.atDate(specificDate));

    }
}
