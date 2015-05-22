package com.kiroule.example.mybatis.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import javax.sql.DataSource;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.HSQL;

/**
 * @author Igor Baiborodine
 */
@Import(value = {
    BaseDataSourceConfig.class,
})
public class TestDataSourceConfig {

  private static final Logger logger = LoggerFactory.getLogger(TestDataSourceConfig.class);

  @Bean
  public DataSource dataSource() {
    EmbeddedDatabase dataSource = new EmbeddedDatabaseBuilder()
        .setType(HSQL)
        .addScript("classpath:hsqldb-schema.sql")
        .addScript("classpath:hsqldb-data.sql")
        .build();

    logger.info("Initialized test data source");
    return dataSource;
  }
}
