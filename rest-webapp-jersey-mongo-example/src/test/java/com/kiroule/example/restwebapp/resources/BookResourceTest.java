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
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.NO_CONTENT;
import static javax.ws.rs.core.Response.Status.OK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

/**
 * JUnit/Mockito based unit tests for the {@link BookResource} class.
 *
 * @author Igor Baiborodine
 */
@RunWith(MockitoJUnitRunner.class)
public class BookResourceTest {

  private static final String ISBN = "978-0-321-35668-0";
  private static final String AUTHOR = "Joshua Bloch";
  private static final String TITLE = "Effective Java";
  private static final BigDecimal PRICE = new BigDecimal("50.0");
  private static final Set<String> CATEGORIES =
      Sets.newHashSet("Software", "Java", "Object-Oriented Design");
  @Mock
  private BookDao bookDaoMock;
  @InjectMocks
  private BookResource resource;

  private Book book;

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
    book = null;
  }

  @Test
  public void create_shouldReturnResponseWithNoContentStatus() { // 204
    when(bookDaoMock.create(book)).thenReturn(Optional.of(book));
    Response response = resource.create(book);
    assertEquals(NO_CONTENT.getStatusCode(), response.getStatus());
  }

  @Test
  public void create_shouldReturnResponseWithBadRequestStatus() { // 400
    when(bookDaoMock.create(book)).thenReturn(Optional.<Book>absent());
    Response response = resource.create(book);
    assertEquals(BAD_REQUEST.getStatusCode(), response.getStatus());
  }

  @Test
  public void readAll_shouldReturnResponseWithOkStatus() { // 200
    when(bookDaoMock.readAll()).thenReturn(Collections.singletonList(book));
    Response response = resource.readAll();
    assertEquals(OK.getStatusCode(), response.getStatus());

    GenericEntity<List<Book>> booksEntity = (GenericEntity<List<Book>>) response.getEntity();
    List<Book> books = booksEntity.getEntity();
    assertEquals(1, books.size());
    assertReflectionEquals(book, books.get(0));
  }

  @Test
  public void readByIsbn_shouldReturnResponseWithOkStatus() { // 200
    when(bookDaoMock.readByIsbn(ISBN)).thenReturn(Optional.of(book));
    Response response = resource.readByIsbn(ISBN);
    assertEquals(OK.getStatusCode(), response.getStatus());
    assertNotNull(response.getEntity());
    assertEquals(book, response.getEntity());
  }

  @Test
  public void update_shouldReturnResponseWithNoContentStatus() { // 204
    when(bookDaoMock.update(book)).thenReturn(Optional.of(book));
    Response response = resource.update(book);
    assertEquals(NO_CONTENT.getStatusCode(), response.getStatus());
  }

  @Test(expected = NotFoundException.class)
  public void update_shouldThrowNotFoundException() {
    when(bookDaoMock.update(book)).thenReturn(Optional.<Book>absent());
    resource.update(book);
  }

  @Test
  public void delete_shouldReturnResponseWithNoContentStatus() { // 204
    when(bookDaoMock.delete(book)).thenReturn(true);
    Response response = resource.delete(ISBN);
    assertEquals(NO_CONTENT.getStatusCode(), response.getStatus());
  }

  @Test(expected = NotFoundException.class)
  public void delete_shouldThrowNotFoundException() {
    when(bookDaoMock.delete(book)).thenReturn(false);
    resource.delete(ISBN);
  }
}
