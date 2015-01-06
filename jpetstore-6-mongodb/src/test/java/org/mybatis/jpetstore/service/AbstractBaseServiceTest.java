package org.mybatis.jpetstore.service;

/**
 * @author Igor Baiborodine
 */

import com.mongodb.DB;
import com.mongodb.MongoClient;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mybatis.jpetstore.util.InitialDataLoadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Base class for all Service test classes.
 *
 * @author Igor Baiborodine
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:application-context-test.xml")
public abstract class AbstractBaseServiceTest {

    @Autowired
    protected MongoClient mongoClient;

    @Value("${mongodb.db.name}")
    protected String dbName;

    @Autowired
    private InitialDataLoadUtils helper;

    protected DB database;

    @Before
    public void setUp() {

        database = mongoClient.getDB(dbName);
        database.dropDatabase();

        database = mongoClient.getDB(dbName);
        assertThat(database.getCollectionNames().size(), equalTo(0));
        helper.importInitialDataToMongo();
    }

    @After
    public void tearDown() {

        database.dropDatabase();
        database = null;
    }

}
