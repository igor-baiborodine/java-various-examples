/**
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.kiroule.example.springjpahsqldb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;

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
