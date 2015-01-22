ocp-upgrade-java8
-----------------

#####These are preparation materials for the Oracle's "Upgrade to Java SE 8 Programmer" exam \([1Z1-810](http://education.oracle.com/pls/web_prod-plq-dad/db_pages.getpage?page_id=5001&get_params=p_exam_id:1Z0-810)\).

####Exam topics:

#####1. Lambda Expressions
- 1.1. [Describe Java inner classes and develop the code that uses Java inner classes (such as: nested class, static class, local class and anonymous classes)](http://docs.oracle.com/javase/tutorial/java/javaOO/nested.html)

    [InnerClassExample.java](https://github.com/igor-baiborodine/java-various-examples/blob/master/ocp-upgrade-java8/src/main/java/com/kiroule/ocpupgradejava8/topic1_1/InnerClassExample.java)
    
    [StaticInnerClassExample.java](https://github.com/igor-baiborodine/java-various-examples/blob/master/ocp-upgrade-java8/src/main/java/com/kiroule/ocpupgradejava8/topic1_1/StaticInnerClassExample.java)
    
    [AnonymousClassExample.java](https://github.com/igor-baiborodine/java-various-examples/blob/master/ocp-upgrade-java8/src/main/java/com/kiroule/ocpupgradejava8/topic1_1/AnonymousClassExample.java) 
    
    [LocalMethodClassExample.java](https://github.com/igor-baiborodine/java-various-examples/blob/master/ocp-upgrade-java8/src/main/java/com/kiroule/ocpupgradejava8/topic1_1/LocalMethodClassExample.java)
    
- 1.2. Define and write functional interfaces

  A _functional interface_ is an interface that specifies exactly one abstract method. The signature of the abstract method of a functional interface is called a function descriptor. @FunctionalInterface annotation is used to indicate that the interface is intended to be a functional interface.
  
  [FunctionalInterfaceExample.java](https://github.com/igor-baiborodine/java-various-examples/blob/master/ocp-upgrade-java8/src/main/java/com/kiroule/ocpupgradejava8/topic2_2/FunctionInterfaceExample.java) 
  
- 1.3. [Describe a Lambda expression;](http://en.wikipedia.org/wiki/Anonymous_function#Java) refactor the code that use anonymous inner class to use Lambda expression; including type inference, target typing

  [Type inference](http://docs.oracle.com/javase/tutorial/java/generics/genTypeInference.html)
  
  [Target typing](http://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html#target-typing)
  
  [RefactoringCodeExample.java](https://github.com/igor-baiborodine/java-various-examples/blob/master/ocp-upgrade-java8/src/main/java/com/kiroule/ocpupgradejava8/topic1_3/RefactoringCodeExample.java)
  
  [TypeInferenceExample.java](https://github.com/igor-baiborodine/java-various-examples/blob/master/ocp-upgrade-java8/src/main/java/com/kiroule/ocpupgradejava8/topic1_3/TypeInferenceExample.java)
      
#####2. Using Built in Lambda Types
- 2.1. Describe the built in interfaces included in Java 8 – [java.util.function package](http://docs.oracle.com/javase/8/docs/api/java/util/function/package-summary.html)

|Functional interface|Function descriptor|Primitive specializations|
|:--------------------------|:------------------------|:------------------------|
|[Predicate\<T\>](http://docs.oracle.com/javase/8/docs/api/java/util/function/Predicate.html)|T -> boolean|IntPredicate, LongPredicate, DoublePredicate|
|[Consumer\<T\>](http://docs.oracle.com/javase/8/docs/api/java/util/function/Consumer.html)|T -> void|IntConsumer, LongConsumer, DoubleConsumer|
|[Function\<T, R\>](http://docs.oracle.com/javase/8/docs/api/java/util/function/Function.html)|T -> R|IntFunction<R>, IntToDoubleFunction, IntToLongFunction, LongFunction<R>, LongToDoubleFunction, LongToIntFunction, DoubleFunction<R>, ToIntFunction<T>, ToDoubleFunction<T>, ToLongFunction<T>|
|[Supplier\<T\>](http://docs.oracle.com/javase/8/docs/api/java/util/function/Function.html)|() -> T|BooleanSupplier, IntSupplier, LongSupplier, DoubleSupplier|
|[UnaryOperator\<T\>](http://docs.oracle.com/javase/8/docs/api/java/util/function/UnaryOperator.html)|T -> T|IntUnaryOperator, LongUnaryOperator, DoubleUnaryOperator|
|[BinaryOperator\<T\>](http://docs.oracle.com/javase/8/docs/api/java/util/function/BinaryOperator.html)|(T, T) -> T|IntBinaryOperator, LongBinaryOperator, DoubleBinaryOperator|

- 2.2. Develop code that uses [Function](http://docs.oracle.com/javase/8/docs/api/java/util/function/Function.html) interface: [FunctionInterfaceExample.java](https://github.com/igor-baiborodine/java-various-examples/blob/master/ocp-upgrade-java8/src/main/java/com/kiroule/ocpupgradejava8/topic2_2/FunctionInterfaceExample.java)

- 2.3. Develop code that uses [Consumer](http://docs.oracle.com/javase/8/docs/api/java/util/function/Consumer.html) interface: [ConsumerInterfaceExample.java](https://github.com/igor-baiborodine/java-various-examples/blob/master/ocp-upgrade-java8/src/main/java/com/kiroule/ocpupgradejava8/topic2_3/ConsumerInterfaceExample.java)

- 2.4. Develop code that uses [Supplier](https://github.com/igor-baiborodine/java-various-examples/blob/master/ocp-upgrade-java8/src/main/java/com/kiroule/ocpupgradejava8/topic2_4/SupplierInterfaceExample.java) interface: [SupplierInterfaceExample.java](https://github.com/igor-baiborodine/java-various-examples/blob/master/ocp-upgrade-java8/src/main/java/com/kiroule/ocpupgradejava8/topic2_4/SupplierInterfaceExample.java)

- 2.5. Develop code that uses [UnaryOperator](http://docs.oracle.com/javase/8/docs/api/java/util/function/UnaryOperator.html) interface: [UnaryOperatorInterfaceExample.java](https://github.com/igor-baiborodine/java-various-examples/blob/master/ocp-upgrade-java8/src/main/java/com/kiroule/ocpupgradejava8/topic2_5/UnaryOperatorInterfaceExample.java)

- 2.6. Develop code that uses [Predicate](http://docs.oracle.com/javase/8/docs/api/java/util/function/Predicate.html) interface: [PredicateInterfaceExample.java](https://github.com/igor-baiborodine/java-various-examples/blob/master/ocp-upgrade-java8/src/main/java/com/kiroule/ocpupgradejava8/topic2_6/PredicateInterfaceExample.java)

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
