package com.kiroule.example.springjpahsqldb.persistence;

import javax.persistence.EntityTransaction;

/**
 * @author Igor Baiborodine
 */
@FunctionalInterface
public interface TransactionTemplate {

  void body();

  default void execute(EntityTransaction entityTransaction) {
    try {
      entityTransaction.begin();
      body();
      entityTransaction.commit();
    } catch (Throwable t) {
      entityTransaction.rollback();
      throw t;
    }
  }

}
