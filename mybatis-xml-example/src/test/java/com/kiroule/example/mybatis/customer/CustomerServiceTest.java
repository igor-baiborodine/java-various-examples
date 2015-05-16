package com.kiroule.example.mybatis.customer;

import com.kiroule.example.mybatis.TestDbServer;
import com.kiroule.example.mybatis.TestUtil;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static com.kiroule.example.mybatis.TestUtil.newAddress;
import static com.kiroule.example.mybatis.TestUtil.newCustomer;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * @author Igor Baiborodine
 */
public class CustomerServiceTest {

  private CustomerService customerService = new CustomerService(TestUtil.getSqlSessionManager());

  @BeforeClass
  public static void setUpClass() {
    TestDbServer.start();
  }

  @AfterClass
  public static void tearDownClass() {
    TestDbServer.stop();
  }

  @Before
  public void setUp() {
    TestDbServer.dropTables();
    TestDbServer.createTables();
    TestDbServer.insertData();
  }

  @Test
  public void getCustomer_shouldReturnNullForNonExistingCustomerId() {
    short nonExistingCustomerId = 45;
    Customer customer = customerService.getCustomer(nonExistingCustomerId);
    assertThat(customer, nullValue());
  }

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
    assertNotNull("get customer rewards report failed - rewards report is null", rewardsReport);
    assertTrue("get customer rewards report failed - rewards report size is not 0",
        rewardsReport.size() == 0);
  }

}
