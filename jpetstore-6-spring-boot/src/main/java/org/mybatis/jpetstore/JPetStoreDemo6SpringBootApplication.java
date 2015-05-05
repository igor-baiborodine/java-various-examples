package org.mybatis.jpetstore;

import org.mybatis.jpetstore.config.DataSourceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class JPetStoreDemo6SpringBootApplication extends SpringBootServletInitializer {

  public static void main(String[] args) {
    SpringApplication.run(JPetStoreDemo6SpringBootApplication.class, args);
  }

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(JPetStoreDemo6SpringBootApplication.class, DataSourceConfig.class);
  }
}
