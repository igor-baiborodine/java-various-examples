package com.kiroule.example.restwebapp.domain.builder;

/**
 * @author Igor Baiborodine
 */
public interface DomainBuilder<T> {
  T build();

  T build(T t);
}
