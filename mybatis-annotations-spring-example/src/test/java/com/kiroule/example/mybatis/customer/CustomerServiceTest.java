package com.kiroule.example.mybatis.customer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

import static com.kiroule.example.mybatis.TestUtil.newAddress;
import static com.kiroule.example.mybatis.TestUtil.newCustomer;
import static org.junit.Assert.*;

/**
 * @author Igor Baiborodine
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/spring-config.xml")
public class CustomerServiceTest {

    @Resource
    private CustomerService customerService;

    @Test
    public void getCustomer_shouldReturnNullForZeroCustomerId() {
        Customer customer = customerService.getCustomer((short) 0);
        assertNull("get customer failed - customer must be null", customer);
    }

    @Test
    public void addCustomer_shouldAddNewCustomer() {
        Short customerId = customerService.addCustomer(newCustomer(), newAddress());
        assertNotNull("add customer failed - address id must not be null", customerId);
    }

    @Test
    public void getCustomerRewardsReport_shouldReturnEmptyRewardsReport() {
        Byte minMonthlyPurchases = Byte.MAX_VALUE;
        Double minDollarAmountPurchased = 1000000.0;
        List<Customer> rewardsReport = customerService.getCustomerRewardsReport(
                minMonthlyPurchases, minDollarAmountPurchased);
        assertNotNull("get customer rewards report failed - rewards report is null", rewardsReport);
        assertTrue("get customer rewards report failed - rewards report size must be 0",
                rewardsReport.size() == 0);
    }
}
