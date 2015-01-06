package com.kiroule.example.mybatis.address;

import com.kiroule.example.mybatis.MybatisExampleUtil;
import org.junit.Test;

import static com.kiroule.example.mybatis.TestUtil.newAddress;
import static org.junit.Assert.assertNotNull;

/**
 * @author Igor Baiborodine
 */
public class AddressServiceTest {

    private AddressService addressService =
            new AddressService(MybatisExampleUtil.getSqlSessionManager());

    @Test
    public void addAddress_shouldAddNewAddress() {
        Short addressId = addressService.addAddress(newAddress());
        assertNotNull("add address failed - address id must not be null", addressId);
    }
}
