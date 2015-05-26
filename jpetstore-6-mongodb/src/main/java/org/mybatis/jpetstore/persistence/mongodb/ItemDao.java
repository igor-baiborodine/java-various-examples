package org.mybatis.jpetstore.persistence.mongodb;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import org.mybatis.jpetstore.domain.Item;
import org.mybatis.jpetstore.persistence.ItemMapper;
import org.mybatis.jpetstore.persistence.NumericValueUpdateParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static com.google.common.base.Strings.emptyToNull;
import static org.mybatis.jpetstore.persistence.NumericValueUpdateParam.ID;
import static org.mybatis.jpetstore.persistence.NumericValueUpdateParam.INCREMENT;
import static org.mybatis.jpetstore.persistence.NumericValueUpdateParam.KEY;

/**
 * @author Igor Baiborodine
 */
public class ItemDao
    extends AbstractBaseDao<Item, String>
    implements ItemMapper {

  private static final Logger logger = LoggerFactory.getLogger(ItemDao.class);

  public ItemDao(final String collectionName) {
    super(collectionName);
  }

  public void updateInventoryQuantity(final Map<NumericValueUpdateParam, Object> params) {

    checkNotNull(params, "Argument[param] must not be null");
    checkState(params.size() == 3,
        String.format("Argument[params] has illegal size[%d]", params.size()));
    String itemId = (String) params.get(ID);
    String key = (String) params.get(KEY);
    Integer increment = (Integer) params.get(INCREMENT);

    int itemQty = getInventoryQuantity(itemId);
    int value = itemQty - increment;

    BasicDBObject query = new BasicDBObject("_id", itemId);
    BasicDBObject update = new BasicDBObject("$set", new BasicDBObject(key, value));
    collection.update(query, update);
  }

  public int getInventoryQuantity(final String itemId) {

    checkNotNull(emptyToNull(itemId), "Argument[itemId] must not be empty or null");

    BasicDBObject field = new BasicDBObject("quantity", 1);
    BasicDBObject query = new BasicDBObject("_id", itemId);
    int quantity = -1;

    try (DBCursor cursor = collection.find(query, field)) {
      while (cursor.hasNext()) {
        DBObject quantityObj = cursor.next();
        quantity = ((BasicDBObject) quantityObj).getInt("quantity");
      }
    }
    logger.info("Found quantity[{}] for item id[{}]", quantity, itemId);

    return quantity;
  }

  public List<Item> getItemListByProduct(final String productId) {

    checkNotNull(emptyToNull(productId), "Argument[productId] must not be empty or null");

    List<Item> items = new ArrayList<>();
    DBObject query = new BasicDBObject("product.product_id", productId);

    try (DBCursor cursor = collection.find(query).sort(new BasicDBObject("item_id", 1))) {
      while (cursor.hasNext()) {
        DBObject itemObj = cursor.next();
        items.add(Item.fromDBObject(itemObj));
      }
    }
    logger.info("Found for product id [{}] [{}] item(s)", productId, items.size());

    return items;
  }

  public Item getItem(final String itemId) {

    checkNotNull(emptyToNull(itemId), "Argument[itemId] must not be empty or null");

    Item item = super.read(itemId);
    logger.info("Found for id[{}] following item[{}]", itemId, item);

    return item;
  }

}
