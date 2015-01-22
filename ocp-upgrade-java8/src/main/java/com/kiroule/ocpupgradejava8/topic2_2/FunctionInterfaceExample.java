package com.kiroule.ocpupgradejava8.topic2_2;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * @author Igor Baiborodine
 */
public class FunctionInterfaceExample {

    public static void main(String... args) {

        Person person1 = new Person("Bender", "Rodriguez");
        Person person2 = new Person("Philip", "Fry");
        Person person3 = new Person("Amy", "Wong");

        List<Person> persons = new ArrayList<>();
        persons.add(person1);
        persons.add(person2);
        persons.add(person3);

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



