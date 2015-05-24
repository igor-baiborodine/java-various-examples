package com.kiroule.example.restwebapp.util;

import com.google.gson.Gson;

import com.mongodb.DBObject;
import com.mongodb.util.JSON;

/**
 * @author Igor Baiborodine
 */
public class AppUtils {

  public static DBObject toDBObject(Object pojo) {
    String json = new Gson().toJson(pojo);
    return (DBObject) JSON.parse(json);
  }

  public static Object fromDBObject(DBObject dbObj, Class clazz) {
    String json = dbObj.toString();
    return new Gson().fromJson(json, clazz);
  }
}
