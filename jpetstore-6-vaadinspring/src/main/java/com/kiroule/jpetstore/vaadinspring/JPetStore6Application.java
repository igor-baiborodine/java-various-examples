package com.kiroule.jpetstore.vaadinspring;

import com.kiroule.jpetstore.vaadinspring.config.DataSourceConfig;
import com.kiroule.jpetstore.vaadinspring.config.ServiceConfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({DataSourceConfig.class, ServiceConfig.class})
public class JPetStore6Application {

  public static void main(String[] args) {
    SpringApplication.run(JPetStore6Application.class, args);
  }
}
