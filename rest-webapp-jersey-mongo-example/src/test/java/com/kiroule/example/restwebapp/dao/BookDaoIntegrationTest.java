package com.kiroule.example.restwebapp.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

import com.google.common.collect.Sets;

import com.kiroule.example.restwebapp.domain.Book;
import com.kiroule.example.restwebapp.domain.builder.BookBuilder;
import com.kiroule.example.restwebapp.util.AppUtils;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.mongo.tests.MongodForTestsFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * JUnit based unit tests for the {@link BookDao} class.
 *
 * @author Igor Baiborodine
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:application-context-test.xml")
public class BookDaoIntegrationTest {

  private static final String ISBN = "978-0-321-35668-0";
  private static final String AUTHOR = "Joshua Bloch";
  private static final String TITLE = "Effective Java";
  private static final BigDecimal PRICE = new BigDecimal("50.0");
  private static final Set<String> CATEGORIES =
      Sets.newHashSet("Software", "Java", "Object-Oriented Design");

  private MongodForTestsFactory factory;
  private MongoClient mongoClient;

  @Value("${mongodb.db.name}")
  private String dbName;
  @Value("${mongodb.db.collection.books}")
  private String collectionName;
  @Autowired
  private BookDao bookDao;

  private DB database;
  private DBCollection collection;
  private Book book;

  @Before
  public void setUp() throws IOException {
    factory = MongodForTestsFactory.with(Version.Main.PRODUCTION);
    mongoClient = factory.newMongo();
    bookDao.setMongoClient(mongoClient);
    bookDao.init();

    database = mongoClient.getDB(dbName);
    collection = database.getCollection(collectionName);
    assertTrue(collection.count() == 0);

    book = new BookBuilder(ISBN)
        .author(AUTHOR)
        .title(TITLE)
        .price(PRICE)
        .categories(CATEGORIES).build();
    book.set_id(book.getIsbn());
  }

  @After
  public void tearDown() {
    if (factory != null) {
      factory.shutdown();
    }
    collection = null;
    database = null;
    book = null;
  }

  private void insertBook(Book book) {
    collection.insert(AppUtils.toDBObject(book));
    assertThat(collection.count(), is(1L));
  }

  @Test(expected = NullPointerException.class)
  public void create_shouldThrowNpeForNullBookArgument() {
    bookDao.create(null);
  }

  @Test
  public void create_shouldReturnOptionalPresentForNonExistingIsbn() {
    Optional<Book> optional = bookDao.create(book);
    assertTrue(optional.isPresent());
    assertSame(book, optional.get());
  }

  @Test
  public void create_shouldReturnOptionalAbsentForExistingIsbn() {
    insertBook(book);

    Optional<Book> optional = bookDao.create(book);
    assertFalse(optional.isPresent());
  }

  @Test
  public void readAll_shouldReturnAllBooks() {
    insertBook(book);

    List<Book> allBooks = bookDao.readAll();
    assertEquals(1, allBooks.size());
    assertTrue(allBooks.contains(book));
    assertReflectionEquals(book, allBooks.get(0));
  }

  @Test(expected = NullPointerException.class)
  public void readByIsbn_shouldThrowNpeForNullIsbnArgument() {
    bookDao.readByIsbn(null);
  }

  @Test
  public void readByIsbn_shouldReturnOptionalPresentForExistingIsbn() {
    insertBook(book);

    Optional<Book> optional = bookDao.readByIsbn(ISBN);
    assertTrue(optional.isPresent());
    assertEquals(ISBN, optional.get().getIsbn());
  }

  @Test
  public void readByIsbn_shouldReturnOptionalAbsentForNonExistingIsbn() {
    Optional<Book> optional = bookDao.readByIsbn(ISBN);
    assertFalse(optional.isPresent());
  }

  @Test(expected = NullPointerException.class)
  public void update_shouldThrowNpeForNullIsbnArgument() {
    bookDao.update(null);
  }

  @Test
  public void update_shouldReturnOptionalPresentForExistingIsbn() {
    insertBook(book);

    Optional<Book> optional = bookDao.update(book);
    assertTrue(optional.isPresent());
    assertSame(book, optional.get());

    DBObject dbObject = collection.findOne(AppUtils.toDBObject(book));
    Book updatedBook = (Book) AppUtils.fromDBObject(dbObject, Book.class);
    assertReflectionEquals(book, updatedBook);
  }

  @Test
  public void update_shouldReturnOptionalAbsentForNonExistingIsbn() {
    Optional<Book> optional = bookDao.update(book);
    assertFalse(optional.isPresent());
  }

  @Test(expected = NullPointerException.class)
  public void delete_shouldThrowNpeForNullIsbnArgument() {
    bookDao.delete(null);
  }

  @Test
  public void delete_shouldDeleteForExistingIsbn() {
    insertBook(book);
    assertTrue(bookDao.delete(book));
  }

  @Test
  public void delete_shouldNotDeleteForNonExistingIsbn() {
    assertFalse(bookDao.delete(book));
  }
}


