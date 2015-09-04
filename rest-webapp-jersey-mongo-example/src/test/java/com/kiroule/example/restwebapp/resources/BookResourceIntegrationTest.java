package com.kiroule.example.restwebapp.resources;

import static com.jayway.restassured.RestAssured.given;
import static com.kiroule.example.restwebapp.resources.BookResource.BOOK_NOT_FOUND_MSG;
import static java.lang.String.format;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import com.github.mjeanroy.junit.servers.jetty.EmbeddedJetty;
import com.github.mjeanroy.junit.servers.rules.JettyServerRule;
import com.jayway.restassured.RestAssured;
import com.kiroule.example.restwebapp.domain.Book;

import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * @author Igor Baiborodine
 */
public class BookResourceIntegrationTest {

  private static String basePath = "/resources/books";
  private static String nonExistingIsbn = "000-12345699";

  @ClassRule
  public static JettyServerRule server = new JettyServerRule(new EmbeddedJetty());

  @BeforeClass
  public static void configureRestAssured() throws Exception {
    RestAssured.port = server.getPort();
    RestAssured.basePath = basePath;
  }

  @Test
  public void restAssuredImpl_readByIsbn_nonExistingISBN_NotFoundStatusCode() throws Exception {

    given()
        .when()
            .get("/" + nonExistingIsbn)
        .then()
            .assertThat().statusCode(NOT_FOUND.getStatusCode())
            .assertThat().body(is(format(BOOK_NOT_FOUND_MSG, nonExistingIsbn)));
  }

  @Test
  public void restTemplateImpl_readByIsbn_nonExistingISBN_NotFoundStatusCode() throws Exception {
    // given
    String url = format("http://localhost:%s%s/%s", server.getPort(), basePath, nonExistingIsbn);
    // when
    Exception thrown = null;
    try {
      new RestTemplate().getForEntity(url, Book.class);
    } catch (Exception e) {
      thrown = e;
    }
    // then
    assertThat(thrown, notNullValue());
    assertThat(thrown, instanceOf(HttpClientErrorException.class));
    assertThat(((HttpClientErrorException) thrown).getStatusCode(), is(HttpStatus.NOT_FOUND));
    assertThat(((HttpClientErrorException) thrown).getResponseBodyAsString(),
        is(format(BOOK_NOT_FOUND_MSG, nonExistingIsbn)));
  }
}