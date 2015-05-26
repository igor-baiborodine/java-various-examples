package org.mybatis.jpetstore.util;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Properties;

import static com.google.common.base.Strings.isNullOrEmpty;
import static org.springframework.data.simpledb.attributeutil.AmazonSimpleDBUtil.encodeAsRealNumber;

/**
 * @author Igor Baiborodine
 */
public class InitialDataLoadUtils {

  private static final Logger logger = LoggerFactory.getLogger(InitialDataLoadUtils.class);

  @Autowired
  private MongoClient mongoClient;
  @Value("${mongodb.db.name}")
  private String dbName;
  private DBCollection collection;

  private String dbPropertiesFile;
  private Properties properties = new Properties();

  public InitialDataLoadUtils(String dbPropertiesFile) {
    this.dbPropertiesFile = dbPropertiesFile;
  }

  private void init() throws IOException {

    try (InputStream in = InitialDataLoadUtils.class.getResourceAsStream("/" + dbPropertiesFile)) {
      properties.load(in);
    }
    logger.info("Loaded database properties[{}]", properties);
  }

  public void importInitialDataToMongo() {

    for (Object key : properties.keySet()) {

      if (((String) key).contains("mongodb.db.collection.")) {

        String collectionName = (String) properties.get(key);
        JSONArray jsonArray = readJSONFile(collectionName);
        importCollection(collectionName, jsonArray);
      }
    }
  }

  public JSONArray readJSONFile(final String collectionName) {

    logger.info("Read file for collection[{}]: starting...", collectionName);

    JSONArray result = new JSONArray();
    URL url = InitialDataLoadUtils.class
        .getResource("/dataload/collections/" + collectionName + ".json");
    if (url == null) {
      logger.warn("File does not exist for collection[{}]", collectionName);
      return result;
    }
    String file = url.getFile();

    try {
      JSONObject jsonObj = (JSONObject) new JSONParser().parse(new FileReader(file));
      result = (JSONArray) jsonObj.get(collectionName);
    } catch (IOException | ParseException e) {
      e.printStackTrace();
    }
    logger.info("Fetched [{}] JSON objects from file[{}]", result.size(), file);

    return result;
  }

  public void importCollection(final String collectionName, final JSONArray jsonArray) {

    logger.info("Import to collection[{}]: starting...", collectionName);
    collection = mongoClient.getDB(dbName).getCollection(collectionName);
    int count = 0;

    for (Object jsonObj : jsonArray) {
      DBObject document = (DBObject) JSON.parse(jsonObj.toString());

      if (collectionName.equals("items")) {
        encodeBigDecimals(document);
      }
      DBObject query = new BasicDBObject("_id", document.get("_id"));
      if (collection.findOne(query) == null) {
        collection.insert(document);
        count++;
      }
    }
    logger.info("Imported [{}] documents to collection[{}]", count, collectionName);

  }

  private void encodeBigDecimals(final DBObject document) {

    String listPrice = (String) document.get("list_price");
    if (!isNullOrEmpty(listPrice)) {
      document.put("list_price", encodeAsRealNumber(new BigDecimal(listPrice.trim())));
    }
    String unitCost = (String) document.get("unit_cost");
    if (!isNullOrEmpty(unitCost)) {
      document.put("unit_cost", encodeAsRealNumber(new BigDecimal(unitCost.trim())));
    }
  }

}
