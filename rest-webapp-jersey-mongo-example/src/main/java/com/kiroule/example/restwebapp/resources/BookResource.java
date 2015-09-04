package com.kiroule.example.restwebapp.resources;

import static java.lang.String.format;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.NO_CONTENT;

import com.kiroule.example.restwebapp.dao.BookDao;
import com.kiroule.example.restwebapp.domain.Book;
import com.kiroule.example.restwebapp.domain.builder.BookBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Igor Baiborodine
 */
@Controller
@Path("/books")
public class BookResource {

  public static final String BOOK_NOT_FOUND_MSG = "Book with ISBN[%s] does not exist";
  public static final String BOOK_EXISTS_MSG = "Book with ISBN[%s] already exists";

  private BookDao bookDao;

  @Autowired
  public BookResource(BookDao bookDao) {
    this.bookDao = bookDao;
  }

  @POST
  @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  public Response create(Book book) {

    if (bookDao.create(book).isPresent()) {
      return Response.status(NO_CONTENT).build();
    }
    return Response.status(BAD_REQUEST)
        .entity(format(BOOK_EXISTS_MSG, book.getIsbn()))
        .type(MediaType.TEXT_PLAIN).build();
  }

  @GET
  @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  public Response readAll() {

    List<Book> books = new ArrayList<>(bookDao.readAll());
    GenericEntity<List<Book>> booksEntity = new GenericEntity<List<Book>>(books) {
    };
    return Response.ok(booksEntity).build();
  }

  @GET
  @Path("{isbn}")
  @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  public Response readByIsbn(@PathParam("isbn") String isbn) throws NotFoundException {

    Optional<Book> optional = bookDao.readByIsbn(isbn);
    if (optional.isPresent()) {
      return Response.ok(optional.get()).build();
    }
    return Response.status(NOT_FOUND).entity(format(BOOK_NOT_FOUND_MSG, isbn)).build();
  }

  @PUT
  @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  public Response update(Book book) throws NotFoundException {

    Optional<Book> optional = bookDao.update(book);
    if (optional.isPresent()) {
      return Response.status(NO_CONTENT).build();
    }
    return Response.status(NOT_FOUND).entity(format(BOOK_NOT_FOUND_MSG, book.getIsbn())).build();
  }

  @DELETE
  @Path("{isbn}")
  public Response delete(@PathParam("isbn") String isbn) throws NotFoundException {

    if (bookDao.delete(new BookBuilder(isbn).build())) {
      return Response.status(NO_CONTENT).build();
    }
    return Response.status(NOT_FOUND).entity(format(BOOK_NOT_FOUND_MSG, isbn)).build();
  }
}
