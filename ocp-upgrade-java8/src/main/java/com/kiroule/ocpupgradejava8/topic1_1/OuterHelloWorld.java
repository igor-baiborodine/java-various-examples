package com.kiroule.ocpupgradejava8.topic1_1;

/**
 * @author Igor Baiborodine
 */
class OuterHelloWorld {

    private String greeting = "Hello World!";

    public void printGreeting() {
        System.out.println("OUTER: " + greeting);
    }
    class InnerHelloWorld{
        public void printGreeting() {
            System.out.println("INNER: " + greeting);
        }
    }

    public static void main(String... args) {
        OuterHelloWorld outerHelloWorld = new OuterHelloWorld();
        outerHelloWorld.printGreeting();

        OuterHelloWorld.InnerHelloWorld innerHelloWorld = outerHelloWorld.new InnerHelloWorld();
        innerHelloWorld.printGreeting();
    }
}
