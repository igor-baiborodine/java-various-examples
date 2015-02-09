package com.kiroule.ocpupgradejava8.topic1_1;

import java.util.*;

/**
 * @author Igor Baiborodine
 */
public class StaticInnerClassExample {

    public static void main(String... args) {

        List<FuturamaCharacter> characters = Arrays.asList(
                new FuturamaCharacter("Bender", "Rodriguez"),
                new FuturamaCharacter("Philip", "Fry"),
                new FuturamaCharacter("Turanga", "Leela"));

        System.out.println("Before sorting: " + characters);

        FuturamaCharacter.FirstNameAscComparator comparator = new FuturamaCharacter.FirstNameAscComparator();
        Collections.sort(characters, comparator);
        System.out.println("After sorting: " + characters);
    }
}

class FuturamaCharacter {

    private String firstName;
    private String lastName;

    FuturamaCharacter(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    static class FirstNameAscComparator implements Comparator<FuturamaCharacter> {

        @Override
        public int compare(FuturamaCharacter o1, FuturamaCharacter o2) {
            return o1.getFirstName().compareTo(o2.getFirstName());
        }
    }

    public String getFirstName() {
        return firstName;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}

