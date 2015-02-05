package com.kiroule.ocpupgradejava8.topic4_3;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static com.kiroule.ocpupgradejava8.topic4_3.Species.*;

/**
 * @author Igor Baiborodine
 */
public class OptionalExample {

    public static void main(String... args) {

        List<Optional<FuturamaCharacter>> characters = Arrays.asList(
                createOptional("Bender", "Rodriguez", ROBOT),
                createOptional("Philip", "Fry", HUMAN),
                createOptional("Turanga", "Leela", MUTANT),
                createOptional("John", "Zoidberg", null),
                Optional.empty());

        characters.forEach(c -> System.out.printf("%s, %s %n", getCharacterFullName(c), getSpeciesName(c)));

        System.out.printf("%nRobots: %n");
        characters.stream()
                .filter(new RobotSpeciesPredicate())
                .forEach(c -> System.out.printf("%s %n", getCharacterFullName(c)));
    }

    private static String getCharacterFullName(Optional<FuturamaCharacter> c) {
        return c.map(FuturamaCharacter::toString)
                .orElse("Empty FuturamaCharacter optional!");
    }

    private static String getSpeciesName(Optional<FuturamaCharacter> character) {
        return character.flatMap(FuturamaCharacter::getSpecies)
                .map(Species::name)
                .orElse("Possible alien species!");
    }

    private static Optional<FuturamaCharacter> createOptional(String firstName, String lastName, Species species) {
        return Optional.of(new FuturamaCharacter(firstName, lastName, Optional.ofNullable(species)));
    }

    private static class RobotSpeciesPredicate implements Predicate<Optional<FuturamaCharacter>> {
        @Override
        public boolean test(Optional<FuturamaCharacter> character) {
            return character.isPresent()
                    && character.get().getSpecies().isPresent()
                    && ROBOT.equals(character.get().getSpecies().get());
        }
    }
}

enum Species {
    HUMAN, ROBOT, MUTANT
}

class FuturamaCharacter {
    private String firstName;
    private String lastName;
    private Optional<Species> species;

    public FuturamaCharacter(String firstName, String lastName, Optional<Species> species) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.species = species;
    }

    public Optional<Species> getSpecies() { return species; }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}


