package com.kiroule.ocpupgradejava8.topic2_3;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author Igor Baiborodine
 */
public class ConsumerInterfaceExample {

    public static void main(String... args) {

        List<FuturamaCharacter> characters = Arrays.asList(
                new FuturamaCharacter("Bender", "Rodriguez"),
                new FuturamaCharacter("Philip", "Fry"),
                new FuturamaCharacter("Turanga", "Leela"));

        Consumer<FuturamaCharacter> fullNameConsumer =
                c -> System.out.println("Full name: " + c.getFirstName() + " " + c.getLastName());
        characters.forEach(fullNameConsumer);

        for (FuturamaCharacter character : characters) {
            character.printFullName(new InvertedFullNameConsumer());
        }

        characters.forEach(fullNameConsumer);
    }
}

class InvertedFullNameConsumer implements Consumer<FuturamaCharacter> {
    @Override
    public void accept(FuturamaCharacter c) {
        c.setLastName(c.getLastName().toUpperCase()); // introducing side effect
        System.out.println("Inverted full name: " + c.getLastName() + ", " + c.getFirstName());
    }
}

class FuturamaCharacter {

    private String firstName;
    private String lastName;

    public FuturamaCharacter(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void printFullName(Consumer<FuturamaCharacter> consumer) {
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



