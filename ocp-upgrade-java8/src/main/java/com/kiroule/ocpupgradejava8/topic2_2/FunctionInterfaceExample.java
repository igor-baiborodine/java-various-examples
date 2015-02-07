package com.kiroule.ocpupgradejava8.topic2_2;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * @author Igor Baiborodine
 */
public class FunctionInterfaceExample {

    public static void main(String... args) {

        List<FuturamaCharacter> characters = Arrays.asList(
                new FuturamaCharacter("Bender", "Rodriguez"),
                new FuturamaCharacter("Philip", "Fry"),
                new FuturamaCharacter("Turanga", "Leela"));

        for (FuturamaCharacter character : characters) {
            System.out.println(character.getFullName(
                    c -> "Full name: " + c.getFirstName() + " " + c.getLastName()
            ));
        }

        characters.forEach(c -> System.out.println(c.getFullName(new InvertedFullNameFunction())));
    }
}

class InvertedFullNameFunction implements Function<FuturamaCharacter, String> {
    @Override
    public String apply(FuturamaCharacter c) {
        return  "Inverted full name: " + c.getLastName() + ", " + c.getFirstName();
    }
}

class FuturamaCharacter {

    private String firstName;
    private String lastName;

    public FuturamaCharacter(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFullName(Function<FuturamaCharacter, String> function) {
        return function.apply(this);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}



