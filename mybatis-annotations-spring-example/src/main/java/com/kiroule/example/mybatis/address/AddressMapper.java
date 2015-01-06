package com.kiroule.example.mybatis.address;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.SelectKey;

public interface AddressMapper {
    String INSERT = "insert into address (address, address2, district, city_id, postal_code, phone, last_update)"
            + " values (#{address,jdbcType=VARCHAR},"
            + " #{address2,jdbcType=VARCHAR}, #{district,jdbcType=VARCHAR}, #{cityId,jdbcType=SMALLINT},"
            + " #{postalCode,jdbcType=VARCHAR}, #{phone,jdbcType=VARCHAR}, #{lastUpdate,jdbcType=TIMESTAMP})";

    @Insert(INSERT)
    @Options(useGeneratedKeys = true, keyProperty = "address_id")
    @SelectKey(statement = "SELECT LAST_INSERT_ID();",
            before = false, keyProperty = "addressId", resultType = short.class)
    int insert(Address record);
}