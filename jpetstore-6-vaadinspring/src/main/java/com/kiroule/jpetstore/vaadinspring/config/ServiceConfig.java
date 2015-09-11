package com.kiroule.jpetstore.vaadinspring.config;

import com.kiroule.jpetstore.vaadinspring.service.AccountService;
import com.kiroule.jpetstore.vaadinspring.service.CatalogService;
import com.kiroule.jpetstore.vaadinspring.service.OrderService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Igor Baiborodine
 */
@Configuration
@EnableTransactionManagement
public class ServiceConfig {

  @Bean
  public AccountService accountService() {
    return new AccountService();
  }

  @Bean
  public CatalogService catalogService() {
    return new CatalogService();
  }

  @Bean
  public OrderService orderService() {
    return new OrderService();
  }
}
