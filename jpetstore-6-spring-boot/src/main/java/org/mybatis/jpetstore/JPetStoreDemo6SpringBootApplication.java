package org.mybatis.jpetstore;

import org.mybatis.jpetstore.config.DataSourceConfig;
import org.mybatis.jpetstore.config.ServiceConfig;
import org.mybatis.jpetstore.config.WebConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

@SpringBootApplication
public class JPetStoreDemo6SpringBootApplication extends SpringBootServletInitializer {

  public static void main(String[] args) {
    SpringApplication.run(new Class[] {JPetStoreDemo6SpringBootApplication.class, WebConfig.class}, args);
  }

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(
        DataSourceConfig.class,
        ServiceConfig.class);
  }

}
