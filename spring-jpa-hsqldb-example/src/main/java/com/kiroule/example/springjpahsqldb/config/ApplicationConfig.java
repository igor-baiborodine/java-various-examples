package com.kiroule.example.springjpahsqldb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Igor Baiborodine
 */
@Configuration
@Import(value = {
    DataSourceConfig.class
})
@Slf4j
public class ApplicationConfig {

  @Autowired
  private Environment environment;

  /**
   * Application context custom initialization. Spring profiles can be configured with a system
   * property -Dspring.profiles.active=prod
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
