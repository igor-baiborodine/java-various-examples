package com.kiroule.ocpupgradejava8.topic1_2;

/**
 * @author Igor Baiborodine
 */
public class FunctionalInterfaceExample {

    public static void process(HelloWorld hw) {
        hw.printGreeting();
    }

    public static void main(String... args) {

        HelloWorld hw1 = () -> System.out.println("Hello World 1!");

        HelloWorld hw2 = new HelloWorld() {
            @Override
            public void printGreeting() {
                System.out.println("Hello World 2!");
            }
        };

        process(hw1);
        process(hw2);
        process(() -> System.out.println("Hello World 3!"));
    }
}

@FunctionalInterface
interface HelloWorld {
    void printGreeting();
}
