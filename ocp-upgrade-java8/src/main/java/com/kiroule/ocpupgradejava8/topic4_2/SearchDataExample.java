package com.kiroule.ocpupgradejava8.topic4_2;

import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;

/**
 * @author Igor Baiborodine
 */
public class SearchDataExample {

    public static void main(String... args) {

        List<FuturamaCharacter> characters = Arrays.asList(
                new FuturamaCharacter("Bender", "Rodriguez", "robot"),
                new FuturamaCharacter("Philip", "Fry", "human"),
                new FuturamaCharacter("Turanga", "Leela", "mutant"),
                new FuturamaCharacter("Zapp", "Brannigan", "human"));

        System.out.println("\nAll hashcodes:");
        characters.stream()
                .mapToInt(FuturamaCharacter::hashCode)
                .sorted()
                .forEach(System.out::println);

        OptionalInt firstNegativeHashcode =
                characters.stream()
                        .mapToInt(FuturamaCharacter::hashCode)
                        .sorted()
                        .findFirst();
        System.out.println("\nFirst negative hashcode: " + firstNegativeHashcode);


        OptionalInt anyHashcode =
                characters.stream()
                        .mapToInt(FuturamaCharacter::hashCode)
                        .filter(hc -> hc != firstNegativeHashcode.getAsInt())
                        .findAny();
        System.out.println("Any hashcode: " + anyHashcode);

        System.out.println("\nFuturama characters:");
        characters.forEach(System.out::println);

        boolean containsRobot = characters.stream()
                .anyMatch(p -> p.getOrigin().equals("robot"));
        System.out.println("\nCharacters contain a robot: " + containsRobot);

        boolean containsAlien = characters.stream()
                .noneMatch(p -> p.getOrigin().equals("alien"));
        System.out.println("Characters do not contain an alien: " + containsAlien);

        boolean containsOnlyHumans = characters.stream()
                .allMatch(p -> p.getOrigin().equals("human"));
        System.out.println("Characters contain only humans: " + containsOnlyHumans);
    }
}

class FuturamaCharacter {
    private String firstName;
    private String lastName;
    private String origin = "human";

    public FuturamaCharacter(String firstName, String lastName, String origin) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.origin = origin;
    }

    public String getOrigin() { return origin; }

    @Override
    public String toString() {
        return firstName + " " + lastName + " [" + origin + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FuturamaCharacter character = (FuturamaCharacter) o;

        if (firstName != null ? !firstName.equals(character.firstName) : character.firstName != null) return false;
        if (lastName != null ? !lastName.equals(character.lastName) : character.lastName != null) return false;
        return !(origin != null ? !origin.equals(character.origin) : character.origin != null);
    }

    @Override
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (origin != null ? origin.hashCode() : 0);
        return result;
    }
}
