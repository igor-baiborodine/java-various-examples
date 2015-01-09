package com.kiroule.example.restwebapp.resources;

import com.google.common.base.Optional;
import com.kiroule.example.restwebapp.dao.BookDao;
import com.kiroule.example.restwebapp.domain.Book;
import com.kiroule.example.restwebapp.domain.builder.BookBuilder;
import com.sun.jersey.api.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.NO_CONTENT;

/**
 * @author Igor Baiborodine
 */
@Service
@Path("/books")
public class BookResource {

    private BookDao bookDao;

    @Autowired
    public BookResource(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Nonnull public Response create(Book book) {

        if (bookDao.create(book).isPresent()) {
            return Response.status(NO_CONTENT).build();
        }
        return Response.status(BAD_REQUEST)
                .entity("Book with ISBN[" + book.getIsbn() + "] already exists.")
                .type(MediaType.TEXT_PLAIN).build();
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Nonnull public Response readAll() {

        List<Book> books = new ArrayList<Book>(bookDao.readAll());
        GenericEntity<List<Book>> booksEntity = new GenericEntity<List<Book>>(books) {};
        return Response.ok(booksEntity).build();
    }

    @GET
    @Path("{isbn}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Nonnull public Response readByIsbn(@PathParam("isbn") String isbn) throws NotFoundException {

        Optional<Book> optional = bookDao.readByIsbn(isbn);
        if (optional.isPresent()) {
            return Response.ok(optional.get()).build();
        }
        throw new NotFoundException("Book with ISBN[" + isbn + "] does not exist.");
    }

    @PUT
    @Path("{isbn}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Nonnull public Response update(Book book) throws NotFoundException {

        Optional<Book> optional = bookDao.update(book);
        if (optional.isPresent()) {
            return Response.status(NO_CONTENT).build();
        }
        throw new NotFoundException("Book with ISBN[" + book.getIsbn() + "] does not exist.");
    }

    @DELETE
    @Path("{isbn}")
    @Nonnull public Response delete(@PathParam("isbn") String isbn) throws NotFoundException {

        if (bookDao.delete(new BookBuilder(isbn).build())) {
            return Response.status(NO_CONTENT).build();
        }
        throw new NotFoundException("Book with ISBN[" + isbn + "] does not exist.");
    }
}
