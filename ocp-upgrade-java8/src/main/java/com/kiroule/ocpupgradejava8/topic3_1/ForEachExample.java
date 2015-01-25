package com.kiroule.ocpupgradejava8.topic3_1;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

/**
 * @author Igor Baiborodine
 */
public class ForEachExample {

    public static void main(String... args) {

        BiFunction<String, String, Person> bf = Person::new; 

        List<Person> persons = Arrays.asList(
                bf.apply("Philip", "Fry"),
                bf.apply("Bender", "Rodriguez"),
                bf.apply("Turanga", "Leela"));

        // Java 7
        for (Person person : persons) {
            person.printFullName();
        }

        // Java 8
        persons.forEach(Person::printFullName);
        persons.forEach(p -> System.out.println(p.getFirstName() + " " + p.getLastName().toUpperCase()));
    }
}

class Person {

    private String firstName;
    private String lastName;

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void printFullName() {
        System.out.println(lastName + ", " + firstName);
    }
}

