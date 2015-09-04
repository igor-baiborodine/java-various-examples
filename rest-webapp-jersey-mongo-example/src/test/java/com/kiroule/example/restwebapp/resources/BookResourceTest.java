package com.kiroule.example.restwebapp.resources;

import static com.kiroule.example.restwebapp.resources.BookResource.BOOK_NOT_FOUND_MSG;
import static java.lang.String.format;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.NO_CONTENT;
import static javax.ws.rs.core.Response.Status.OK;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

import com.google.common.collect.Sets;

import com.kiroule.example.restwebapp.dao.BookDao;
import com.kiroule.example.restwebapp.domain.Book;
import com.kiroule.example.restwebapp.domain.builder.BookBuilder;

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
import java.util.Optional;
import java.util.Set;

import javax.ws.rs.core.Response;

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
    when(bookDaoMock.create(book)).thenReturn(Optional.empty());
    Response response = resource.create(book);
    assertEquals(BAD_REQUEST.getStatusCode(), response.getStatus());
  }

  @Test
  public void readAll_shouldReturnResponseWithOkStatus() { // 200
    when(bookDaoMock.readAll()).thenReturn(Collections.singletonList(book));
    Response response = resource.readAll();
    assertEquals(OK.getStatusCode(), response.getStatus());

    List<Book> books = (List<Book>) response.getEntity();
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

  @Test
  public void update_shouldReturnResponseWithNotFoundStatus() { // 404
    when(bookDaoMock.update(book)).thenReturn(Optional.empty());
    Response response = resource.update(book);
    assertThat(response.getStatus(), is(NOT_FOUND.getStatusCode()));
    assertThat(response.getEntity(), is(format(BOOK_NOT_FOUND_MSG, book.getIsbn())));
  }

  @Test
  public void delete_shouldReturnResponseWithNoContentStatus() { // 204
    when(bookDaoMock.delete(book)).thenReturn(true);
    Response response = resource.delete(ISBN);
    assertEquals(NO_CONTENT.getStatusCode(), response.getStatus());
  }

  @Test
  public void delete_shouldThrowNotFoundException() {
    when(bookDaoMock.delete(book)).thenReturn(false);
    Response response = resource.delete(ISBN);
    assertThat(response.getStatus(), is(NOT_FOUND.getStatusCode()));
    assertThat(response.getEntity(), is(format(BOOK_NOT_FOUND_MSG, book.getIsbn())));
  }
}
