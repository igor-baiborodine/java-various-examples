package com.kiroule.example.mybatis.address;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static com.kiroule.example.mybatis.TestUtil.newAddress;
import static org.junit.Assert.assertNotNull;

/**
 * @author Igor Baiborodine
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/spring-config.xml")
public class AddressServiceTest {

    @Resource
    private AddressService addressService;

    @Test
    public void addAddress_shouldAddNewAddress() {
        Short addressId = addressService.addAddress(newAddress());
        assertNotNull("add address failed - address id must not be null", addressId);
    }
}
