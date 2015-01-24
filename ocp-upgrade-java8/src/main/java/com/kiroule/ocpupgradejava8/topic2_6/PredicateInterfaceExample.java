package com.kiroule.ocpupgradejava8.topic2_6;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * @author Igor Baiborodine
 */
public class PredicateInterfaceExample {

    public static void main(String... args) {

        List<Person> persons = Arrays.asList(
                new Person("Bender", "Rodriguez", "robot"),
                new Person("Philip", "Fry", "human"),
                new Person("Turanga", "Leela", "mutant"));

        System.out.println("Robots:");
        printPersonsByOrigin(persons, p -> p.getOrigin().equals("robot"));

        System.out.println("Mutants:");
        printPersonsByOrigin(persons, new MutantOriginPredicate());

        System.out.println("Humans:");
        printPersonsByOrigin(persons, p -> p.getOrigin().equals("human"),
                p -> System.out.println(p.getFirstName() + " " + p.getLastName().toUpperCase()));
    }

    public static void printPersonsByOrigin(List<Person> persons, Predicate<Person> predicate) {
        for (Person p : persons) {
            if (predicate.test(p)) {
                System.out.println(p.getFirstName() + " " + p.getLastName());
            }
        }
    }

    public static void printPersonsByOrigin(List<Person> persons, Predicate<Person> predicate, Consumer<Person> consumer) {
        for (Person p : persons) {
            if (predicate.test(p)) {
                consumer.accept(p);
            }
        }
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

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getOrigin() { return origin; }
}
