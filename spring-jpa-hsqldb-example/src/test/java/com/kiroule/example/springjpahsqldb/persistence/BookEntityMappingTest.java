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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

/**
 * JUnit entity mapping test for the {@link Book} class.
 *
 * @author Igor Baiborodine
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:/META-INF/spring-config.xml")
public class BookEntityMappingTest {

  private static final String ISBN = "978-0-321-35668-0";
  private static final String AUTHOR = "Joshua Bloch";
  private static final String TITLE = "Effective Java";
  private static final BigDecimal PRICE = new BigDecimal("50.0");

  @Autowired
  private EntityManagerFactory entityManagerFactory;
  private EntityManager entityManager;

  @Before
  public void before() throws Throwable {
    entityManager = entityManagerFactory.createEntityManager();
    TransactionTemplate template =
        () -> entityManager.createQuery("delete from Book").executeUpdate();
    template.execute(entityManager.getTransaction());
  }

  @After
  public void after() {
    if (entityManager != null) {
      entityManager.close();
      entityManager = null;
    }
  }

  @Test(expected = PersistenceException.class)
  public void entityMapping_shouldThrowPersistenceExceptionOnIsbnValueWithLengthGreaterThanMax()
      throws Throwable {
    Book book = new BookBuilder(ISBN + "length is over the limit").build();
    getPersistEntityTemplate(book).execute(entityManager.getTransaction());
  }

  @Test(expected = PersistenceException.class)
  public void entityMapping_shouldThrowPersistenceExceptionOnNullIsbnValue() throws Throwable {
    Book book = new Book();
    getPersistEntityTemplate(book).execute(entityManager.getTransaction());
  }

  @Test(expected = PersistenceException.class)
  public void entityMapping_shouldThrowPersistenceExceptionOnNonUniqueIsbnValue() throws Throwable {
    Book book = new BookBuilder(ISBN).build();
    getPersistEntityTemplate(book).execute(entityManager.getTransaction());

    Book bookWithExistingIsbn = new BookBuilder(ISBN).build();
    getPersistEntityTemplate(bookWithExistingIsbn).execute(entityManager.getTransaction());
  }

  @Test
  public void entityMapping_shouldPersistBookEntityWithNonNullValueForOptionalFields()
      throws Throwable {
    Book book = new BookBuilder(ISBN)
        .author(AUTHOR)
        .title(TITLE)
        .price(PRICE).build();

    getPersistEntityTemplate(book).execute(entityManager.getTransaction());
    assertThat(book.getBookId(), notNullValue()); // managed
    assertThat(book.getVersion(), notNullValue());

    entityManager.clear();
    assertThat(entityManager.contains(book), is(false)); // detached

    Book foundBook = entityManager.find(Book.class, book.getBookId());
    assertThat(foundBook, notNullValue());
    assertThat(foundBook.getVersion(), is(book.getVersion()));
    assertThat(foundBook.getIsbn(), is(ISBN));
    assertThat(foundBook.getAuthor(), is(AUTHOR));
    assertThat(foundBook.getTitle(), is(TITLE));
    assertThat(PRICE.compareTo(foundBook.getPrice()), is(0));
  }

  @Test
  public void entityMapping_shouldPersistBookEntityWithNullValueForOptionalFields()
      throws Throwable {
    Book book = new BookBuilder(ISBN).build();
    getPersistEntityTemplate(book).execute(entityManager.getTransaction());
    assertThat(book.getBookId(), notNullValue());
    assertThat(book.getVersion(), notNullValue());

    entityManager.clear();
    assertFalse(entityManager.contains(book));

    Book foundBook = entityManager.find(Book.class, book.getBookId());
    assertThat(foundBook, notNullValue());
    assertThat(foundBook.getVersion(), is(book.getVersion()));
    assertThat(foundBook.getIsbn(), is(ISBN));
    assertThat(foundBook.getAuthor(), nullValue());
    assertThat(foundBook.getTitle(), nullValue());
    assertThat(foundBook.getPrice(), nullValue());
  }

  @Test
  public void entityMapping_shouldUpdateBookEntityInDataBase() throws Throwable {
    Book book = new BookBuilder(ISBN).build();
    getPersistEntityTemplate(book).execute(entityManager.getTransaction());
    assertThat(book.getBookId(), notNullValue());
    assertThat(book.getVersion(), notNullValue());

    entityManager.clear();
    assertFalse(entityManager.contains(book)); // book is no longer managed

    Book foundBook = entityManager.find(Book.class, book.getBookId());
    assertThat(foundBook, notNullValue()); // foundBook is managed
    assertThat(foundBook.getVersion(), is(0L));
    assertThat(foundBook.getVersion(), is(book.getVersion()));
    assertThat(foundBook.getIsbn(), is(book.getIsbn()));
    assertNull(foundBook.getAuthor());
    assertNull(foundBook.getTitle());
    assertNull(foundBook.getPrice());

    foundBook.setAuthor(AUTHOR);
    foundBook.setTitle(TITLE);
    foundBook.setPrice(PRICE);

    TransactionTemplate template = () -> entityManager.merge(foundBook);
    template.execute(entityManager.getTransaction());
    entityManager.clear();
    assertThat(entityManager.contains(foundBook), is(false)); // foundBook is no longer managed

    Book updatedBook = entityManager.find(Book.class, foundBook.getBookId());
    assertThat(updatedBook, notNullValue()); // updatedBook is managed
    assertThat(updatedBook.getVersion(), is(1L));
    assertThat(updatedBook.getVersion(), is(foundBook.getVersion()));
    assertThat(updatedBook.getIsbn(), is(ISBN));
    assertThat(updatedBook.getAuthor(), is(AUTHOR));
    assertThat(updatedBook.getTitle(), is(TITLE));
    assertThat(foundBook.getPrice().compareTo(updatedBook.getPrice()), is(0));
  }

  @Test
  public void entityMapping_shouldRemoveBookEntityFromPersistenceContextAndDeleteFromDatabase()
      throws Throwable {
    Book book = new BookBuilder(ISBN).build();
    getPersistEntityTemplate(book).execute(entityManager.getTransaction());
    assertThat(entityManager.contains(book), is(true)); // managed entity

    TransactionTemplate template = () -> entityManager.remove(book);
    template.execute(entityManager.getTransaction());
    assertThat(entityManager.contains(book), is(false));

    Book foundBook = entityManager.find(Book.class, book.getBookId());
    assertThat(foundBook, nullValue());
  }

  private TransactionTemplate getPersistEntityTemplate(Book entity) {
    return () -> entityManager.persist(entity);
  }

}
