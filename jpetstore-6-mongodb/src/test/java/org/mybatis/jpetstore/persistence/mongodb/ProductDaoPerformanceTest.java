package org.mybatis.jpetstore.persistence.mongodb;

import com.ibm.icu.text.RuleBasedNumberFormat;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import org.junit.Ignore;
import org.junit.Test;
import org.mybatis.jpetstore.domain.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mybatis.jpetstore.persistence.helper.TestBuilderFactory.createProductBuilderWithBaseFields;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

/**
 * JUnit based performance unit tests for the {@link org.mybatis.jpetstore.persistence.mongodb.ProductDao} class.
 *
 * @author Igor Baiborodine
 */
public class ProductDaoPerformanceTest
        extends AbstractBaseDaoTest {

    private static final Logger logger = LoggerFactory.getLogger(ProductDaoPerformanceTest.class);

    private RuleBasedNumberFormat speller = new RuleBasedNumberFormat(RuleBasedNumberFormat.SPELLOUT);

    @Autowired
    private ProductDao productDao;

    private BasicDBObject explain;
    private Product productWithKeyword;

    @Override
    public String getCollectionName() {

        return productDao.getCollectionName();
    }

    @Test
    @Ignore
    public void searchProductList_shouldFindListWithProductsWithNameContainingKeyword() {

        // tested on Acer with Intel i7 8-core, 8 GB RAM, Windows 7
        long maxNumber = 1000000L;
        String categoryId = "NUMBER";
        String keyword = "million";
        Product product = null;

        // populate collection
        for (long number = 1L; number <= maxNumber; number++) {

            product = createProductBuilderWithBaseFields("NUMBER-" + number, categoryId).build();
            product.setName(speller.format(number));
            collection.insert(product.toDBObject());
            if (number % 10000L == 0) {
                logger.info("Inserted {} products", number);
            }
        }
        logger.info("Last inserted product[{}]", product);

        assertThat(collection.count(), equalTo(maxNumber));

        // test performance of old implementation
        DBObject regex = new BasicDBObject("$regex", ".*" + keyword + ".*");
        DBObject nameRegexQuery = new BasicDBObject("name", regex);

        findProduct(nameRegexQuery);
        assertReflectionEquals(product, productWithKeyword);
        assertThat(explain.getBoolean("isMultiKey"), equalTo(false));
        assertThat(explain.getLong("nscanned"), equalTo(maxNumber));
        // "millis" : 6929

        collection.ensureIndex(new BasicDBObject("name", 1));

        findProduct(nameRegexQuery);

        assertReflectionEquals(product, productWithKeyword);
        assertThat(explain.getBoolean("isMultiKey"), equalTo(false));
        assertThat(explain.getLong("nscanned"), equalTo(maxNumber));
        // "millis" : 7561

        // test performance of new implementation
        DBObject nameKeywordsQuery = new BasicDBObject("name_keywords", keyword);

        findProduct(nameKeywordsQuery);

        assertReflectionEquals(product, productWithKeyword);
        assertThat(explain.getBoolean("isMultiKey"), equalTo(false));
        assertThat(explain.getLong("nscanned"), equalTo(maxNumber));
        // "millis" : 1633

        collection.ensureIndex(new BasicDBObject("name_keywords", 1));

        findProduct(nameKeywordsQuery);

        assertReflectionEquals(product, productWithKeyword);
        assertThat(explain.getBoolean("isMultiKey"), equalTo(true));
        assertThat(explain.getLong("nscanned"), equalTo(1L));
        // "millis" : 0

    }

    private void findProduct(DBObject query) {

        try (DBCursor cursor = collection.find(query)) {
            explain = (BasicDBObject) cursor.explain();

            while (cursor.hasNext()) {
                DBObject productObj = cursor.next();
                productWithKeyword = Product.fromDBObject(productObj);
            }
        }
        logger.info("explain[{}]", explain);

    }

}
