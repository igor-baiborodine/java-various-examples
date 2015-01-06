package com.kiroule.example.mybatis.customer;

import com.kiroule.example.mybatis.MybatisExampleUtil;
import org.junit.Test;

import java.util.List;

import static com.kiroule.example.mybatis.TestUtil.newAddress;
import static com.kiroule.example.mybatis.TestUtil.newCustomer;
import static org.junit.Assert.*;

/**
 * @author Igor Baiborodine
 */
public class CustomerServiceTest {

    private CustomerService customerService =
            new CustomerService(MybatisExampleUtil.getSqlSessionManager());

    @Test
    public void getCustomer_shouldReturnNullForZeroCustomerId() {
        Customer customer = customerService.getCustomer(new Short("0"));
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
        Double minDollarAmountPurchased = new Double("1000000.0");
        List<Customer> rewardsReport = customerService.getCustomerRewardsReport(
                minMonthlyPurchases, minDollarAmountPurchased);
        assertNotNull("get customer rewards report failed - rewards report is null", rewardsReport);
        assertTrue("get customer rewards report failed - rewards report size is not 0",
                rewardsReport.size() == 0);
    }
}
