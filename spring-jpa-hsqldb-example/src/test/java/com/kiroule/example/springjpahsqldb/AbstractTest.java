package com.kiroule.example.springjpahsqldb;

import com.kiroule.example.springjpahsqldb.config.ApplicationConfig;

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
    @ContextConfiguration(classes = ApplicationConfig.class)})
@ActiveProfiles("test")
public class AbstractTest {

}
