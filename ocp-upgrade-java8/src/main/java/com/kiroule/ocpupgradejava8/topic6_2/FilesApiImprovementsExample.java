package com.kiroule.ocpupgradejava8.topic6_2;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Igor Baiborodine
 */
public class FilesApiImprovementsExample {

    public static void main(String... args) throws IOException {

        Path start = new File(".").toPath();
        int maxDepth = Integer.MAX_VALUE;
        BiPredicate<Path, BasicFileAttributes> topicDirectoriesMatcher =
                (path, basicFileAttributes) -> basicFileAttributes.isDirectory() && path.toString().contains("topic");
        Stream<Path> pathStream = Files.find(start, maxDepth, topicDirectoriesMatcher);
        System.out.println("Topic directories ASC:");
        pathStream.sorted().forEach(System.out::println);

        List<Path> paths = Files.walk(start)
                .filter(p -> p.toString().contains("FilesApiImprovementsExample.java"))
                .collect(Collectors.toList());
        System.out.println("\nFile path: " + paths.get(0));

        System.out.println("\nFile content: ");
        Files.lines(paths.get(0), Charset.defaultCharset())
                .forEach(System.out::println);

    }
}
