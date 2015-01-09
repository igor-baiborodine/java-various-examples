package com.kiroule.example.restwebapp.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Igor Baiborodine
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name = "books")
public class BooksList {

    private List<Book> books = new ArrayList<Book>();

    public BooksList() {}

    public BooksList(List<Book> books) {
        this.books = books;
    }

    @XmlElement(name = "book")
    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(final List<Book> books) {
        this.books = books;
    }
}
