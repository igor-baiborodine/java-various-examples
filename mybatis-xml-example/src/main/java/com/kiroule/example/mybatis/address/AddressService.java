package com.kiroule.example.mybatis.address;

import org.apache.ibatis.session.SqlSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Igor Baiborodine
 */
public class AddressService {

  private static final Logger logger = LoggerFactory.getLogger(AddressService.class);
  private SqlSessionManager sqlSessionManager;
  private AddressMapper addressMapper;

  public AddressService(@Nonnull SqlSessionManager sqlSessionManager) {
    checkNotNull(sqlSessionManager, "Argument[sqlSessionManager] must not be null");

    this.sqlSessionManager = sqlSessionManager;
    addressMapper = sqlSessionManager.getMapper(AddressMapper.class);
  }

  @Nonnull
  public Short addAddress(Address address) {
    addressMapper.insert(address);
    logger.info("Added address[{}]", address);
    return address.getAddressId();
  }

  @Nullable
  public Address getAddress(short id) {
    Address address = addressMapper.selectByPrimaryKey(id);
    logger.info("Found address[{}]", address);
    return address;
  }
}
