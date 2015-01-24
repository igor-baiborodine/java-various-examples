package com.kiroule.ocpupgradejava8.topic2_7;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;

/**
 * @author Igor Baiborodine
 */
public class PrimitiveAndBinaryVariationsExample {

    public static void main(String... args) {

        BiFunction<String, String, Person> bf = (firstName, lastName) -> new Person(firstName, lastName);

        List<Person> persons = Arrays.asList(
                bf.apply("Bender", "Rodriguez"),
                bf.apply("Philip", "Fry"),
                bf.apply("Turanga", "Leela"));

        BiConsumer<String, String> biConsumer = (firstName, lastName) -> System.out.println(lastName.toUpperCase() + ", " + firstName);
        for (Person person : persons) {
            biConsumer.accept(person.getFirstName(), person.getLastName());
        }

        IntBinaryOperator intBinaryOperator = (s1, s2) -> s1 * s2;
        int side1 = 3, side2 = 8;
        IntConsumer intConsumer = (area) -> System.out.println("Area for rectangle with sides["
                + side1 + ", " + side2 + "] is " + area);
        intConsumer.accept(intBinaryOperator.applyAsInt(side1, side2));
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
