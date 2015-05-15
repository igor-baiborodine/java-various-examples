package com.kiroule.example.mybatis;

import com.kiroule.example.mybatis.customer.Customer;
import com.kiroule.example.mybatis.customer.CustomerService;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author Igor Baiborodine
 */
public class MybatisExampleApp {

  public static void main(String...args) {

    CustomerService customerService = new CustomerService(MybatisExampleUtil.getSqlSessionManager());
    List<Customer> customers = customerService.getAll();
    customers.stream()
        .filter(c -> c.getFirstName().contains("SARA"))
        .collect(toList())
        .forEach(System.out::println);
  }
}
