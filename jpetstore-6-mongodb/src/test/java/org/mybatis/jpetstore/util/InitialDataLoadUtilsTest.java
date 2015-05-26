package org.mybatis.jpetstore.util;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.jpetstore.domain.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Integration tests for the {@link org.mybatis.jpetstore.util.InitialDataLoadUtils} class.
 *
 * @author Igor Baiborodine
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:application-context-test.xml")
public class InitialDataLoadUtilsTest {

  @Autowired
  private InitialDataLoadUtils loadUtils;

  @Autowired
  private MongoClient mongoClient;

  @Value("${mongodb.db.name}")
  private String dbName;

  private DB database;
  private DBCollection collection;

  @Before
  public void setUp() {

    database = mongoClient.getDB(dbName);
  }

  @After
  public void tearDown() {

    database.dropDatabase();
    collection = null;
    database = null;
  }

  @Test
  public void importInitialDataToMongo_shouldImportInitialDataSet() {

    loadUtils.importInitialDataToMongo();
  }

  @Test
  public void importCollection_shouldImportCategoryCollection() {

    JSONArray categoriesJson = new JSONArray();
    JSONObject categoryJson = getCategoryJson();
    categoriesJson.add(categoryJson);

    String collectionName = "categories";
    loadUtils.importCollection(collectionName, categoriesJson);

    collection = database.getCollection(collectionName);
    assertThat(collection.count(), is((long) categoriesJson.size()));

    String _id = (String) categoryJson.get("_id");
    DBObject categoryDBObject = collection.findOne(new BasicDBObject("_id", _id));
    assertThat(categoryDBObject, notNullValue());

    Category category = Category.fromDBObject(categoryDBObject);
    assertThat(category.getId(), is(_id));
    assertThat(category.getCategoryId(), is((String) categoryJson.get("category_id")));
    assertThat(category.getName(), is((String) categoryJson.get("name")));
    assertThat(category.getDescription(), is((String) categoryJson.get("description")));
  }

  private JSONObject getCategoryJson() {

    JSONObject result = new JSONObject();
    result.put("_id", "FISH");
    result.put("category_id", "FISH");
    result.put("name", "Fish");
    result.put("description",
        "<image src='../images/fish_icon.gif'><font size='5' color='blue'> Fish</font>");
    return result;
  }
}
