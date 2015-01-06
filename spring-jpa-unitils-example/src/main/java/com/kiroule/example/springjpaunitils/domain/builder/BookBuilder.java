package com.kiroule.example.springjpaunitils.domain.builder;

import com.kiroule.example.springjpaunitils.domain.Book;

import java.math.BigDecimal;

/**
 * @author Igor Baiborodine
 */
public class BookBuilder implements Builder<Book> {

    // required parameters
    private String isbn;

    // optional parameters
    private String author;
    private String title;
    private BigDecimal price;

    public BookBuilder(final String isbn) {
        this.isbn = isbn;
    }

    public BookBuilder author(final String author) {
        this.author = author;
        return this;
    }

    public BookBuilder title(final String title) {
        this.title = title;
        return this;
    }

    public BookBuilder price(final BigDecimal price) {
        this.price = price;
        return this;
    }

    @Override
    public Book build() {
        Book book = new Book();

        book.setIsbn(isbn);
        book.setAuthor(author);
        book.setTitle(title);
        book.setPrice(price);

        return book;
    }
}
