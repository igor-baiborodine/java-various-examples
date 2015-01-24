package com.kiroule.ocpupgradejava8.topic2_3;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author Igor Baiborodine
 */
public class ConsumerInterfaceExample {

    public static void main(String... args) {

        List<Person> persons = Arrays.asList(
                new Person("Bender", "Rodriguez"),
                new Person("Philip", "Fry"),
                new Person("Turanga", "Leela"));

        Consumer<Person> fullName = (Person p) -> System.out.println("Full name: " + p.getFirstName() + " " + p.getLastName());
        persons.forEach(fullName);

        for (Person person : persons) {
            person.printFullName(new InvertedFullNameConsumer());
        }

        persons.forEach(fullName);
    }
}

class InvertedFullNameConsumer implements Consumer<Person> {
    @Override
    public void accept(Person p) {
        p.setLastName(p.getLastName().toUpperCase()); // introducing side effect
        System.out.println("Inverted full name: " + p.getLastName() + ", " + p.getFirstName());
    }
}

class Person {

    private String firstName;
    private String lastName;

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void printFullName(Consumer<Person> consumer) {
        consumer.accept(this);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }
}



