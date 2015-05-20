package com.kiroule.example.mybatis;

import com.kiroule.example.mybatis.config.TestApplicationConfig;

import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Igor Baiborodine
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({
    @ContextConfiguration(classes = TestApplicationConfig.class)})
@ActiveProfiles("test")
public abstract class AbstractTest {
}
