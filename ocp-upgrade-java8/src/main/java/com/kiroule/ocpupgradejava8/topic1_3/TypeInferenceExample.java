package com.kiroule.ocpupgradejava8.topic1_3;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Igor Baiborodine
 */
public class TypeInferenceExample {

  public static void main(String... args) {

    Foo<Bar> foo = new Foo<>();

    foo.<String>apply(bar -> bar::setBar);
    // We omit the type String and Java compiler automatically infers
    // (from the method's argument) that the type parameter is String
    foo.apply(bar -> bar::setBar);
  }
}

class Foo<T> {

  <U> void apply(Function<T, Consumer<U>> bar) {
  }
}

class Bar {

  void setBar(String bar) {
  }
}
