package com.kiroule.example.mybatis.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;

import java.util.Arrays;

import javax.annotation.PostConstruct;

/**
 * @author Igor Baiborodine
 */
@Configuration
@Import(value = {
    TestDataSourceConfig.class,
    ServiceConfig.class
})
public class TestApplicationConfig {

  private static final Logger logger = LoggerFactory.getLogger(TestApplicationConfig.class);

  @Autowired
  private Environment environment;

  /**
   * Application context custom initialization. Spring profiles can be configured with a system
   * property -Dspring.profiles.active=test
   */
  @PostConstruct
  public void customInit() {

    logger.debug("Looking for Spring active profiles...");
    if (environment.getActiveProfiles().length == 0) {
      logger.info("No Spring profile configured, running with default configuration.");
    } else {
      logger.info("Detected Spring profiles: {}", Arrays.asList(environment.getActiveProfiles()));
    }
  }
}
