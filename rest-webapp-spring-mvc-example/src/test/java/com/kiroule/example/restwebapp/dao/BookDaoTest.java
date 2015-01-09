package com.kiroule.example.restwebapp.dao;

import com.google.common.base.Optional;
import com.google.common.collect.Sets;
import com.kiroule.example.restwebapp.domain.Book;
import com.kiroule.example.restwebapp.domain.builder.BookBuilder;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static org.junit.Assert.*;
import static org.springframework.util.ReflectionUtils.*;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

/**
 * JUnit based unit tests for the {@link BookDao} class.
 *
 * @author Igor Baiborodine
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:/META-INF/application-context.xml")
public class BookDaoTest {

    private static final String ISBN = "978-0-321-35668-0";
    private static final String AUTHOR = "Joshua Bloch";
    private static final String TITLE = "Effective Java";
    private static final BigDecimal PRICE = new BigDecimal("50.0");
    private static final Set<String> CATEGORIES =
            Sets.newHashSet("Software", "Java", "Object-Oriented Design");
    private static final Field BOOKS_FIELD = findField(BookDao.class, "books");

    @Autowired
    private BookDao bookDao;
    private Book book;

    private void setBooksFieldMap() {
        Map<String, Book> books = new ConcurrentHashMap<String, Book>();
        Book copy = new BookBuilder(book.getIsbn()).build(book);
        books.put(copy.getIsbn(), copy);
        setField(BOOKS_FIELD, bookDao, books);
        assertEquals(1, ((ConcurrentMap<String, Book>) getField(BOOKS_FIELD, bookDao)).size());
    }

    private void clearBooksFieldMap() {
        setField(BOOKS_FIELD, bookDao, new ConcurrentHashMap<String, Book>());
        assertTrue(((ConcurrentMap<String, Book>) getField(BOOKS_FIELD, bookDao)).isEmpty());
    }

    @BeforeClass
    public static void setUpClass() {
        assertNotNull(BOOKS_FIELD);
        makeAccessible(BOOKS_FIELD);
    }

    @Before
    public void setUp() {
        book = new BookBuilder(ISBN)
                .author(AUTHOR)
                .title(TITLE)
                .price(PRICE)
                .categories(CATEGORIES).build();
    }

    @After
    public void tearDown() {
        clearBooksFieldMap();
        book = null;
    }

    @Test(expected = NullPointerException.class)
    public void create_shouldThrowNpeForNullBookArgument() {
        bookDao.create(null);
    }

    @Test
    public void create_shouldReturnOptionalPresentForNonExistingIsbn() {
        Optional<Book> optional = bookDao.create(book);
        assertTrue(optional.isPresent());
        assertSame(book, optional.get());

        Book createdBook = ((Map<String, Book>) getField(BOOKS_FIELD, bookDao)).get(book.getIsbn());
        assertNotSame(book, createdBook);
        assertReflectionEquals(book, createdBook);
    }

    @Test
    public void create_shouldReturnOptionalAbsentForExistingIsbn() {
        setBooksFieldMap();
        Optional<Book> optional = bookDao.create(book);
        assertFalse(optional.isPresent());
    }

    @Test
    public void readAll_shouldReturnAllBooks() {
        setBooksFieldMap();
        List<Book> allBooks = bookDao.readAll();
        assertEquals(1, allBooks.size());
        assertTrue(allBooks.contains(book));
        assertNotSame(book, allBooks.get(0));
        assertReflectionEquals(book, allBooks.get(0));

        clearBooksFieldMap();
        allBooks = bookDao.readAll();
        assertTrue(allBooks.isEmpty());
    }

    @Test(expected = NullPointerException.class)
    public void readByIsbn_shouldThrowNpeForNullIsbnArgument() {
        bookDao.readByIsbn(null);
    }

    @Test
    public void readByIsbn_shouldReturnOptionalPresentForExistingIsbn() {
        setBooksFieldMap();
        Optional<Book> optional = bookDao.readByIsbn(ISBN);
        assertTrue(optional.isPresent());
        assertEquals(ISBN, optional.get().getIsbn());
    }

    @Test
    public void readByIsbn_shouldReturnOptionalAbsentForNonExistingIsbn() {
        Optional<Book> optional = bookDao.readByIsbn(ISBN);
        assertFalse(optional.isPresent());
    }

    @Test(expected = NullPointerException.class)
    public void update_shouldThrowNpeForNullIsbnArgument() {
        bookDao.update(null);
    }

    @Test
    public void update_shouldReturnOptionalPresentForExistingIsbn() {
        setBooksFieldMap();
        Optional<Book> optional = bookDao.update(book);
        assertTrue(optional.isPresent());
        assertSame(book, optional.get());

        Book updatedBook = ((Map<String, Book>) getField(BOOKS_FIELD, bookDao)).get(book.getIsbn());
        assertNotSame(book, updatedBook);
        assertReflectionEquals(book, updatedBook);
    }

    @Test
    public void update_shouldReturnOptionalAbsentForNonExistingIsbn() {
        Optional<Book> optional = bookDao.update(book);
        assertFalse(optional.isPresent());
    }

    @Test(expected = NullPointerException.class)
    public void delete_shouldThrowNpeForNullIsbnArgument() {
        bookDao.delete(null);
    }

    @Test
    public void delete_shouldDeleteForExistingIsbn() {
        setBooksFieldMap();
        assertTrue(bookDao.delete(book));
    }

    @Test
    public void delete_shouldNotDeleteForNonExistingIsbn() {
        assertFalse(bookDao.delete(book));
    }
}


