package com.kiroule.example.mybatis.customer;

import com.kiroule.example.mybatis.address.Address;
import com.kiroule.example.mybatis.address.AddressMapper;
import org.apache.ibatis.session.SqlSessionManager;
import org.apache.ibatis.session.TransactionIsolationLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Igor Baiborodine
 */
public class CustomerService {

    private SqlSessionManager sqlSessionManager;
    private CustomerMapper customerMapper;
    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    public CustomerService(@Nonnull SqlSessionManager sqlSessionManager) {
        checkNotNull(sqlSessionManager, "Argument[sqlSessionManager] must not be null");

        this.sqlSessionManager = sqlSessionManager;
        this.customerMapper = sqlSessionManager.getMapper(CustomerMapper.class);
    }

    @Nullable
    public Customer getCustomer(@Nonnull Short customerId) {
        checkNotNull(customerId, "Argument[customerId] must not be null");

        Customer customer = customerMapper.selectByPrimaryKey(customerId);
        logger.info("Found customer[{}]", customer);
        return customer;
    }

    @Nonnull
    public Short addCustomer(@Nonnull Customer customer, @Nonnull Address address) {
        checkNotNull(customer, "Argument[customer] must not be null");
        checkNotNull(address, "Argument[address] must not be null");

        sqlSessionManager.startManagedSession(TransactionIsolationLevel.READ_COMMITTED);

        try {
            AddressMapper addressMapper = sqlSessionManager.getMapper(AddressMapper.class);
            addressMapper.insert(address);
            assert(address.getAddressId() > 0);

            customer.setAddressId(address.getAddressId());
            customerMapper.insert(customer);
            assert(customer.getCustomerId() > 0);

            sqlSessionManager.commit();
            logger.info("Added customer[{}]", customer);
        } catch (Throwable t) {
            sqlSessionManager.rollback();
            logger.error("Failed adding customer[{}], error[{}]", customer, t.getMessage());
            t.printStackTrace();
        }
        return customer.getCustomerId();
    }

    @Nonnull
    public List<Customer> getCustomerRewardsReport(
            @Nonnull Byte minMonthlyPurchases, @Nonnull Double minDollarAmountPurchased) {
        checkNotNull(minMonthlyPurchases, "Argument[minMonthlyPurchases] must not be null");
        checkNotNull(minDollarAmountPurchased, "Argument[minDollarAmountPurchased] must not be null");

        Map<String, Object> params = new HashMap<>();
        params.put("min_monthly_purchases", minMonthlyPurchases);
        params.put("min_dollar_amount_purchased", minDollarAmountPurchased);

        List<Customer> rewardsReport = customerMapper.getCustomerRewardsReport(params);
        Integer rewardeesCount = (Integer) params.get("count_rewardees");
        logger.info(String.format("Rewardees count[%d]", rewardeesCount));

        return rewardsReport;
    }
}
