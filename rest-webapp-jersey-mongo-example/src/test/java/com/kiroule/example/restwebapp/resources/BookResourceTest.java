package com.kiroule.example.restwebapp.resources;

import com.google.common.base.Optional;
import com.google.common.collect.Sets;
import com.kiroule.example.restwebapp.dao.BookDao;
import com.kiroule.example.restwebapp.domain.Book;
import com.kiroule.example.restwebapp.domain.builder.BookBuilder;
import com.sun.jersey.api.NotFoundException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static javax.ws.rs.core.Response.Status.OK;
import static org.junit.Assert.*;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.NO_CONTENT;
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
        Response response = resource.create(book);
        assertEquals(NO_CONTENT.getStatusCode(), response.getStatus());
    }

    @Test
    public void create_shouldReturnResponseWithBadRequestStatus() { // 400
        Optional<Book> optionalAbsent = Optional.absent();
        Mockito.when(bookDaoMock.create(book)).thenReturn(optionalAbsent);
        BookResource resource = new BookResource(bookDaoMock);
        Response response = resource.create(book);
        assertEquals(BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    public void readAll_shouldReturnResponseWithOkStatus() { // 200
        Mockito.when(bookDaoMock.readAll()).thenReturn(Arrays.asList(book));
        BookResource resource = new BookResource(bookDaoMock);
        Response response = resource.readAll();
        assertEquals(OK.getStatusCode(), response.getStatus());

        GenericEntity<List<Book>> booksEntity = (GenericEntity<List<Book>>) response.getEntity();
        List<Book> books = booksEntity.getEntity();
        assertEquals(1, books.size());
        assertReflectionEquals(book, books.get(0));
    }

    @Test
    public void readByIsbn_shouldReturnResponseWithOkStatus() { // 200
        Mockito.when(bookDaoMock.readByIsbn(ISBN)).thenReturn(Optional.of(book));
        BookResource resource = new BookResource(bookDaoMock);
        Response response = resource.readByIsbn(ISBN);
        assertEquals(OK.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
        assertEquals(book, response.getEntity());
    }

    @Test(expected = NotFoundException.class)
    public void readByIsbn_shouldThrowNotFoundException()  {
        Optional<Book> optionalAbsent = Optional.absent();
        Mockito.when(bookDaoMock.readByIsbn(ISBN)).thenReturn(optionalAbsent);
        BookResource resource = new BookResource(bookDaoMock);
        Response response = resource.readByIsbn(ISBN);
    }

    @Test
    public void update_shouldReturnResponseWithNoContentStatus() { // 204
        Mockito.when(bookDaoMock.update(book)).thenReturn(Optional.of(book));
        BookResource resource = new BookResource(bookDaoMock);
        Response response = resource.update(book);
        assertEquals(NO_CONTENT.getStatusCode(), response.getStatus());
    }

    @Test(expected = NotFoundException.class)
    public void update_shouldThrowNotFoundException() {
        Optional<Book> optionalAbsent = Optional.absent();
        Mockito.when(bookDaoMock.update(book)).thenReturn(optionalAbsent);
        BookResource resource = new BookResource(bookDaoMock);
        Response response = resource.update(book);
    }

    @Test
    public void delete_shouldReturnResponseWithNoContentStatus() { // 204
        Mockito.when(bookDaoMock.delete(book)).thenReturn(true);
        BookResource resource = new BookResource(bookDaoMock);
        Response response = resource.delete(ISBN);
        assertEquals(NO_CONTENT.getStatusCode(), response.getStatus());
    }

    @Test(expected = NotFoundException.class)
    public void delete_shouldThrowNotFoundException() {
        Mockito.when(bookDaoMock.delete(book)).thenReturn(false);
        BookResource resource = new BookResource(bookDaoMock);
        Response response = resource.delete(ISBN);
    }
}
