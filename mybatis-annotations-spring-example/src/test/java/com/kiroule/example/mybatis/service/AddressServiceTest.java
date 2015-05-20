package com.kiroule.example.mybatis.service;

import com.kiroule.example.mybatis.AbstractTest;
import com.kiroule.example.mybatis.TestUtil;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertNotNull;

/**
 * @author Igor Baiborodine
 */
public class AddressServiceTest extends AbstractTest {

  @Autowired
  private AddressService addressService;

  @Test
  public void addAddress_shouldAddNewAddress() {
    Short addressId = addressService.addAddress(TestUtil.newAddress());
    assertNotNull("add address failed - address id must not be null", addressId);
  }
}
