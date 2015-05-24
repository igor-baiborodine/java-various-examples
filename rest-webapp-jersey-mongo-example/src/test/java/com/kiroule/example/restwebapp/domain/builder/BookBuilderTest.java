package com.kiroule.example.restwebapp.domain.builder;

import com.google.common.collect.Sets;

import com.kiroule.example.restwebapp.domain.Book;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

/**
 * JUnit based unit tests for the {@link BookBuilder} class.
 *
 * @author Igor Baiborodine
 */
public class BookBuilderTest {

  private static final String ISBN = "978-0-321-35668-0";
  private static final String AUTHOR = "Joshua Bloch";
  private static final String TITLE = "Effective Java";
  private static final BigDecimal PRICE = new BigDecimal("50.0");
  private static final Set<String> CATEGORIES =
      Sets.newHashSet("Software", "Java", "Object-Oriented Design");

  private BookBuilder builder;
  private Book bookToCopy;

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
    Book b = builder.build();
    assertEquals(ISBN, b.getIsbn());
  }

  @Test
  public void build_shouldInitAuthorField() {
    Book b = builder.author(AUTHOR).build();
    assertEquals(AUTHOR, b.getAuthor());
  }

  @Test
  public void build_shouldInitTitleField() {
    Book b = builder.title(TITLE).build();
    assertEquals(TITLE, b.getTitle());
  }

  @Test
  public void build_shouldInitPriceField() {
    Book b = builder.price(PRICE).build();
    assertEquals(PRICE, b.getPrice());
  }

  @Test
  public void build_shouldInitCategoriesField() {
    Book b = builder.categories(CATEGORIES).build();
    assertEquals(CATEGORIES, b.getCategories());
    assertNotSame(CATEGORIES, b.getCategories());
  }

  @Test
  public void build_shouldCopyAllFields() {
    Book bookToCopy = new Book();
    bookToCopy.setIsbn(ISBN);
    bookToCopy.setAuthor(AUTHOR);
    bookToCopy.setTitle(TITLE);
    bookToCopy.setPrice(PRICE);
    bookToCopy.setCategories(CATEGORIES);

    Book copy = new BookBuilder(bookToCopy.getIsbn()).build(bookToCopy);
    assertEquals(ISBN, copy.getIsbn());
    assertEquals(AUTHOR, copy.getAuthor());
    assertEquals(TITLE, copy.getTitle());
    assertEquals(PRICE, copy.getPrice());
    assertEquals(CATEGORIES, copy.getCategories());
  }
}
