package com.kiroule.ocpupgradejava8.topic6_4;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author Igor Baiborodine
 */
public class OtherStreamSourcesExample {

    public static void main(String... args) {

        String[] words = {"sporadic", "perjury", "confounded", "skimming", "incumbent", "jailer"};

        Stream<String> stream = Stream.of(words);
        System.out.println("Words in upper case: ");
        stream.map(String::toUpperCase).forEach(System.out::println);

        System.out.printf("%nWords count: %d%n", Arrays.stream(words).count());

        int minValue = 1;
        int maxValue = 100_000;
        long sum = IntStream.range(minValue, maxValue).sum();
        System.out.printf("%nSum[%d, %d]: %d", minValue, maxValue, sum);
    }
}