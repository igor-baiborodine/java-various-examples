package com.kiroule.example.springjpahsqldb.config;

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
    DataSourceConfig.class,
    JpaPersistenceConfig.class
})
public class ApplicationConfig {

  private static final Logger log = LoggerFactory.getLogger(ApplicationConfig.class);

  @Autowired
  private Environment environment;

  /**
   * Application context custom initialization. Spring profiles can be configured with a system
   * property -Dspring.profiles.active=test
   */
  @PostConstruct
  public void customInit() {

    log.debug("Looking for Spring active profiles...");
    if (environment.getActiveProfiles().length == 0) {
      log.info("No Spring profile configured, running with default configuration.");
    } else {
      log.info("Detected Spring profile: {}", Arrays.asList(environment.getActiveProfiles()));
    }
  }
}
