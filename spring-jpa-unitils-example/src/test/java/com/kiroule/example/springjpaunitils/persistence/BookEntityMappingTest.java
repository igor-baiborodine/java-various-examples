package com.kiroule.example.springjpaunitils.persistence;

import com.kiroule.example.springjpaunitils.domain.Book;
import com.kiroule.example.springjpaunitils.domain.builder.BookBuilder;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.database.annotations.Transactional;
import org.unitils.database.util.TransactionMode;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.orm.jpa.annotation.JpaEntityManagerFactory;
import org.unitils.spring.annotation.SpringApplicationContext;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import static org.junit.Assert.assertNotNull;
import static com.kiroule.example.springjpaunitils.util.TestHelper.*;

/**
 * JUnit/Unitils-based entity mapping tests for the {@link Book} class.
 *
 * @author Igor Baiborodine
 *
 */
@RunWith(UnitilsJUnit4TestClassRunner.class)
@SpringApplicationContext({ "classpath:persistence-context-test.xml" })
@JpaEntityManagerFactory(persistenceUnit = "spring-jpa-unitils-pu")
public class BookEntityMappingTest {

    @PersistenceContext
    private EntityManager em;

    @After
    public void tearDown() {
        if (em != null) {
            em.clear();
        }
    }

    @Test(expected = PersistenceException.class)
    @Transactional(TransactionMode.ROLLBACK)
    @DataSet(value = "/datasets/empty.xml")
    public void entityMapping_shouldThrowPersistenceExceptionOnIsbnValueWithLengthGreaterThanMax() {
        Book book = new BookBuilder(ISBN + "length is over the limit").build();
        em.persist(book);
    }

    @Test(expected = PersistenceException.class)
    @Transactional(TransactionMode.ROLLBACK)
    @DataSet(value = "/datasets/empty.xml")
    public void entityMapping_shouldThrowPersistenceExceptionOnNullIsbnValue() {
        Book book = new Book();
        em.persist(book);
    }

    @Test(expected = PersistenceException.class)
    @DataSet(value = "/datasets/book.xml")
    @Transactional(TransactionMode.ROLLBACK)
    public void entityMapping_shouldThrowPersistenceExceptionOnDuplicateBook() {
        Book book = em.find(Book.class, ID);
        assertNotNull(book);
        em.persist(getNewBook());
    }
}