package com.kiroule.example.mybatis;

import com.kiroule.example.mybatis.address.Address;
import com.kiroule.example.mybatis.customer.Customer;

import org.apache.ibatis.session.SqlSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Date;

/**
 * @author Igor Baiborodine
 */
public class TestUtil {

  private static final Logger logger = LoggerFactory.getLogger(TestUtil.class);
  private static SqlSessionManager sqlSessionManager;

  static {
    try (InputStream inputStream = MybatisExampleUtil.class.getResourceAsStream(
        "/mybatisConfig-test.xml")) {
      sqlSessionManager = SqlSessionManager.newInstance(inputStream);
      logger.info("Created initial test SqlSessionManager[{}]", sqlSessionManager);
    } catch (Throwable t) {
      logger.error("Error while creating initial test SqlSessionManager:", t);
    }
  }

  public static SqlSessionManager getSqlSessionManager() {
    return sqlSessionManager;
  }

  public static Address newAddress() {
    Address address = new Address();

    address.setAddress("University St.");
    address.setCityId((short) 1);
    address.setPhone("111-222-3333");
    address.setPostalCode("H2Y 1C6");
    address.setDistrict("Quebec");
    address.setLastUpdate(new Date());

    return address;
  }

  public static Customer newCustomer() {
    Customer customer = new Customer();

    customer.setFirstName("Bender");
    customer.setLastName("Rodriguez");
    customer.setEmail("bender.rodriquez@planetexpress.earth");
    customer.setStoreId((byte) 1);
    customer.setActive(true);
    customer.setCreateDate(new Date());

    return customer;
  }
}
