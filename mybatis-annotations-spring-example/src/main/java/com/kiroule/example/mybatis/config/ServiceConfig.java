package com.kiroule.example.mybatis.config;

import com.kiroule.example.mybatis.service.AddressService;
import com.kiroule.example.mybatis.service.CustomerService;

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
  public CustomerService accountService() {
    return new CustomerService();
  }

  @Bean
  public AddressService catalogService() {
    return new AddressService();
  }
}
