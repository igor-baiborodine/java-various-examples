package com.kiroule.example.restwebapp.resources;

import com.google.common.base.Optional;
import com.google.common.collect.Sets;
import com.kiroule.example.restwebapp.dao.BookDao;
import com.kiroule.example.restwebapp.domain.Book;
import com.kiroule.example.restwebapp.domain.BooksList;
import com.kiroule.example.restwebapp.domain.builder.BookBuilder;
import com.kiroule.example.restwebapp.exceptions.EntityExistsException;
import com.kiroule.example.restwebapp.exceptions.EntityNotFoundException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

/**
 * JUnit/Mockito based unit tests for the {@link BookResource} class.
 *
 * @author Igor Baiborodine
 */
public class BookResourceTest {

    private static final String ISBN = "978-0-321-35668-0";
    private static final String AUTHOR = "Joshua Bloch";
    private static final String TITLE = "Effective Java";
    private static final BigDecimal PRICE = new BigDecimal("50.0");
    private static final Set<String> CATEGORIES =
            Sets.newHashSet("Software", "Java", "Object-Oriented Design");
    private BookDao bookDaoMock;
    private Book book;

    @Before
    public void setUp() {
        bookDaoMock = Mockito.mock(BookDao.class);
        book = new BookBuilder(ISBN)
                .author(AUTHOR)
                .title(TITLE)
                .price(PRICE)
                .categories(CATEGORIES).build();
    }

    @After
    public void tearDown() {
        bookDaoMock = null;
        book = null;
    }

    @Test
    public void create_shouldReturnResponseWithNoContentStatus() { // 204
        Mockito.when(bookDaoMock.create(book)).thenReturn(Optional.of(book));
        BookResource resource = new BookResource(bookDaoMock);
        ResponseEntity<String> response = resource.create(book);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test(expected = EntityExistsException.class)
    public void create_shouldThrowEntityExistsException() { // 400
        Optional<Book> optionalAbsent = Optional.absent();
        Mockito.when(bookDaoMock.create(book)).thenReturn(optionalAbsent);
        BookResource resource = new BookResource(bookDaoMock);
        ResponseEntity<String> response = resource.create(book);
    }

    @Test
    public void readAll_shouldReturnResponseWithOkStatus() { // 200
        Mockito.when(bookDaoMock.readAll()).thenReturn(Arrays.asList(book));
        BookResource resource = new BookResource(bookDaoMock);
        ResponseEntity<BooksList> response = resource.readAll();
        assertEquals(HttpStatus.OK, response.getStatusCode());

        BooksList booksList = response.getBody();
        assertEquals(1, booksList.getBooks().size());
        assertReflectionEquals(book, booksList.getBooks().get(0));
    }

    @Test
    public void readByIsbn_shouldReturnResponseWithOkStatus() { // 200
        Mockito.when(bookDaoMock.readByIsbn(ISBN)).thenReturn(Optional.of(book));
        BookResource resource = new BookResource(bookDaoMock);
        ResponseEntity<Book> response = resource.readByIsbn(ISBN);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(book, response.getBody());
    }

    @Test(expected = EntityNotFoundException.class)
    public void readByIsbn_shouldThrowEntityNotFoundException()  {
        Optional<Book> optionalAbsent = Optional.absent();
        Mockito.when(bookDaoMock.readByIsbn(ISBN)).thenReturn(optionalAbsent);
        BookResource resource = new BookResource(bookDaoMock);
        ResponseEntity<Book> response = resource.readByIsbn(ISBN);
    }

    @Test
    public void update_shouldReturnResponseWithNoContentStatus() { // 204
        Mockito.when(bookDaoMock.update(book)).thenReturn(Optional.of(book));
        BookResource resource = new BookResource(bookDaoMock);
        ResponseEntity<String> response = resource.update(book);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test(expected = EntityNotFoundException.class)
    public void update_shouldThrowEntityNotFoundException() {
        Optional<Book> optionalAbsent = Optional.absent();
        Mockito.when(bookDaoMock.update(book)).thenReturn(optionalAbsent);
        BookResource resource = new BookResource(bookDaoMock);
        ResponseEntity<String> response = resource.update(book);
    }

    @Test
    public void delete_shouldReturnResponseWithNoContentStatus() { // 204
        Mockito.when(bookDaoMock.delete(book)).thenReturn(true);
        BookResource resource = new BookResource(bookDaoMock);
        ResponseEntity<String> response = resource.delete(ISBN);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test(expected = EntityNotFoundException.class)
    public void delete_shouldThrowNotFoundException() {
        Mockito.when(bookDaoMock.delete(book)).thenReturn(false);
        BookResource resource = new BookResource(bookDaoMock);
        ResponseEntity<String> response = resource.delete(ISBN);
    }
}
