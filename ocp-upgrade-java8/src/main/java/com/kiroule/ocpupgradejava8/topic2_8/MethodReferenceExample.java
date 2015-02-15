package com.kiroule.ocpupgradejava8.topic2_8;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

import static java.util.Comparator.comparing;

/**
 * @author Igor Baiborodine
 */
public class MethodReferenceExample {

  public static void main(String... args) {

    BiFunction<String, String, FuturamaCharacter> biFunctionLambda =
        (firstName, lastName) -> new FuturamaCharacter(firstName, lastName);
    BiFunction<String, String, FuturamaCharacter> biFunctionMethodReference =
        FuturamaCharacter::new; // method reference

    List<FuturamaCharacter> characters = Arrays.asList(
        biFunctionLambda.apply("Philip", "Fry"),
        biFunctionMethodReference.apply("Bender", "Rodriguez"),
        biFunctionMethodReference.apply("Turanga", "Leela"));

    System.out.println("\nBefore sorting:");
    characters.forEach(c -> c.printFullName());

    System.out.println("\nAfter sorting by last name(ASC):");
    characters.sort((FuturamaCharacter c1, FuturamaCharacter c2) -> c1.getLastName()
        .compareTo(c2.getLastName()));
    characters.forEach(FuturamaCharacter::printFullName); // method reference

    System.out.println("\nAfter sorting by first name(ASC):");
    characters.sort(comparing(FuturamaCharacter::getFirstName)); // method reference
    characters.forEach(FuturamaCharacter::printFullName); // method reference
  }
}

class FuturamaCharacter {

  private String firstName;
  private String lastName;

  public FuturamaCharacter(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void printFullName() {
    System.out.println(lastName + ", " + firstName);
  }
}
