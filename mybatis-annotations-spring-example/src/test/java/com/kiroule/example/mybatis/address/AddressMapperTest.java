package com.kiroule.example.mybatis.address;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static com.kiroule.example.mybatis.TestUtil.newAddress;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Igor Baiborodine
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/spring-config.xml")
public class AddressMapperTest {

    @Resource
    private AddressMapper addressMapper;

    @Test
    public void insert_shouldInsertAddress() {
        Address address = newAddress();
        int count = addressMapper.insert(address);
        assertEquals("test insert failed - count must be 1", 1, count);
        assertNotNull("test insert failed - address id must not be null", address.getAddressId());
    }
}
