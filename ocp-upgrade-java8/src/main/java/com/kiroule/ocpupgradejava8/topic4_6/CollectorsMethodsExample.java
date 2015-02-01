package com.kiroule.ocpupgradejava8.topic4_6;

import java.util.*;
import java.util.stream.Collectors;

import static com.kiroule.ocpupgradejava8.topic4_6.Origin.*;

/**
 * @author Igor Baiborodine
 */
public class CollectorsMethodsExample {

    public static void main(String... args) {

        List<FuturamaCharacter> characters = Arrays.asList(
                new FuturamaCharacter("Bender", "Rodriguez", 5, ROBOT),
                new FuturamaCharacter("Philip", "Fry", 126, HUMAN),
                new FuturamaCharacter("Turanga", "Leela", 22, MUTANT),
                new FuturamaCharacter("Hubert", "Farnsworth", 161, HUMAN));
        System.out.println("Futurama characters:");
        characters.forEach(System.out::println);

        System.out.println("\nYounger than 100 years:");
        List<FuturamaCharacter> youngerThan100Years = characters
                .stream()
                .filter(c -> c.getAge() < 100)
                // sorted by age DESC
                .sorted((c1, c2) -> new Integer(c2.getAge()).compareTo(new Integer(c1.getAge())))
                //.collect(Collectors.toList());
                .collect(Collectors.toCollection(ArrayList::new));
        youngerThan100Years.forEach(System.out::println);

        Double averageAge = characters
                .stream()
                .collect(Collectors.averagingInt(FuturamaCharacter::getAge));
        System.out.println("\nAverage age: " + averageAge);

        IntSummaryStatistics intSummaryStatistics = characters
                .stream()
                .collect(Collectors.summarizingInt(FuturamaCharacter::getAge));
        System.out.println("\nintSummaryStatistics: " + intSummaryStatistics);
        System.out.println("Sum of ages: " + intSummaryStatistics.getSum());

        Map<Origin, List<FuturamaCharacter>> originToFuturamaCharactersMap = characters
                .stream()
                .collect(Collectors.groupingBy(FuturamaCharacter::getOrigin));
        System.out.println("\nHumans: ");
        originToFuturamaCharactersMap.get(HUMAN).forEach(System.out::println);

        Map<Boolean, List<FuturamaCharacter>> isHumanToFuturamaCharactersMap = characters
                .stream()
                .collect(Collectors.partitioningBy(c -> HUMAN.equals(c.getOrigin())));
        System.out.println("\nNon-humans: ");
        isHumanToFuturamaCharactersMap.get(Boolean.FALSE).forEach(System.out::println);

        String firstNames = characters
                .stream()
                .map(FuturamaCharacter::getFirstName)
                .sorted()
                .collect(Collectors.joining(", "));
        System.out.println("\nFirst names: " + firstNames);
    }
}

enum Origin {
    HUMAN, ROBOT, MUTANT
}

class FuturamaCharacter {
    private String firstName;
    private String lastName;
    private int age = 0;
    private Origin origin = HUMAN;

    public FuturamaCharacter(String firstName, String lastName, int age, Origin origin) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.origin = origin;
    }

    public String getFirstName() { return firstName; }

    public String getLastName() { return lastName; }

    public int getAge() { return age; }

    public Origin getOrigin() { return origin; }

    @Override
    public String toString() {
        return firstName + " " + lastName + " [" + age + "][" + origin + "]";
    }
}
