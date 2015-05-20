package com.kiroule.example.mybatis.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

/**
 * @author Igor Baiborodine
 */
public class DataSourceConfig extends BaseDataSourceConfig {

  private static final Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);

  @Bean
  public DataSource dataSource() {

    BasicDataSource dataSource = new BasicDataSource();
    try {
      dataSource.setDriverClassName("com.mysql.jdbc.Driver");
      dataSource.setUrl("jdbc:mysql://localhost:3306/sakila");
      dataSource.setUsername("testuser");
      dataSource.setPassword("123abc");
      logger.info("Initialized data source");
    } catch (Exception e) {
      logger.error("Error while initializing data source:", e);
    }
    return dataSource;
  }
}
