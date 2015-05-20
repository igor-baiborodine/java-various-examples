package com.kiroule.example.mybatis.mapper;

import com.kiroule.example.mybatis.AbstractTest;
import com.kiroule.example.mybatis.domain.Address;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.kiroule.example.mybatis.TestUtil.newAddress;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Igor Baiborodine
 */
public class AddressMapperTest extends AbstractTest {

  @Autowired
  private AddressMapper addressMapper;

  @Test
  public void insert_shouldInsertAddress() {
    Address address = newAddress();
    int count = addressMapper.insert(address);
    assertEquals("test insert failed - count must be 1", 1, count);
    assertNotNull("test insert failed - address id must not be null", address.getAddressId());
  }
}
