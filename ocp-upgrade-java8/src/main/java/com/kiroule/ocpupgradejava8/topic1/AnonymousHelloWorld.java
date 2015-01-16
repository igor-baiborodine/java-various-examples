package com.kiroule.ocpupgradejava8.topic1;

/**
 * @author Igor Baiborodine
 */
class AnonymousHelloWorld {

    abstract static class HelloWorld {
        public abstract void printGreeting();
    }

    public static void main(String... args) {

        HelloWorld helloWorld = new HelloWorld() {
            @Override
            public void printGreeting() {
                System.out.println("ANONYMOUS: Hello World!");
            }
        };
        helloWorld.printGreeting();
    }
}
