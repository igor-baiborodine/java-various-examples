package com.kiroule.ocpupgradejava8.topic4_3;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static com.kiroule.ocpupgradejava8.topic4_3.Origin.*;

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

        characters.forEach(c -> System.out.printf("%s, %s %n", getCharacterFullName(c), getOriginName(c)));

        System.out.printf("%nRobots: %n");
        characters.stream()
                .filter(new RobotOriginPredicate())
                .forEach(c -> System.out.printf("%s %n", getCharacterFullName(c)));
    }

    private static String getCharacterFullName(Optional<FuturamaCharacter> c) {
        return c.map(FuturamaCharacter::toString)
                .orElse("Empty FuturamaCharacter optional!");
    }

    private static String getOriginName(Optional<FuturamaCharacter> character) {
        return character.flatMap(FuturamaCharacter::getOrigin)
                .map(Origin::name)
                .orElse("Possible alien origin!");
    }

    private static Optional<FuturamaCharacter> createOptional(String firstName, String lastName, Origin origin) {
        return Optional.of(new FuturamaCharacter(firstName, lastName, Optional.ofNullable(origin)));
    }

    private static class RobotOriginPredicate implements Predicate<Optional<FuturamaCharacter>> {
        @Override
        public boolean test(Optional<FuturamaCharacter> character) {
            return character.isPresent()
                    && character.get().getOrigin().isPresent()
                    && ROBOT.equals(character.get().getOrigin().get());
        }
    }
}

enum Origin {
    HUMAN, ROBOT, MUTANT
}

class FuturamaCharacter {
    private String firstName;
    private String lastName;
    private Optional<Origin> origin;

    public FuturamaCharacter(String firstName, String lastName, Optional<Origin> origin) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.origin = origin;
    }

    public Optional<Origin> getOrigin() { return origin; }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}


