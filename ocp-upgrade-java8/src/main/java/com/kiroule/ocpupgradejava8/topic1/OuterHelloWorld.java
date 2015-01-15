package com.kiroule.ocpupgradejava8.topic1;

/**
 * @author Igor Baiborodine
 */
class OuterHelloWorld {

    public void printGreeting() {
        System.out.println("OUTER: Hello World!");
    }
    class InnerHelloWorld{
        public void printGreeting() {
            System.out.println("INNER: Hello World!");
        }
    }

    public static void main(String... args) {
        OuterHelloWorld outerHelloWorld = new OuterHelloWorld();
        outerHelloWorld.printGreeting();

        OuterHelloWorld.InnerHelloWorld innerHelloWorld = outerHelloWorld.new InnerHelloWorld();
        innerHelloWorld.printGreeting();
    }
}
