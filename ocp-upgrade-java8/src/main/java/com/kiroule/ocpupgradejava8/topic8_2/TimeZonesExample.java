package com.kiroule.ocpupgradejava8.topic8_2;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * @author Igor Baiborodine
 */
public class TimeZonesExample {

    public static void main(String... args) {

        LocalDateTime nowDateTime = LocalDateTime.now();

        ZoneId montrealZone = ZoneId.of("America/Montreal");
        ZonedDateTime montrealZonedDateTime = ZonedDateTime.of(nowDateTime, montrealZone);
        System.out.printf("Montreal-zoned now date/time: %s %n", montrealZonedDateTime);

        ZoneId moscowZone = ZoneId.of("Europe/Moscow");
        ZonedDateTime moscowZonedDateTime = montrealZonedDateTime.withZoneSameInstant(moscowZone);
        System.out.printf("Moscow-zoned now date/time: %s %n", moscowZonedDateTime);

        // Typically the amount is zero during winter and one hour during summer.
        System.out.printf("Current daylight savings offset for [%s]: %s %n",
                moscowZone, moscowZone.getRules().getDaylightSavings(Instant.now()));

    }
}
