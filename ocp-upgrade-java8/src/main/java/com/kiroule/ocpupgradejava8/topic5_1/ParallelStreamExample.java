package com.kiroule.ocpupgradejava8.topic5_1;

import java.util.stream.IntStream;

/**
 * @author Igor Baiborodine
 */
public class ParallelStreamExample {

  public static void main(String... args) {

    int minValue = 1;
    int maxValue = 100_000;
    long sum = IntStream.range(minValue, maxValue)
        .parallel()
        .sum();
    System.out.printf("Sum[%d, %d]: %d", minValue, maxValue, sum);
  }
}
