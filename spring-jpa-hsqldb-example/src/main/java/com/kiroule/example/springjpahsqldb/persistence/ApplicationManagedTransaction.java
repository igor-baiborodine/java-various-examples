package com.kiroule.example.springjpahsqldb.persistence;

import javax.persistence.EntityManager;

/**
 * @author Igor Baiborodine
 */
@FunctionalInterface
public interface ApplicationManagedTransaction {

  void body();

  default void execute(EntityManager entityManager) {
    try {
      entityManager.getTransaction().begin();
      body();
      entityManager.getTransaction().commit();
    } catch (Throwable t) {
      entityManager.getTransaction().rollback();
      throw t;
    }
  }

}
