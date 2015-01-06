package com.kiroule.example.springjpaunitils.persistence;

import com.kiroule.example.springjpaunitils.domain.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;

/**
 * @author Igor Baiborodine
 */
@Repository
public class BookJpaDaoImpl extends AbstractJpaDao<Book, Long> {

    private final static Logger logger = LoggerFactory.getLogger(BookJpaDaoImpl.class);

    public Book readByIsbn(String isbn) {
        Query query = entityManager.createNamedQuery("Book.readByIsbn");
        query.setParameter("isbn", isbn);
        Book book = (Book) query.getSingleResult();
        logger.debug("Read by isbn book[{}]", book);
        return book;
    }
}
