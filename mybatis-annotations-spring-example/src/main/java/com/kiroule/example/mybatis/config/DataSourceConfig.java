package com.kiroule.example.mybatis.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

/**
 * @author Igor Baiborodine
 */
public class DataSourceConfig {

  private static final Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);

  @Autowired
  private Environment env;

  @Bean
  public DataSource dataSource() {

    BasicDataSource dataSource = new BasicDataSource();
    try {
      dataSource.setDriverClassName(env.getProperty("ds.driver.class.name"));
      dataSource.setUrl(env.getProperty("ds.url"));
      dataSource.setUsername(env.getProperty("ds.username"));
      dataSource.setPassword(env.getProperty("ds.password"));
      logger.info("Initialized data source");
    } catch (Exception e) {
      logger.error("Error while initializing data source:", e);
    }
    return dataSource;
  }
}
