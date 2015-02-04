package com.kiroule.ocpupgradejava8.topic6_1;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.kiroule.ocpupgradejava8.topic6_1.Species.*;

/**
 * @author Igor Baiborodine
 */
public class CollectionImprovementsExample {

    public static void main(String... args) {

        List<FuturamaCharacter> characters = new LinkedList<>(Arrays.asList(
                new FuturamaCharacter("Bender", "Rodriguez", 5, ROBOT),
                new FuturamaCharacter("Philip", "Fry", 126, HUMAN),
                new FuturamaCharacter("Turanga", "Leela", 22, MUTANT),
                new FuturamaCharacter("Hubert", "Farnsworth", 161, HUMAN)));
        System.out.println("Futurama characters:");
        characters.forEach(System.out::println);

        // Map.forEach
        Map<Species, List<FuturamaCharacter>> speciesToFuturamaCharactersMap = characters
                .stream()
                .collect(Collectors.groupingBy(FuturamaCharacter::getSpecies));
        System.out.println("\nCharacters by species: ");
        speciesToFuturamaCharactersMap.forEach((k, v) -> System.out.println(k + ": " + v));

        // List.removeIf
        System.out.println("\nHumans: ");
        characters.removeIf(c -> !c.getSpecies().equals(Species.HUMAN));
        characters.forEach(System.out::println);

        // List.replaceAll
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
        numbers.replaceAll(x -> x % 2 == 0 ? x : 0);
        System.out.println("\nOdd numbers replaced by zero: " + numbers);

        // Map.computeIfAbsent
        class Fibonacci {
            private Map<Integer, Integer> cache = new ConcurrentHashMap<>();

            public int get(int n) {
                if (n == 0 || n == 1) return n;
                return cache.computeIfAbsent(n, (key) -> get(n - 2) + get(n - 1));
            }
        }
        Fibonacci fibonacci = new Fibonacci();
        System.out.println("\nFirst 10 Fibonacci numbers: ");
        IntStream.range(0, 10).forEach(x -> System.out.println(fibonacci.get(x)));
    }
}

enum Species {
    HUMAN, ROBOT, MUTANT
}

class FuturamaCharacter {
    private String firstName;
    private String lastName;
    private int age = 0;
    private Species species = HUMAN;

    public FuturamaCharacter(String firstName, String lastName, int age, Species species) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.species = species;
    }

    public String getFirstName() { return firstName; }

    public String getLastName() { return lastName; }

    public Species getSpecies() { return species; }

    @Override
    public String toString() {
        return firstName + " " + lastName + " [" + age + "][" + species + "]";
    }
}
