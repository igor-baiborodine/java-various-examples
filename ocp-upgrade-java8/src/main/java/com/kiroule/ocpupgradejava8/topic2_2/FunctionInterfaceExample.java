package com.kiroule.ocpupgradejava8.topic2_2;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * @author Igor Baiborodine
 */
public class FunctionInterfaceExample {

    public static void main(String... args) {

        List<Person> persons = Arrays.asList(
                new Person("Bender", "Rodriguez"),
                new Person("Philip", "Fry"),
                new Person("Turanga", "Leela"));

        for (Person person : persons) {
            System.out.println(person.getFullName(
                    e -> "Full name: " + e.getFirstName() + " " + e.getLastName()
            ));
        }

        persons.forEach(p -> System.out.println(p.getFullName(new InvertedFullNameFunction())));
    }
}

class InvertedFullNameFunction implements Function<Person, String> {
    @Override
    public String apply(Person p) {
        return  "Inverted full name: " + p.getLastName() + ", " + p.getFirstName();
    }
}

class Person {

    private String firstName;
    private String lastName;

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFullName(Function<Person, String> function) {
        return function.apply(this);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}



