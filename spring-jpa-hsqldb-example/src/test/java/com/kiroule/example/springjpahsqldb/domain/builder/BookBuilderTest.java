package com.kiroule.example.springjpahsqldb.domain.builder;

import com.kiroule.example.springjpahsqldb.domain.Book;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * JUnit-based unit tests for the {@link BookBuilder} class.
 *
 * @author Igor Baiborodine
 */
public class BookBuilderTest {

  private BookBuilder builder;
  private static final String ISBN = "978-0-321-35668-0";
  private static final String AUTHOR = "Joshua Bloch";
  private static final String TITLE = "Effective Java";
  private static final BigDecimal PRICE = new BigDecimal("50.0");

  @Before
  public void setUp() {
    builder = new BookBuilder(ISBN);
  }

  @After
  public void tearDown() {
    builder = null;
  }

  @Test(expected = NullPointerException.class)
  public void build_shouldThrowNpeForNullIsbnArgument() {
    new BookBuilder(null).build();
  }

  @Test
  public void build_shouldInitIsbnField() {
    Book book = builder.build();
    assertThat(book.getIsbn(), is(ISBN));
  }

  @Test
  public void build_shouldInitAuthorField() {
    Book book = builder.author(AUTHOR).build();
    assertThat(book.getAuthor(), is(AUTHOR));
  }

  @Test
  public void build_shouldInitTitleField() {
    Book book = builder.title(TITLE).build();
    assertThat(book.getTitle(), is(TITLE));
  }

  @Test
  public void build_shouldInitPriceField() {
    Book book = builder.price(PRICE).build();
    assertThat(book.getPrice(), is(PRICE));
  }

  @Test
  public void build_shouldNotInitBookIdField() {
    Book book = builder.build();
    assertThat(book.getBookId(), nullValue());
  }

  @Test
  public void build_shouldNotInitVersionField() {
    Book book = builder.build();
    assertThat(book.getVersion(), nullValue());
  }
}
