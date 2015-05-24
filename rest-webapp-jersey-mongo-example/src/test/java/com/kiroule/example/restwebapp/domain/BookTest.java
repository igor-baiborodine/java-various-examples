package com.kiroule.example.restwebapp.domain;

import com.google.common.testing.EqualsTester;

import com.kiroule.example.restwebapp.domain.builder.BookBuilder;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * JUnit-based tests for the {@link Book} class.
 *
 * @author Igor Baiborodine
 */
public class BookTest {

  private Book a1;
  private Book a2;
  private Book a3;
  private Book b;
  private Book c;
  private Book d;

  @Before
  public void setUp() {
    a1 = new BookBuilder("AAA").build();
    a2 = new BookBuilder("AAA").build();
    a3 = new BookBuilder("AAA").build();
    b = new BookBuilder("BBB").build();
    c = new BookBuilder("CCC").build();
    d = new BookBuilder("DDD").build();
  }

  @After
  public void tearDown() {
    a1 = null;
    a2 = null;
    a3 = null;
    b = null;
    c = null;
    d = null;
  }

  @Test(expected = NullPointerException.class)
  public void setIsbn_shouldThrowNpeForNullArgument() {
    new Book().setIsbn(null);
  }

  @Test(expected = NullPointerException.class)
  public void setIsbn_shouldThrowNpeForEmptyStringArgument() {
    new Book().setIsbn("");
  }

  @Test
  public void setAuthor_shouldConvertEmptyStringArgumentToNull() {
    Book book = new Book();
    book.setAuthor("");
    assertNull(book.getAuthor());
  }

  @Test
  public void setTitle_shouldConvertEmptyStringArgumentToNull() {
    Book book = new Book();
    book.setTitle("");
    assertNull(book.getTitle());
  }

  @Test(expected = NullPointerException.class)
  public void setCategories_shouldThrowNpeForNullArgument() {
    new Book().setCategories(null);
  }

  @Test
  public void equals_shouldBeEqual() {
    new EqualsTester()
        .addEqualityGroup(a1, a2).testEquals();
    new EqualsTester()
        .addEqualityGroup(a2, a3).testEquals();
    new EqualsTester()
        .addEqualityGroup(a1, a3).testEquals();
    new EqualsTester()
        .addEqualityGroup(b, b)
        .addEqualityGroup(c, c)
        .addEqualityGroup(d, d).testEquals();
  }

  @Test
  public void compareToTest() {
    assertEquals(0, b.compareTo(b));
    assertEquals(-1, b.compareTo(c));
    assertEquals(1, c.compareTo(b));
  }

  @Test
  public void compareTo_shouldSortInNaturalOrder() {
    List<Book> books = new ArrayList<>(3);
    books.add(c);
    books.add(d);
    books.add(b);
    Collections.sort(books);

    assertEquals(b, books.get(0));
    assertEquals(c, books.get(1));
    assertEquals(d, books.get(2));
  }
}
