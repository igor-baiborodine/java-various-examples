package com.kiroule.ocpupgradejava8.topic3_3;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author Igor Baiborodine
 */
public class FilterCollectionExample {

  public static void main(String... args) {

    List<FuturamaCharacter> characters = Arrays.asList(
        new FuturamaCharacter("Bender", "Rodriguez", "robot"),
        new FuturamaCharacter("Philip", "Fry", "human"),
        new FuturamaCharacter("Turanga", "Leela", "mutant"));

    System.out.println("Robots:");
    characters.stream()
        .filter(c -> c.getSpecies().equals("robot"))
        .collect(toList())
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

  public String getSpecies() {
    return species;
  }

  @Override
  public String toString() {
    return firstName + " " + lastName;
  }
}
