package com.kiroule.example.mybatis.service;

import com.kiroule.example.mybatis.domain.Address;
import com.kiroule.example.mybatis.domain.Customer;
import com.kiroule.example.mybatis.mapper.AddressMapper;
import com.kiroule.example.mybatis.mapper.CustomerMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Igor Baiborodine
 */
public class CustomerService {

  private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);
  private CustomerMapper customerMapper;
  private AddressMapper addressMapper;

  @Autowired
  public void setCustomerMapper(@Nonnull CustomerMapper customerMapper) {
    this.customerMapper = customerMapper;
  }

  @Autowired
  public void setAddressMapper(@Nonnull AddressMapper addressMapper) {
    this.addressMapper = addressMapper;
  }

  @Nullable
  public Customer getCustomer(@Nonnull Short customerId) {
    checkNotNull(customerId, "Argument[customerId] must not be null");

    Customer customer = customerMapper.selectByPrimaryKey(customerId);
    logger.info("Found customer[{}]", customer);
    return customer;
  }

  @Nonnull
  @Transactional
  public Short addCustomer(@Nonnull Customer customer, @Nonnull Address address) {
    checkNotNull(customer, "Argument[customer] must not be null");
    checkNotNull(address, "Argument[address] must not be null");

    addressMapper.insert(address);
    assert (address.getAddressId() != 0);

    customer.setAddressId(address.getAddressId());
    customerMapper.insert(customer);
    assert (customer.getCustomerId() != 0);

    logger.info("Added customer[{}]", customer);
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

    List<Customer> rewardsReport = customerMapper.getRewardsReport(params);
    Integer rewardeesCount = (Integer) params.get("count_rewardees");
    logger.info(String.format("Rewardees count[%d]", rewardeesCount));

    return rewardsReport;
  }
}
