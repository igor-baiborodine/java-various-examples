package com.kiroule.ocpupgradejava8.topic1_3;

/**
 * @author Igor Baiborodine
 */
public class RefactoringCodeExample {

  public static void process(HelloWorld hw) {
    hw.printGreeting(2);
  }

  public static void main(String... args) {

    // anonymous class
    process(new HelloWorld() {
      @Override
      public void printGreeting(int count) {
        for (int i = 0; i < count; i++) {
          System.out.println("ANONYMOUS: Hello World " + i);
        }
      }
    });

    // refactor to lambda expression
    process((int count) -> {
      for (int i = 0; i < count; i++) {
        System.out.println("LAMBDA: Hello World " + i);
      }
    });
  }
}

@FunctionalInterface
interface HelloWorld {

  void printGreeting(int count);
}
