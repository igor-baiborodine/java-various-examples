package org.mybatis.jpetstore.persistence.mongodb;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import org.mybatis.jpetstore.domain.Product;
import org.mybatis.jpetstore.persistence.ProductMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.emptyToNull;

/**
 * @author Igor Baiborodine
 */
public class ProductDao
    extends AbstractBaseDao<Product, String>
    implements ProductMapper {

  private static final Logger logger = LoggerFactory.getLogger(ProductDao.class);

  public ProductDao(final String collectionName) {

    super(collectionName);
  }

  public List<Product> getProductListByCategory(final String categoryId) {

    checkNotNull(emptyToNull(categoryId), "Argument[categoryId] must not be empty or null");

    List<Product> products = new ArrayList<>();
    DBObject query = new BasicDBObject("category_id", categoryId);

    try (DBCursor cursor = collection.find(query).sort(new BasicDBObject("product_id", 1))) {
      while (cursor.hasNext()) {
        DBObject productObj = cursor.next();
        products.add(Product.fromDBObject(productObj));
      }
    }
    logger.info("Found for category id [{}] [{}] product(s)", categoryId, products.size());

    return products;
  }

  public Product getProduct(final String productId) {

    checkNotNull(emptyToNull(productId), "Argument[productId] must not be empty or null");

    Product product = super.read(productId);
    logger.info("Found for id [{}] following product[{}]", productId, product);

    return product;
  }

  public List<Product> searchProductList(final String keyword) {

    checkNotNull(emptyToNull(keyword), "Argument[keyword] must not be empty or null");

    List<Product> products = new ArrayList<>();
    // old implementation
    //DBObject regex = new BasicDBObject("$regex", ".*" + keyword + ".*").append("$options", "i");
    //DBObject query = new BasicDBObject("name", regex);
    DBObject query = new BasicDBObject("name_keywords", keyword.toLowerCase());

    try (DBCursor cursor = collection.find(query).sort(new BasicDBObject("product_id", 1))) {
      while (cursor.hasNext()) {
        DBObject productObj = cursor.next();
        products.add(Product.fromDBObject(productObj));
      }
    }
    logger.info("Found for keyword [{}] [{}] product(s)", keyword, products.size());

    return products;
  }
}
