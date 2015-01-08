package com.kiroule.example.restwebapp.dao;

import com.google.common.base.Optional;
import com.kiroule.example.restwebapp.domain.Book;
import com.kiroule.example.restwebapp.domain.builder.BookBuilder;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Igor Baiborodine
 */
@Repository
public class BookDao {

    // ConcurrentHashMap does NOT allow null to be used as a key or value
    private ConcurrentMap<String, Book> books = new ConcurrentHashMap<String, Book>();

    @Nonnull
    public Optional<Book> create(@Nonnull final Book bookToCreate) {
        checkNotNull(bookToCreate, "Argument[bookToCreate] must not be null");

        Book book = new BookBuilder(bookToCreate.getIsbn()).build(bookToCreate);
        if (books.putIfAbsent(book.getIsbn(), book) == null) {
            return Optional.of(bookToCreate);
        }
        return Optional.absent(); // book already exists
    }

    @Nonnull
    public List<Book> readAll() {
        return new ArrayList<>(books.values());
    }

    @Nonnull
    public Optional<Book> readByIsbn(@Nonnull final String isbn) {
        checkNotNull(isbn, "Argument[isbn] must not be null");

        Book book = books.get(isbn);
        if (book != null) {
            synchronized (book) {
                if (books.get(isbn) == null) {
                    return Optional.absent(); // book does not exist
                }
                return Optional.of(new BookBuilder(book.getIsbn()).build(book));
            }
        }
        return Optional.absent(); // book does not exist
    }

    @Nonnull
    public Optional<Book> update(@Nonnull final Book bookToUpdate) {
        checkNotNull(bookToUpdate, "Argument[bookToUpdate] must not be null");

        Book book = books.get(bookToUpdate.getIsbn());
        if (book != null) {
            synchronized (book) {
                if (books.get(bookToUpdate.getIsbn()) == null) {
                    return Optional.absent(); // book does not exist
                }
                book.setAuthor(bookToUpdate.getAuthor());
                book.setTitle(bookToUpdate.getTitle());
                book.setPrice(bookToUpdate.getPrice());
                book.setCategories(bookToUpdate.getCategories());
                return Optional.of(bookToUpdate);
            }
        }
        return Optional.absent(); // book does not exist
    }

    public boolean delete(@Nonnull final Book bookToDelete) {
        checkNotNull(bookToDelete, "Argument[bookToDelete] must not be null");

        Book book = books.get(bookToDelete.getIsbn());
        if (book != null) {
            synchronized (book) {
                return books.remove(book.getIsbn(), book);
            }
        }
        return false;
    }
}
