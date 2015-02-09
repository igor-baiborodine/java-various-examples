package com.kiroule.ocpupgradejava8.topic3_2;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author Igor Baiborodine
 */
public class StreamInterfaceExample {

    public static void main(String... args) {

        List<FuturamaCharacter> characters = Arrays.asList(
                new FuturamaCharacter("Bender", "Rodriguez", "robot"),
                new FuturamaCharacter("Philip", "Fry", "human"),
                new FuturamaCharacter("Turanga", "Leela", "mutant"));

        long count = characters.stream()
                .filter(new MutantSpeciesPredicate())
                .count();
        System.out.printf("Found %d mutant(s).", count);
    }
}

class MutantSpeciesPredicate implements Predicate<FuturamaCharacter> {
    @Override
    public boolean test(FuturamaCharacter character) {
        return character.getSpecies().equals("mutant");
    }
}

class FuturamaCharacter {
    private String firstName;
    private String lastName;
    private String species = "human";

    public FuturamaCharacter(String firstName, String lastName, String species) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.species = species;
    }

    public String getSpecies() { return species; }
}
