package com.kiroule.ocpupgradejava8.topic4_1;

import java.util.Arrays;
import java.util.List;

/**
 * @author Igor Baiborodine
 */
public class ExtractDataWithMapExample {

    public static void main(String... args) {

        List<FuturamaCharacter> characters = Arrays.asList(
                new FuturamaCharacter("Bender", "Rodriguez", "robot"),
                new FuturamaCharacter("Philip", "Fry", "human"),
                new FuturamaCharacter("Turanga", "Leela", "mutant"),
                new FuturamaCharacter("Zapp", "Brannigan", "human"));

        System.out.println("Distinct futurama character species:");
        characters.stream()
                .map(FuturamaCharacter::getSpecies)
                .distinct()
                .sorted()
                .forEach(System.out::println);

        System.out.println("\nHashcodes:");
        characters.stream()
                .mapToInt(FuturamaCharacter::hashCode)
                .sorted()
                .forEach(System.out::println);
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
    public String toString() {
        return firstName + " " + lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FuturamaCharacter character = (FuturamaCharacter) o;

        if (firstName != null ? !firstName.equals(character.firstName) : character.firstName != null) return false;
        if (lastName != null ? !lastName.equals(character.lastName) : character.lastName != null) return false;
        return !(species != null ? !species.equals(character.species) : character.species != null);

    }

    @Override
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (species != null ? species.hashCode() : 0);
        return result;
    }
}
