package org.mybatis.jpetstore.persistence.mongodb;

/**
 * @author Igor Baiborodine
 */

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Base class for all DAO test classes.
 *
 * @author Igor Baiborodine
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:application-context-test.xml")
public abstract class AbstractBaseDaoTest {

    @Autowired
    protected MongoClient mongoClient;

    @Value("${mongodb.db.name}")
    protected String dbName;

    protected DB database;
    protected DBCollection collection;

    public abstract String getCollectionName();

    @Before
    public void setUp() {

        database = mongoClient.getDB(dbName);
        collection = database.getCollection(getCollectionName());
        assertThat(collection.count(), is(0L));
    }

    @After
    public void tearDown() {

        collection.drop();
        collection = null;
        database = null;
    }

}
