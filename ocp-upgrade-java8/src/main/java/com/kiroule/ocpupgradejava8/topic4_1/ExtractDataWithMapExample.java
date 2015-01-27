package com.kiroule.ocpupgradejava8.topic4_1;

import java.util.Arrays;
import java.util.List;

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
                .distinct()
                .sorted()
                .forEach(s -> System.out.println(s));

        System.out.println("\nHashcodes:");
        persons.stream()
                .mapToInt(Person::hashCode)
                .sorted()
                .forEach(hc -> System.out.println(hc));
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (firstName != null ? !firstName.equals(person.firstName) : person.firstName != null) return false;
        if (lastName != null ? !lastName.equals(person.lastName) : person.lastName != null) return false;
        if (origin != null ? !origin.equals(person.origin) : person.origin != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (origin != null ? origin.hashCode() : 0);
        return result;
    }
}
