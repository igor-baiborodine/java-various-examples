package com.kiroule.ocpupgradejava8.topic2_7;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

/**
 * @author Igor Baiborodine
 */
public class PrimitiveAndBinaryVariationsExample {

    public static void main(String... args) {

        BiFunction<String, String, Person> bf = Person::new;

        List<Person> persons = Arrays.asList(
                bf.apply("Bender", "Rodriguez"),
                bf.apply("Philip", "Fry"),
                bf.apply("Turanga", "Leela"));

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
