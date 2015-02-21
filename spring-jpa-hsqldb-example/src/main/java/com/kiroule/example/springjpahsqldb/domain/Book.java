package com.kiroule.example.springjpahsqldb.domain;

import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Igor Baiborodine
 */
@Entity
@Table(name = "BOOK")
public class Book implements Serializable, Comparable<Book> {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "BOOK_ID")
  private Long bookId;

  @Version
  @Column(name = "VERSION")
  private Long version;

  @Column(name = "ISBN", unique = true, nullable = false, length = 17)
  private String isbn;

  @Column(name = "AUTHOR")
  private String author;

  @Column(name = "TITLE")
  private String title;

  @Column(name = "PRICE", precision = 12, scale = 2)
  private BigDecimal price;

  public Long getBookId() {
    return bookId;
  }

  public void setBookId(final Long bookId) {
    this.bookId = bookId;
  }

  public Long getVersion() {
    return version;
  }

  public void setVersion(final Long version) {
    this.version = version;
  }

  public String getIsbn() {
    return isbn;
  }

  public void setIsbn(final String isbn) {
    this.isbn = checkNotNull(isbn, "Argument[isbn] must not be null");
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(final String author) {
    this.author = author;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(final String title) {
    this.title = title;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(final BigDecimal price) {
    this.price = price;
  }

  @Override
  public boolean equals(final Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof Book)) {
      return false;
    }
    Book that = (Book) o;
    return Objects.equal(this.isbn, that.isbn);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(isbn);
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(this)
        .add("bookId", bookId)
        .add("version", version)
        .add("isbn", isbn)
        .add("author", author)
        .add("title", title)
        .add("price", price)
        .toString();
  }

  @Override
  public int compareTo(final Book that) {
    return ComparisonChain.start()
        .compare(this.isbn, that.isbn)
        .result();
  }
}
