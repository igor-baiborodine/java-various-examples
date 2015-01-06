package com.kiroule.example.mybatis.customer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static com.kiroule.example.mybatis.TestUtil.newCustomer;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Igor Baiborodine
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/spring-config.xml")
public class CustomerMapperTest {

    @Resource
    private CustomerMapper customerMapper;

    @Test
    public void insert_shouldInsertCustomer() {
        Customer customer = newCustomer();
        customer.setAddressId(new Short("1"));
        int count = customerMapper.insert(customer);
        assertEquals("test insert failed - insert count must be 1", 1, count);
        assertNotNull("test insert failed - customer id must not be null", customer.getCustomerId());
    }
}
