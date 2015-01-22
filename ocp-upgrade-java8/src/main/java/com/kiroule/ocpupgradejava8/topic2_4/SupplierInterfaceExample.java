package com.kiroule.ocpupgradejava8.topic2_4;

import java.util.function.Supplier;

/**
 * @author Igor Baiborodine
 */
public class SupplierInterfaceExample {

    public static void printGreeting(Supplier<HelloWorld> supplier) {
        System.out.println(supplier.get().getGreeting());
    }

    public static void main(String... args) {

        Supplier<HelloWorld> supplier = HelloWorld::new;

        printGreeting(supplier);
        printGreeting(() -> new HelloWorld());
        printGreeting(new CustomGreetingSupplier());
    }
}

class CustomGreetingSupplier implements Supplier<HelloWorld> {
    @Override
    public HelloWorld get() {
        HelloWorld helloWorld = new HelloWorld();
        helloWorld.setGreeting("Custom Hello World!");
        return helloWorld;
    }
}

class HelloWorld {

    private String greeting = "Default Hello World!";

    public String getGreeting() { return greeting; }

    public void setGreeting(String greeting) { this.greeting = greeting; }
}
