package com.kiroule.example.restwebapp.resources;

import com.google.common.base.Optional;
import com.kiroule.example.restwebapp.dao.BookDao;
import com.kiroule.example.restwebapp.domain.Book;
import com.kiroule.example.restwebapp.domain.BooksList;
import com.kiroule.example.restwebapp.domain.builder.BookBuilder;
import com.kiroule.example.restwebapp.exceptions.EntityExistsException;
import com.kiroule.example.restwebapp.exceptions.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Igor Baiborodine
 */
@Controller
@RequestMapping("/books")
public class BookResource {

    private static final Logger logger = LoggerFactory.getLogger(BookResource.class);
    private BookDao bookDao;

    @Autowired
    public BookResource(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @RequestMapping(method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> create(@RequestBody Book book) {
        logger.info("book[{}]", book);

        if (bookDao.create(book).isPresent()) {
            return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
        }
        throw new EntityExistsException("Book with ISBN[" + book.getIsbn() + "] already exists.");
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BooksList> readAll() {

        BooksList books = new BooksList(bookDao.readAll());
        logger.info("found [{}] books", books.getBooks().size());
        return new ResponseEntity<BooksList>(books, HttpStatus.OK);
    }

    @RequestMapping(value = "/{isbn}", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Book> readByIsbn(@PathVariable String isbn) {
        logger.info("isbn[{}]", isbn);

        Optional<Book> optional = bookDao.readByIsbn(isbn);
        if (optional.isPresent()) {
            return new ResponseEntity<Book>(optional.get(), HttpStatus.OK);
        }
        throw new EntityNotFoundException("Book with ISBN[" + isbn + "] not found.");
    }

    @RequestMapping(value = "/{isbn}", method = RequestMethod.PUT,
            consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> update(@RequestBody Book book) {

        Optional<Book> optional = bookDao.update(book);
        if (optional.isPresent()) {
            return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
        }
        throw new EntityNotFoundException("Book with ISBN[" + book.getIsbn() + "] does not exist.");
    }

    @RequestMapping(value = "{isbn}", method = RequestMethod.DELETE)
    public ResponseEntity<String> delete(@PathVariable String isbn) {

        if (bookDao.delete(new BookBuilder(isbn).build())) {
            return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
        }
        throw new EntityNotFoundException("Book with ISBN[" + isbn + "] does not exist.");
    }

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException e) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        return new ResponseEntity<String>(e.getMessage(), headers, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({EntityExistsException.class})
    public ResponseEntity<String> handleEntityExistsException(EntityExistsException e) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        return new ResponseEntity<String>(e.getMessage(), headers, HttpStatus.BAD_REQUEST);
    }
}
