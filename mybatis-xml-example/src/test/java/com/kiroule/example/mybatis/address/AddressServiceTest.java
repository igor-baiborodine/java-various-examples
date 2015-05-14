package com.kiroule.example.mybatis.address;

import com.kiroule.example.mybatis.TestDbServer;
import com.kiroule.example.mybatis.TestUtil;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static com.kiroule.example.mybatis.TestUtil.newAddress;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * @author Igor Baiborodine
 */
public class AddressServiceTest {

  private AddressService addressService = new AddressService(TestUtil.getSqlSessionManager());

  @BeforeClass
  public static void setUpClass() {
    TestDbServer.start();
  }

  @AfterClass
  public static void tearDownClass() {
    TestDbServer.stop();
  }

  @Test
  public void addAddress_shouldAddNewAddress() {
    Short addressId = addressService.addAddress(newAddress());
    assertThat(addressId, notNullValue());
  }

}
