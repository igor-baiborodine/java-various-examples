package org.mybatis.jpetstore.persistence.mongodb;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteResult;

import org.mybatis.jpetstore.domain.AbstractBaseDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

/**
 * @author Igor Baiborodine
 */
abstract class AbstractBaseDao<T, ID> {

  @Autowired
  protected MongoClient mongoClient;
  @Value("${mongodb.db.name}")
  protected String dbName;

  protected String collectionName;
  protected DBCollection collection;
  protected Class<T> domainClazz;

  AbstractBaseDao(String collectionName) {
    this.collectionName = collectionName;
    ParameterizedType genericSuperclass = (ParameterizedType) getClass()
        .getGenericSuperclass();
    domainClazz = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
  }

  void init() {
    collection = mongoClient.getDB(dbName).getCollection(collectionName);
  }

  String getCollectionName() {
    return collectionName;
  }

  public T read(ID id) {

    DBObject dbObj = collection.findOne(new BasicDBObject("_id", id));
    Object domainObj = null;

    try {
      Method method = domainClazz.getMethod("fromDBObject", DBObject.class);
      domainObj = method.invoke(null, dbObj);
    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
      e.printStackTrace();
    }

    return (T) domainObj;
  }

  public void create(final T domainObj) throws MongoException.DuplicateKey {

    DBObject dbObj = ((AbstractBaseDomain) domainObj).toDBObject();
    collection.insert(dbObj);
  }

  public void update(final T domainObj) {

    DBObject query = new BasicDBObject("_id", ((AbstractBaseDomain) domainObj).getId());
    DBObject dbObj = ((AbstractBaseDomain) domainObj).toDBObject();

    WriteResult result = collection.update(query, dbObj);
    if (result.getN() == 0) {
      throw new RuntimeException("Cannot update non-existing document[" + dbObj + "]");
    }
  }

  public void delete(final ID id) {

    DBObject query = new BasicDBObject("_id", id);

    WriteResult result = collection.remove(query);
    if (result.getN() == 0) {
      throw new RuntimeException("Cannot delete non-existing document with id[" + id + "]");
    }
  }

}
