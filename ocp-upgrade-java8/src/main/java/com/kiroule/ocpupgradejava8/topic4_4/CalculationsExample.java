package com.kiroule.ocpupgradejava8.topic4_4;

import java.util.Arrays;
import java.util.List;
import java.util.OptionalDouble;
import java.util.OptionalInt;

/**
 * @author Igor Baiborodine
 */
public class CalculationsExample {

    public static void main(String... args) {

        List<FuturamaCharacter> characters = Arrays.asList(
                new FuturamaCharacter("Bender", "Rodriguez", 5),
                new FuturamaCharacter("Philip", "Fry", 126),
                new FuturamaCharacter("Turanga", "Leela", 22));
        System.out.println("Futurama characters:");
        characters.forEach(System.out::println); // c -> System.out.println(c)

        long count = characters.stream().count();
        System.out.println("\nCharacters count: " + count);

        OptionalInt minAge = characters.stream().mapToInt(FuturamaCharacter::getAge).min();
        System.out.println("Characters min age: "
                + (minAge.isPresent() ? minAge.getAsInt() : "Not available"));

        OptionalInt maxAge = characters.stream().mapToInt(FuturamaCharacter::getAge).max();
        System.out.println("Characters max age: "
                + (maxAge.isPresent() ? maxAge.getAsInt() : "Not available"));

        OptionalDouble averageAge = characters.stream().mapToDouble(FuturamaCharacter::getAge).average();
        System.out.println("Characters average age: "
                + (averageAge.isPresent() ? averageAge.getAsDouble() : "Not available"));

        int sumAge = characters.stream().mapToInt(FuturamaCharacter::getAge).sum();
        System.out.println("Characters sum of ages: " + sumAge);
    }
}

class FuturamaCharacter {
    private String firstName;
    private String lastName;
    private int age = 0;

    public FuturamaCharacter(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public int getAge() { return age; }

    @Override
    public String toString() {
        return firstName + " " + lastName + " [" + age + "]";
    }
}
