package org.mybatis.jpetstore.persistence.jpa;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mybatis.jpetstore.domain.Category;
import org.mybatis.jpetstore.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.mybatis.jpetstore.persistence.helper.TestBuilderFactory.createCategoryBuilderWithAllFields;
import static org.mybatis.jpetstore.persistence.helper.TestBuilderFactory.createProductBuilderWithAllFields;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

/**
 * @author Igor Baiborodine
 */
public class ProductBasePersistenceTest
        extends AbstractBasePersistenceTest {

    @Autowired
    private ProductBasePersistence persistence;

    private Category category;
    private Product product;

    @Before
    public void setUp() throws Exception {
        persistence.clearContext();
        cleanDatabase(persistence.getEntityManager());

        category = createCategoryBuilderWithAllFields().build();
        persistence.getEntityManager().persist(category);
        persistence.flushContext();

        product = createProductBuilderWithAllFields(category).build();
        persistence.getEntityManager().persist(product);

        persistence.flushContext();
        persistence.detachEntity(product);
        assertFalse(persistence.contextContains(product));
    }

    @After
    public void tearDown() throws Exception {
        category = null;
        product = null;
    }

    @Test
    public void getsAnExistingProduct() {
        Product existingProduct = persistence.getProduct(product.getProductId());
        assertReflectionEquals(product, existingProduct);
    }

    @Test
    public void getsAProductListByCategory() {
        List<Product> products = persistence.getProductListByCategory(category.getCategoryId());
        assertThat(products.size(), equalTo(1));
        assertReflectionEquals(product, products.get(0));
    }

    @Test
    public void searchesAProductListByName() {
        List<Product> products = persistence.searchProductList(product.getName().toLowerCase());
        assertThat(products.size(), equalTo(1));
        assertReflectionEquals(product, products.get(0));
        assertReflectionEquals(category, product.getCategory());
    }
}
