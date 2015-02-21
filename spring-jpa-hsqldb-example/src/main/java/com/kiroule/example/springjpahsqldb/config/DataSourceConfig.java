package com.kiroule.example.springjpahsqldb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import javax.sql.DataSource;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.HSQL;

/**
 * @author Igor Baiborodine
 */
@Configuration
public class DataSourceConfig {

  @Bean
  @Profile("test")
  public DataSource testDataSource() {
    return new EmbeddedDatabaseBuilder().setType(HSQL).build();
  }
}
