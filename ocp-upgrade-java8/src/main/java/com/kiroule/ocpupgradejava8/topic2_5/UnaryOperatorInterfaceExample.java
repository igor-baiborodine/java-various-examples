package com.kiroule.ocpupgradejava8.topic2_5;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

/**
 * @author Igor Baiborodine
 */
public class UnaryOperatorInterfaceExample {

    public static void main(String... args) {

        Person person1 = new Person("Bender", "Rodriguez");
        Person person2 = new Person("Philip", "Fry");
        Person person3 = new Person("Amy", "Wong");

        List<Person> persons = new ArrayList<>();
        persons.add(person1);
        persons.add(person2);
        persons.add(person3);

        UnaryOperator<String> toUpperCaseUnaryOperator = (s) -> s.toUpperCase();
        for (Person p : persons) {
            System.out.println("Full name: " + p.getFirstName() + " " + toUpperCaseUnaryOperator.apply(p.getLastName()));
        }
        // the same, one-liner
        persons.forEach(p -> System.out.println("Full name: " + p.getFirstName() + " " + toUpperCaseUnaryOperator.apply(p.getLastName())));
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
}