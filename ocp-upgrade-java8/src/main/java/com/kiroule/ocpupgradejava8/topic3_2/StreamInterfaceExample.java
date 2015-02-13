package com.kiroule.ocpupgradejava8.topic3_2;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.lang.String.format;

/**
 * @author Igor Baiborodine
 */
public class StreamInterfaceExample {

    public static void main(String... args) {

        List<FuturamaCharacter> characters = Arrays.asList(
                new FuturamaCharacter("Bender", "Rodriguez", "robot"),
                new FuturamaCharacter("Philip", "Fry", "human"),
                new FuturamaCharacter("Turanga", "Leela", "mutant"));

        Stream<FuturamaCharacter> characterStream = characters.stream();

        long count = characterStream
                .peek(c -> System.out.println("Filtered value: " + c))
                .filter(new MutantSpeciesPredicate())
                .count();
        System.out.printf("%nFound %d mutant(s).", count);

        // Cannot re-use the stream, it will throw a runtime exception:
        // java.lang.IllegalStateException: stream has already been operated upon or closed
        characterStream.count();
    }
}

class MutantSpeciesPredicate implements Predicate<FuturamaCharacter> {
    @Override
    public boolean test(FuturamaCharacter character) {
        return "mutant".equals(character.getSpecies());
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

    @Override
    public String toString() { return format("%s %s [%s]", firstName, lastName, species); }
}
