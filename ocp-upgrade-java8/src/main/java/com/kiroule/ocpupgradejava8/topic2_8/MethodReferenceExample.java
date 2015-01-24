package com.kiroule.ocpupgradejava8.topic2_8;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

import static java.util.Comparator.comparing;

/**
 * @author Igor Baiborodine
 */
public class MethodReferenceExample {

    public static void main(String... args) {

        BiFunction<String, String, Person> bf = (firstName, lastName) -> new Person(firstName, lastName);
        BiFunction<String, String, Person> bf2 = Person::new;

        List<Person> persons = Arrays.asList(
                bf.apply("Philip", "Fry"),
                bf2.apply("Bender", "Rodriguez"),
                bf2.apply("Turanga", "Leela"));

        System.out.println("\nBefore sorting:");
        persons.forEach(p -> p.printFullName());

        System.out.println("\nAfter sorting by last name(ASC):");
        persons.sort((Person p1, Person p2) -> p1.getLastName().compareTo(p2.getLastName()));
        persons.forEach(Person::printFullName);

        System.out.println("\nAfter sorting by first name(ASC):");
        persons.sort(comparing(Person::getFirstName));
        persons.forEach(Person::printFullName);
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
