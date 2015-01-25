package com.kiroule.ocpupgradejava8.topic3_3;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author Igor Baiborodine
 */
public class FilterCollectionExample {

    public static void main(String... args) {

        List<Person> persons = Arrays.asList(
                new Person("Bender", "Rodriguez", "robot"),
                new Person("Philip", "Fry", "human"),
                new Person("Turanga", "Leela", "mutant"));

        System.out.println("Robots:"); // extreme method chaining
        persons.stream().filter(p -> p.getOrigin().equals("robot")).collect(toList()).forEach(p -> System.out.println(p));
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
