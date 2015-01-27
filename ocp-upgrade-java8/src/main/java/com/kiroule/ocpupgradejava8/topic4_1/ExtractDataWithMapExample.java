package com.kiroule.ocpupgradejava8.topic4_1;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toSet;

/**
 * @author Igor Baiborodine
 */
public class ExtractDataWithMapExample {

    public static void main(String... args) {

        List<Person> persons = Arrays.asList(
                new Person("Bender", "Rodriguez", "robot"),
                new Person("Philip", "Fry", "human"),
                new Person("Turanga", "Leela", "mutant"),
                new Person("Zapp", "Brannigan", "human"));

        System.out.println("Distinct person origins:");
        persons.stream()
                .map(Person::getOrigin)
                .collect(toSet())
                .stream()
                .sorted()
                .forEach(s -> System.out.println(s));
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
        return firstName + " " + lastName;
    }
}
