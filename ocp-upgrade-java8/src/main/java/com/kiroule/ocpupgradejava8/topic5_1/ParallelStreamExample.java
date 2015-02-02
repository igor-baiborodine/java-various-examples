package com.kiroule.ocpupgradejava8.topic5_1;

import java.util.stream.IntStream;

/**
 * @author Igor Baiborodine
 */
public class ParallelStreamExample {

    public static void main(String... args) {

        int maxValue = 100_000;
        long sum = IntStream.range(1, maxValue)
                .parallel()
                .sum();
        System.out.printf("Sum[1, %d]: %d", maxValue, sum);
    }
}
