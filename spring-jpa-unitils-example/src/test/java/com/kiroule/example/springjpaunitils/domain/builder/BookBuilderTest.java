package com.kiroule.example.springjpaunitils.domain.builder;

import com.kiroule.example.springjpaunitils.domain.Book;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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
    public void  build_shouldInitPriceField() {
        Book b = builder.price(PRICE).build();
        assertEquals(PRICE, b.getPrice());
    }

    @Test
    public void build_shouldNotInitBookIdField() {
        Book b = builder.build();
        assertNull(b.getBookId());
    }

    @Test
    public void build_shouldNotInitVersionField() {
        Book b = builder.build();
        assertNull(b.getVersion());
    }
}
