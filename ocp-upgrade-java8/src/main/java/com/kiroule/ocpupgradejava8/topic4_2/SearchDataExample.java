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

    OptionalInt firstNegativeHashcode = characters
        .stream()
        .mapToInt(FuturamaCharacter::hashCode)
        .sorted()
        .findFirst();
    System.out.println("\nFirst negative hashcode: " + firstNegativeHashcode);

    OptionalInt anyHashcode = characters
        .stream()
        .mapToInt(FuturamaCharacter::hashCode)
        .filter(hc -> hc != firstNegativeHashcode.getAsInt())
        .findAny();
    System.out.println("Any hashcode except the first negative hashcode: "
                       + (anyHashcode.isPresent() ? anyHashcode.getAsInt() : "Not found"));

    System.out.println("\nFuturama characters:");
    characters.forEach(System.out::println);

    boolean containsRobot = characters
        .stream()
        .anyMatch(c -> c.getSpecies().equals("robot"));
    System.out.println("\nCharacters contain a robot: " + containsRobot);

    boolean containsAlien = characters
        .stream()
        .noneMatch(c -> c.getSpecies().equals("alien"));
    System.out.println("Characters do not contain an alien: " + containsAlien);

    boolean containsOnlyHumans = characters
        .stream()
        .allMatch(FuturamaCharacter::isHumanSpecies);
    System.out.println("Characters contain only humans: " + containsOnlyHumans);
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

  public String getSpecies() {
    return species;
  }

  public boolean isHumanSpecies() {
    return "human".equals(species);
  }

  @Override
  public String toString() {
    return firstName + " " + lastName + " [" + species + "]";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    FuturamaCharacter character = (FuturamaCharacter) o;

    if (firstName != null ? !firstName.equals(character.firstName) : character.firstName != null) {
      return false;
    }
    if (lastName != null ? !lastName.equals(character.lastName) : character.lastName != null) {
      return false;
    }
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
