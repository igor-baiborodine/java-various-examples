ocp-upgrade-java8
-----------------

#####These are preparation materials for the "Upgrade to Java SE 8 Programmer" exam \(1Z1-810\).

####Exam topics:

#####1. Lambda Expressions
- 1.1. [Describe Java inner classes and develop the code that uses Java inner classes (such as: nested class, static class, local class and anonymous classes)](http://docs.oracle.com/javase/tutorial/java/javaOO/nested.html)

    Inner class example: [OuterHelloWorld.java](https://github.com/igor-baiborodine/java-various-examples/blob/master/ocp-upgrade-java8/src/main/java/com/kiroule/ocpupgradejava8/topic1_1/OuterHelloWorld.java)
    
    Static inner class example: [Employee.java](https://github.com/igor-baiborodine/java-various-examples/blob/master/ocp-upgrade-java8/src/main/java/com/kiroule/ocpupgradejava8/topic1_1/Employee.java)/[EmployeeTest.java](https://github.com/igor-baiborodine/java-various-examples/blob/master/ocp-upgrade-java8/src/main/java/com/kiroule/ocpupgradejava8/topic1/EmployeeTest.java)
    
    Anonymous class example: [AnonymousHelloWorld.java](https://github.com/igor-baiborodine/java-various-examples/blob/master/ocp-upgrade-java8/src/main/java/com/kiroule/ocpupgradejava8/topic1_1/AnonymousHelloWorld.java) 
    
    Local method class example: [LocalMethodHelloWorld.java](https://github.com/igor-baiborodine/java-various-examples/blob/master/ocp-upgrade-java8/src/main/java/com/kiroule/ocpupgradejava8/topic1_1/LocalMethodHelloWorld.java)
    
- 1.2. Define and write functional interfaces

  A _functional interface_ is an interface that specifies exactly one abstract method. The signature of the abstract method of a functional interface is called a function descriptor. @FunctionalInterface annotation is used to indicate that the interface is intended to be a functional interface.
  
  Functional interface example: [FunctionalInterfaceHelloWorld.java](https://github.com/igor-baiborodine/java-various-examples/blob/master/ocp-upgrade-java8/src/main/java/com/kiroule/ocpupgradejava8/topic1_2/FunctionalInterfaceHelloWorld.java) 
  
- 1.3. [Describe a Lambda expression;](http://en.wikipedia.org/wiki/Anonymous_function#Java) refactor the code that use anonymous inner class to use Lambda expression; including type inference, target typing

#####2. Using Built in Lambda Types
- 2.1. Describe the built in interfaces included in Java 8 – java.util.function package
- 2.2. Develop code that uses Function interface
- 2.3. Develop code that uses Consumer interface
- 2.4. Develop code that uses Supplier interface
- 2.5. Develop code that uses UnaryOperator interface
- 2.6. Develop code that uses Predicate interface
- 2.7. Develop the code that use primitive and binary variations of base interfaces of java.util.function package
- 2.8. Develop the code that use method reference; including refactor the code that use Lambda expression to use method references

#####3. Filtering Collections with Lambdas
- 3.1. Develop the code that iterates a collection by using forEach; including method chaining
- 3.2. Describe the Stream interface and pipelines
- 3.3. Filter a collection using lambda expressions
- 3.4. Identify lambda operations that are lazy

#####4. Collection Operations with Lambda
- 4.1. Develop the code to extract data from an object using map
- 4.2. Search for data using search methods including: findFirst, findAny, anyMatch, allMatch, noneMatch.
- 4.3. Describe the unique characteristics of the Optional classes
- 4.4. Perform calculations using methods: count, max, min, average, sum
- 4.5. Sort a collection using lambda expressions
- 4.6. Save results to a collection by using the collect method and Collector class; including methods such as averagingDouble,groupingBy,joining,partitioningBy

#####5. Parallel Streams
- 5.1. Develop the code that use parallel streams
- 5.2. Implement decomposition, reduction, in streams

#####6. Lambda Cookbook
- 6.1. Develop the code that use Java SE 8 collection improvements: Colleciton.removeIf, List.replaceAll, Map.computeIfAbsent/Present, Map.forEach
- 6.2. Read files using lambda improvements: Files.find, lines(), walk()
- 6.3. Use merge, flatMap methods on a collection
- 6.4. Describe other stream sources: Arrays.stream(), IntStream.range()

#####7. Method Enhancements
- 7.1. Adding static methods to interfaces
- 7.2. Define and use a default method of a interface; Describe the inheritance rules for a default method

#####8. Use Java SE 8 Date/Time API - 10%
- 8.1. Create and manage date-based and time-based events; including combination of date and time into a single object using  LocalDate, LocalTime, LocalDateTime, Instant, Period, Duration
- 8.2. Work with dates and times across time-zones and manage changes resulting from daylight savings
- 8.3. Define and create timestamps, periods and durations; apply formatting to local and zoned dates and times

#####9.JavaScript on Java with Nashorn
- 9.1. Develop Javascript code that creates and uses Java members such as Java objects, methods, JavaBeans, Arrays, Collections, Interfaces.
- 9.2. Develop code that  evaluates JavaScript in java, passes Java object to Javascript, invokes Javascript function and calls methods on Javascript objects.
