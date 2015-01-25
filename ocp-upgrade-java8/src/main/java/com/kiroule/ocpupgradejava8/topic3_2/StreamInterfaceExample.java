package com.kiroule.ocpupgradejava8.topic3_2;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author Igor Baiborodine
 */
public class StreamInterfaceExample {

    public static void main(String... args) {

        List<Person> persons = Arrays.asList(
                new Person("Bender", "Rodriguez", "robot"),
                new Person("Philip", "Fry", "human"),
                new Person("Turanga", "Leela", "mutant"));

        long count = persons.stream()
                .filter(new MutantOriginPredicate())
                .count();
        System.out.printf("Found %d mutant(s).", count);
    }
}

class MutantOriginPredicate implements Predicate<Person> {
    @Override
    public boolean test(Person person) {
        return person.getOrigin().equals("mutant");
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
}
