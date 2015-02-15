package com.kiroule.ocpupgradejava8.topic1_1;

/**
 * @author Igor Baiborodine
 */
class InnerClassExample {

  private String greeting = "Hello World!";

  public void printGreeting() {
    System.out.println("OUTER: " + greeting);
  }

  class HelloWorld {

    public void printGreeting() {
      System.out.println("INNER: " + greeting);
    }
  }

  public static void main(String... args) {
    InnerClassExample innerClassExample = new InnerClassExample();
    innerClassExample.printGreeting();

    HelloWorld helloWorld = innerClassExample.new HelloWorld();
    helloWorld.printGreeting();
  }
}
