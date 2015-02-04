package com.kiroule.ocpupgradejava8.topic6_3;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Igor Baiborodine
 */
public class MergeAndFlatMapExample {

    public static void main(String... args) {

        List<String> phrases = Arrays.asList(
                "sporadic perjury",
                "confounded skimming",
                "incumbent jailer",
                "confounded jailer");

        List<String> uniqueWords = phrases
                .stream()
                .flatMap(phrase -> Stream.of(phrase.split(" +")))
                .distinct()
                .collect(Collectors.toList());
        System.out.println("Unique words: " + uniqueWords);

        Map<Integer, String> lengthToWordsMap = new HashMap<>();
        Consumer<String> action = w -> {
            BiFunction<String, String, String> remappingFunction = (value, newValue) -> value + ", " + newValue;
            lengthToWordsMap.merge(w.length(), w, remappingFunction);
        };
        uniqueWords.forEach(action);

        lengthToWordsMap.forEach((key, value) -> System.out.printf("%nWords with length %d: %s", key, value));
    }
}

