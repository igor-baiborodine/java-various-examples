package com.kiroule.ocpupgradejava8.topic1_1;

/**
 * @author Igor Baiborodine
 */
class LocalMethodClassExample {

  private String greeting = "Hello World!";

  public void printGreeting() {

    class HelloWorld {

      public void printGreeting() {
        System.out.println("LOCAL METHOD: " + greeting);
      }
    }
    HelloWorld helloWorld = new HelloWorld();
    helloWorld.printGreeting();
  }

  public static void main(String... args) {
    new LocalMethodClassExample().printGreeting();
  }
}
