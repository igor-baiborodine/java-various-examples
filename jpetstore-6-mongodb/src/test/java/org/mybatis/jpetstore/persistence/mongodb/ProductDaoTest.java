package org.mybatis.jpetstore.persistence.mongodb;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import org.junit.Test;
import org.mybatis.jpetstore.domain.Product;
import org.mybatis.jpetstore.domain.builder.ProductBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mybatis.jpetstore.persistence.helper.TestBuilderFactory.createProductBuilderWithAllFields;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

/**
 * Integration tests for the {@link ProductDao} class.
 *
 * @author Igor Baiborodine
 */
public class ProductDaoTest extends AbstractDaoTest {

  @Autowired
  private ProductDao productDao;

  @Override
  public String getCollectionName() {

    return productDao.getCollectionName();
  }

  @Test(expected = NullPointerException.class)
  public void getProduct_shouldThrowNPEForNullProductIdArgument() {

    productDao.getProduct(null);
  }

  @Test(expected = NullPointerException.class)
  public void getProduct_shouldThrowNPEForEmptyProductIdArgument() {

    productDao.getProduct("");
  }

  @Test
  public void getProduct_shouldFindExistingProduct() {

    Product existingProduct = createProductBuilderWithAllFields().build();
    insertProduct(existingProduct);

    Product product = productDao.getProduct(existingProduct.getProductId());
    assertReflectionEquals(existingProduct, product);
  }

  @Test(expected = NullPointerException.class)
  public void getProductListByCategory_shouldThrowNPEForNullCategoryIdArgument() {

    productDao.getProductListByCategory(null);
  }

  @Test(expected = NullPointerException.class)
  public void getProductListByCategory_shouldThrowNPEForEmptyCategoryIdArgument() {

    productDao.getProductListByCategory("");
  }

  @Test
  public void getProductListByCategory_shouldFindListWithExistingProducts() {

    Product existingProduct = createProductBuilderWithAllFields().build();
    insertProduct(existingProduct);

    List<Product> products = productDao.getProductListByCategory(existingProduct.getCategoryId());
    assertThat(products.size(), is(1));
    assertReflectionEquals(existingProduct, products.get(0));
  }

  @Test
  public void getProductListByCategory_shouldFindEmptyListForNonExistingCategory() {

    List<Product> products = productDao.getProductListByCategory("NON_EXISTING_CATEGORY");
    assertThat(products.size(), is(0));
  }

  @Test(expected = NullPointerException.class)
  public void searchProductList_shouldThrowNPEForNullKeywordArgument() {

    productDao.searchProductList(null);
  }

  @Test(expected = NullPointerException.class)
  public void searchProductList_shouldThrowNPEForEmptyKeywordArgument() {

    productDao.searchProductList("");
  }

  @Test
  public void searchProductList_shouldFindListWithProductsWithNameContainingKeyword() {

    ProductBuilder builder = createProductBuilderWithAllFields();
    builder.name("One Two Three");
    Product existingProduct = builder.build();
    insertProduct(existingProduct);

    List<Product> products = productDao.searchProductList("One");
    assertThat(products.size(), is(1));
    assertReflectionEquals(existingProduct, products.get(0));

    products = productDao.searchProductList("Two");
    assertThat(products.size(), is(1));

    products = productDao.searchProductList("Three");
    assertThat(products.size(), is(1));
  }

  private void insertProduct(final Product product) {

    collection.insert(product.toDBObject());

    DBObject productObj = collection.findOne(new BasicDBObject("_id", product.getProductId()));
    assertThat("Cannot find product with id[" + product.getProductId() + "]", productObj, notNullValue());
  }

}
