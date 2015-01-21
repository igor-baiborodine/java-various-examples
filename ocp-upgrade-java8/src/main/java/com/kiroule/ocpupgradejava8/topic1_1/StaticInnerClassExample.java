package com.kiroule.ocpupgradejava8.topic1_1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Igor Baiborodine
 */
public class StaticInnerClassExample {

    public static void main(String... args) {

        Person person1 = new Person("Bender", "Rodriguez");
        Person person2 = new Person("Philip", "Fry");
        Person person3 = new Person("Amy", "Wong");

        List<Person> persons = new ArrayList<>();
        persons.add(person1);
        persons.add(person2);
        persons.add(person3);

        System.out.println("Before sorting: " + persons);

        Person.FirstNameAscComparator comparator = new Person.FirstNameAscComparator();
        Collections.sort(persons, comparator);
        System.out.println("After sorting: " + persons);
    }
}

class Person {

    private String firstName;
    private String lastName;

    Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    static class FirstNameAscComparator implements Comparator<Person> {

        @Override
        public int compare(Person o1, Person o2) {
            return o1.getFirstName().compareTo(o2.getFirstName());
        }
    }

    public String getFirstName() {
        return firstName;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}

