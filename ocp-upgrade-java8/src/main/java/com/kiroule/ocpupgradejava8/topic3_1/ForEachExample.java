package com.kiroule.ocpupgradejava8.topic3_1;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

/**
 * @author Igor Baiborodine
 */
public class ForEachExample {

    public static void main(String... args) {

        BiFunction<String, String, FuturamaCharacter> bf = FuturamaCharacter::new;

        List<FuturamaCharacter> characters = Arrays.asList(
                bf.apply("Philip", "Fry"),
                bf.apply("Bender", "Rodriguez"),
                bf.apply("Turanga", "Leela"));

        // Java 7
        for (FuturamaCharacter character : characters) {
            character.printFullName();
        }

        // Java 8
        characters.forEach(FuturamaCharacter::printFullName);
        characters.forEach(c -> System.out.println(c.getFirstName() + " " + c.getLastName().toUpperCase()));
    }
}

class FuturamaCharacter {

    private String firstName;
    private String lastName;

    public FuturamaCharacter(String firstName, String lastName) {
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

