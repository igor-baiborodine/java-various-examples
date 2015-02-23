package com.kiroule.example.springjpahsqldb.domain;

import com.kiroule.example.springjpahsqldb.domain.builder.BookBuilder;

import com.google.common.testing.EqualsTester;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

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
  public void before() {

    a1 = new BookBuilder("AAA").build();
    a2 = new BookBuilder("AAA").build();
    a3 = new BookBuilder("AAA").build();
    b = new BookBuilder("BBB").build();
    c = new BookBuilder("CCC").build();
    d = new BookBuilder("DDD").build();
  }

  @After
  public void after() {

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

    assertThat(b.compareTo(b), is(0));
    assertThat(b.compareTo(c), is(-1));
    assertThat(c.compareTo(b), is(1));
  }

  @Test
  public void compareTo_shouldSortInNaturalOrder() {

    List<Book> books = Arrays.asList(c, d, b);
    Collections.sort(books);

    assertThat(books.get(0), is(b));
    assertThat(books.get(1), is(c));
    assertThat(books.get(2), is(d));
  }
}
