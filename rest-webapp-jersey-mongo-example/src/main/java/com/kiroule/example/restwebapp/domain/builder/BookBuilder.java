package com.kiroule.example.restwebapp.domain.builder;

import com.kiroule.example.restwebapp.domain.Book;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Igor Baiborodine
 */
public class BookBuilder implements DomainBuilder<Book> {

  // required parameters
  private String isbn;

  // optional parameters
  private String author;
  private String title;
  private BigDecimal price;
  private Set<String> categories = Collections.emptySet();

  public BookBuilder(@Nonnull final String isbn) {
    this.isbn = isbn;
  }

  public BookBuilder author(@Nullable final String author) {
    this.author = author;
    return this;
  }

  public BookBuilder title(@Nullable final String title) {
    this.title = title;
    return this;
  }

  public BookBuilder price(@Nullable final BigDecimal price) {
    this.price = price;
    return this;
  }

  public BookBuilder categories(@Nonnull final Set<String> categories) {
    this.categories = categories;
    return this;
  }

  @Override
  @Nonnull
  public Book build() {
    Book book = new Book();

    book.setIsbn(isbn);
    book.setAuthor(author);
    book.setTitle(title);
    book.setPrice(price);
    book.setCategories(categories);

    return book;
  }

  @Override
  @Nonnull
  public Book build(@Nonnull final Book book) {
    Book copy = new Book();

    copy.setIsbn(this.isbn);
    copy.setAuthor(book.getAuthor());
    copy.setTitle(book.getTitle());
    copy.setPrice(book.getPrice());
    copy.setCategories(book.getCategories());

    return copy;
  }
}
