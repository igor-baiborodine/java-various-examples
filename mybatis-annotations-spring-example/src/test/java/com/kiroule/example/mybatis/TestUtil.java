package com.kiroule.example.mybatis;

import com.kiroule.example.mybatis.address.Address;
import com.kiroule.example.mybatis.customer.Customer;

import java.util.Date;

/**
 * @author Igor Baiborodine
 */
public class TestUtil {

    public static Address newAddress() {
        Address address = new Address();

        address.setAddress("University St.");
        address.setCityId((short) 1);
        address.setPhone("111-222-3333");
        address.setPostalCode("H2Y 1C6");
        address.setDistrict("Quebec");

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
