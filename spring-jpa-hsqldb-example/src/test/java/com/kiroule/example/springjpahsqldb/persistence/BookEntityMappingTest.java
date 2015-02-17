package com.kiroule.example.springjpahsqldb.persistence;

import com.kiroule.example.springjpahsqldb.domain.Book;
import com.kiroule.example.springjpahsqldb.domain.builder.BookBuilder;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * JUnit entity mapping test for the {@link Book} class.
 *
 * @author Igor Baiborodine
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:/META-INF/spring-config.xml")
public class BookEntityMappingTest {

  @Autowired
  private EntityManagerFactory emf;
  private EntityManager em;

  private static final String ISBN = "978-0-321-35668-0";
  private static final String AUTHOR = "Joshua Bloch";
  private static final String TITLE = "Effective Java";
  private static final BigDecimal PRICE = new BigDecimal("50.0");

  private abstract class TransactionTemplate<T> {

    protected EntityManager entityManager;
    protected T entity;

    public TransactionTemplate(EntityManager em) {
      this(em, null);
    }

    public TransactionTemplate(EntityManager em, T e) {
      entityManager = em;
      entity = e;
    }

    public void execute() throws Throwable {
      try {
        entityManager.getTransaction().begin();
        body();
        entityManager.getTransaction().commit();
      } catch (Throwable t) {
        entityManager.getTransaction().rollback();
        throw t;
      }
    }

    public abstract void body();
  }

  @Before
  public void before() throws Throwable {
    em = emf.createEntityManager();

    new TransactionTemplate<Book>(em) {
      @Override
      public void body() {
        entityManager.createQuery("delete from Book").executeUpdate();
      }
    }.execute();
  }

  @After
  public void after() {
    if (em != null) {
      em.close();
      em = null;
    }
  }

  @Test(expected = PersistenceException.class)
  public void entityMapping_shouldThrowPersistenceExceptionOnIsbnValueWithLengthGreaterThanMax()
      throws Throwable {
    final Book book = new BookBuilder(ISBN + "length is over the limit").build();
    new TransactionTemplate<Book>(em, book) {
      @Override
      public void body() {
        entityManager.persist(entity);
      }
    }.execute();
  }

  @Test(expected = PersistenceException.class)
  public void entityMapping_shouldThrowPersistenceExceptionOnNullIsbnValue() throws Throwable {
    final Book book = new Book();
    new TransactionTemplate<Book>(em, book) {
      @Override
      public void body() {
        entityManager.persist(entity);
      }
    }.execute();
  }

  @Test(expected = PersistenceException.class)
  public void entityMapping_shouldThrowPersistenceExceptionOnNonUniqueIsbnValue() throws Throwable {
    final Book book = new BookBuilder(ISBN).build();
    new TransactionTemplate<Book>(em, book) {
      @Override
      public void body() {
        entityManager.persist(entity);
      }
    }.execute();

    final Book bookWithNonUniqueIsbn = new BookBuilder(ISBN).build();
    new TransactionTemplate<Book>(em, bookWithNonUniqueIsbn) {
      @Override
      public void body() {
        entityManager.persist(entity);
      }
    }.execute();
  }

  @Test
  public void entityMapping_shouldPersistBookEntityWithNonNullValueForOptionalFields()
      throws Throwable {
    final Book book = new BookBuilder(ISBN)
        .author(AUTHOR)
        .title(TITLE)
        .price(PRICE).build();

    new TransactionTemplate<Book>(em, book) {
      @Override
      public void body() {
        entityManager.persist(entity);
      }
    }.execute();
    assertNotNull(book.getBookId()); // managed
    assertNotNull(book.getVersion());

    em.clear();
    assertFalse(em.contains(book)); // detached

    Book foundBook = em.find(Book.class, book.getBookId());
    assertNotNull(foundBook);
    assertEquals(book.getVersion(), foundBook.getVersion());
    assertEquals(ISBN, foundBook.getIsbn());
    assertEquals(AUTHOR, foundBook.getAuthor());
    assertEquals(TITLE, foundBook.getTitle());
    assertEquals(0, PRICE.compareTo(foundBook.getPrice()));
  }

  @Test
  public void entityMapping_shouldPersistBookEntityWithNullValueForOptionalFields()
      throws Throwable {
    Book book = new BookBuilder(ISBN).build();

    new TransactionTemplate<Book>(em, book) {
      @Override
      public void body() {
        entityManager.persist(entity);
      }
    }.execute();
    assertNotNull(book.getBookId());
    assertNotNull(book.getVersion());

    em.clear();
    assertFalse(em.contains(book));

    Book foundBook = em.find(Book.class, book.getBookId());
    assertNotNull(foundBook);
    assertEquals(book.getVersion(), foundBook.getVersion());
    assertEquals(ISBN, foundBook.getIsbn());
    assertNull(foundBook.getAuthor());
    assertNull(foundBook.getTitle());
    assertNull(foundBook.getPrice());
  }

  @Test
  public void entityMapping_shouldUpdateBookEntityInDataBase() throws Throwable {
    Book book = new BookBuilder(ISBN).build();

    new TransactionTemplate<Book>(em, book) {
      @Override
      public void body() {
        entityManager.persist(entity);
      }
    }.execute();
    assertNotNull(book.getBookId());
    assertNotNull(book.getVersion());
    em.clear();
    assertFalse(em.contains(book)); // book is no longer managed

    Book foundBook = em.find(Book.class, book.getBookId());
    assertNotNull(foundBook); // foundBook is managed
    assertEquals(Long.valueOf("0"), foundBook.getVersion());
    assertEquals(book.getVersion(), foundBook.getVersion());
    assertEquals(book.getIsbn(), foundBook.getIsbn());
    assertNull(foundBook.getAuthor());
    assertNull(foundBook.getTitle());
    assertNull(foundBook.getPrice());

    foundBook.setAuthor(AUTHOR);
    foundBook.setTitle(TITLE);
    foundBook.setPrice(PRICE);

    new TransactionTemplate<Book>(em, foundBook) {
      @Override
      public void body() {
        entityManager.merge(entity);
      }
    }.execute();
    em.clear();
    assertFalse(em.contains(foundBook)); // foundBook is no longer managed

    Book updatedBook = em.find(Book.class, foundBook.getBookId());
    assertNotNull(updatedBook); // updatedBook is managed
    assertEquals(Long.valueOf("1"), updatedBook.getVersion());
    assertEquals(foundBook.getVersion(), updatedBook.getVersion());
    assertEquals(ISBN, updatedBook.getIsbn());
    assertEquals(AUTHOR, updatedBook.getAuthor());
    assertEquals(TITLE, updatedBook.getTitle());
    assertEquals(0, foundBook.getPrice().compareTo(updatedBook.getPrice()));
  }

  @Test
  public void entityMapping_shouldRemoveBookEntityFromPersistenceContextAndDeleteFromDatabase()
      throws Throwable {
    Book book = new BookBuilder(ISBN).build();

    new TransactionTemplate<Book>(em, book) {
      @Override
      public void body() {
        entityManager.persist(entity);
      }
    }.execute();
    assertTrue(em.contains(book)); // managed entity

    new TransactionTemplate<Book>(em, book) {
      @Override
      public void body() {
        entityManager.remove(entity);
      }
    }.execute();
    assertFalse(em.contains(book));

    Book foundBook = em.find(Book.class, book.getBookId());
    assertNull(foundBook);
  }
}
