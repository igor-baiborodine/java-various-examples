package com.kiroule.ocpupgradejava8.topic5_2;

import java.util.Arrays;
import java.util.List;

/**
 * @author Igor Baiborodine
 */
public class ReductionExample {

    public static void main(String... args) {

        List<FuturamaCharacter> characters = Arrays.asList(
                new FuturamaCharacter("Bender", "Rodriguez", 5),
                new FuturamaCharacter("Philip", "Fry", 126),
                new FuturamaCharacter("Turanga", "Leela", 22));
        System.out.println("Futurama characters:");
        characters.forEach(c -> System.out.println(c));

        int sumAge = characters
                .stream()
                .parallel()
                .mapToInt(FuturamaCharacter::getAge).sum();
        System.out.println("\nSum of ages: " + sumAge);
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
