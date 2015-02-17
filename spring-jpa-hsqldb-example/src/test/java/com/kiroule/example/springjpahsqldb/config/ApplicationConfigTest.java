package com.kiroule.example.springjpahsqldb.config;

import com.kiroule.example.springjpahsqldb.AbstractTest;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.sql.DataSource;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class ApplicationConfigTest extends AbstractTest {

  @Autowired
  private ApplicationContext applicationContext;

  @Autowired
  private DataSource dataSource;

  @Test
  public void customInit_shouldInitializeApplicationContext() throws Exception {

    assertThat(applicationContext, notNullValue());
    assertThat(dataSource, notNullValue());
  }
}