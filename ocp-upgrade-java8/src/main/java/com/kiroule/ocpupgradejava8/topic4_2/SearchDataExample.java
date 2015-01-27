package com.kiroule.ocpupgradejava8.topic4_2;

import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;

/**
 * @author Igor Baiborodine
 */
public class SearchDataExample {

    public static void main(String... args) {

        List<Person> persons = Arrays.asList(
                new Person("Bender", "Rodriguez", "robot"),
                new Person("Philip", "Fry", "human"),
                new Person("Turanga", "Leela", "mutant"),
                new Person("Zapp", "Brannigan", "human"));

        System.out.println("\nAll hashcodes:");
        persons.stream()
                .mapToInt(Person::hashCode)
                .sorted()
                .forEach(System.out::println);

        OptionalInt firstNegativeHashcode =
                persons.stream()
                        .mapToInt(Person::hashCode)
                        .sorted()
                        .findFirst();
        System.out.println("\nFirst negative hashcode: " + firstNegativeHashcode);


        OptionalInt anyNegativeHashcode =
                persons.stream()
                        .mapToInt(Person::hashCode)
                        .filter(hc -> hc != firstNegativeHashcode.getAsInt())
                        .findAny();
        System.out.println("Any negative hashcode: " + anyNegativeHashcode);

        System.out.println("\nPersons:");
        persons.forEach(System.out::println);

        boolean containsRobot = persons.stream()
                .anyMatch(p -> p.getOrigin().equals("robot"));
        System.out.println("\nPersons contain a robot: " + containsRobot);

        boolean containsAlien = persons.stream()
                .noneMatch(p -> p.getOrigin().equals("alien"));
        System.out.println("Persons do not contain an alien: " + containsAlien);

        boolean containsOnlyHumans = persons.stream()
                .allMatch(p -> p.getOrigin().equals("human"));
        System.out.println("Persons contain only humans: " + containsOnlyHumans);
    }
}

class Person {
    private String firstName;
    private String lastName;
    private String origin = "human";

    public Person(String firstName, String lastName, String origin) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.origin = origin;
    }

    public String getOrigin() { return origin; }

    @Override
    public String toString() {
        return firstName + " " + lastName + " [" + origin + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (firstName != null ? !firstName.equals(person.firstName) : person.firstName != null) return false;
        if (lastName != null ? !lastName.equals(person.lastName) : person.lastName != null) return false;
        return !(origin != null ? !origin.equals(person.origin) : person.origin != null);

    }

    @Override
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (origin != null ? origin.hashCode() : 0);
        return result;
    }
}
