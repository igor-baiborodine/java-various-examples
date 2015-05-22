package com.kiroule.example.mybatis.config;

import com.kiroule.example.mybatis.AbstractTest;
import com.kiroule.example.mybatis.mapper.CustomerMapper;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class ApplicationConfigTest extends AbstractTest {

  @Autowired
  private Environment env;

  @Autowired
  private ApplicationContext applicationContext;

  @Autowired
  private DataSource dataSource;

  @Autowired
  private CustomerMapper customerMapper;

  @Test
  public void customInit_shouldInitializeApplicationContext() throws Exception {
    assertThat(applicationContext, notNullValue());
    assertThat(dataSource, notNullValue());
    assertThat(customerMapper, notNullValue());
    assertThat(env.getProperty("test.property"), is("test"));
  }
}