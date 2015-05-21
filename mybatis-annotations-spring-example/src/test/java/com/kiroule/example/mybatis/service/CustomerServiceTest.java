package com.kiroule.example.mybatis.service;

import com.kiroule.example.mybatis.AbstractTest;
import com.kiroule.example.mybatis.domain.Customer;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.kiroule.example.mybatis.TestUtil.newAddress;
import static com.kiroule.example.mybatis.TestUtil.newCustomer;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * @author Igor Baiborodine
 */
public class CustomerServiceTest extends AbstractTest {

  @Autowired
  private CustomerService customerService;

  @Test
  public void addCustomer_shouldAddNewCustomer() {
    Short customerId = customerService.addCustomer(newCustomer(), newAddress());
    assertThat(customerId, notNullValue());
  }

  @Test @Ignore
  public void getCustomerRewardsReport_shouldReturnEmptyRewardsReport() {
    Byte minMonthlyPurchases = Byte.MAX_VALUE;
    Double minDollarAmountPurchased = 1000000.0;
    List<Customer> rewardsReport = customerService.getCustomerRewardsReport(
        minMonthlyPurchases, minDollarAmountPurchased);
    assertTrue("get customer rewards report failed - rewards report size must be 0", rewardsReport.size() == 0);
  }
}
