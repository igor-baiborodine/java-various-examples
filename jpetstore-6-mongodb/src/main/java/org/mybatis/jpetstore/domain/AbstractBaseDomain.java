package org.mybatis.jpetstore.domain;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import java.math.BigDecimal;

import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * @author Igor Baiborodine
 */
public abstract class AbstractBaseDomain<ID> {

  public abstract ID getId();

  public abstract DBObject toDBObject();

  protected void appendTo(BasicDBObject dbObject, String key, Object value) {

    if (value instanceof BigDecimal) {
      dbObject.append(key, value.toString());
    } else if (value instanceof String && (!isNullOrEmpty((String) value))
        || value != null) {
      dbObject.append(key, value);
    }
  }

}
