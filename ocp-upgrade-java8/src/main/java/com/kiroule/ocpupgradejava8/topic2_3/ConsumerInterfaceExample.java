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
        c -> System.out.println(c.getFirstName() + " " + c.getLastName());
    System.out.println("\nFull names:");
    characters.forEach(fullNameConsumer);

    System.out.println("\nInverted full names:");
    characters.forEach(new InvertedFullNameConsumer());

    System.out.println("\nFull names after introducing the side effect:");
    characters.forEach(fullNameConsumer);
  }
}

class InvertedFullNameConsumer implements Consumer<FuturamaCharacter> {

  @Override
  public void accept(FuturamaCharacter c) {
    c.setLastName(c.getLastName().toUpperCase()); // introducing a side effect
    System.out.println(c.getLastName() + ", " + c.getFirstName());
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



