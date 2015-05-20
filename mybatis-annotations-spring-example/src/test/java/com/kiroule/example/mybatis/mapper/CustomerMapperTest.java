package com.kiroule.example.mybatis.mapper;

import com.kiroule.example.mybatis.AbstractTest;
import com.kiroule.example.mybatis.domain.Customer;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.kiroule.example.mybatis.TestUtil.newCustomer;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Igor Baiborodine
 */
public class CustomerMapperTest extends AbstractTest {

  @Autowired
  private CustomerMapper customerMapper;

  @Test
  public void insert_shouldInsertCustomer() {
    Customer customer = newCustomer();
    customer.setAddressId((short) 1);
    int count = customerMapper.insert(customer);
    assertEquals("test insert failed - insert count must be 1", 1, count);
    assertNotNull("test insert failed - customer id must not be null", customer.getCustomerId());
  }
}
