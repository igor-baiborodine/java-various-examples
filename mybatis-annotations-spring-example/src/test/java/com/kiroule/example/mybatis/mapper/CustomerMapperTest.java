package com.kiroule.example.mybatis.mapper;

import com.kiroule.example.mybatis.AbstractTest;
import com.kiroule.example.mybatis.domain.Address;
import com.kiroule.example.mybatis.domain.Customer;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.kiroule.example.mybatis.TestUtil.newAddress;
import static com.kiroule.example.mybatis.TestUtil.newCustomer;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * @author Igor Baiborodine
 */
public class CustomerMapperTest extends AbstractTest {

  @Autowired
  private CustomerMapper customerMapper;

  @Autowired
  private AddressMapper addressMapper;

  @Test
  public void insert_shouldInsertCustomer() {

    Address address = newAddress();
    addressMapper.insert(address);
    assertThat(address.getAddressId(), notNullValue());

    Customer customer = newCustomer();
    customer.setAddressId(address.getAddressId());
    int count = customerMapper.insert(customer);
    assertThat(count, is(1));
    assertThat(customer.getCustomerId(), notNullValue());
  }
}
