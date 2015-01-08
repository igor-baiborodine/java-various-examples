package org.mybatis.jpetstore.persistence.jpa;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mybatis.jpetstore.domain.Category;
import org.mybatis.jpetstore.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mybatis.jpetstore.domain.Domain.PRODUCT;
import static org.mybatis.jpetstore.persistence.helper.TestBuilderFactory.createCategoryBuilderWithAllFields;
import static org.mybatis.jpetstore.persistence.helper.TestBuilderFactory.createProductBuilderWithAllFields;

/**
 * @author Igor Baiborodine
 */
public class ProductBasePersistenceTest extends AbstractBasePersistenceTest {

    @Autowired
    private ProductBasePersistence persistence;

    private Category existingCategory;
    private Product existingProduct;

    @Before
    public void setUp() throws Exception {

        cleanDomain(PRODUCT);

        existingCategory = createCategoryBuilderWithAllFields().build();
        existingProduct = createProductBuilderWithAllFields(existingCategory).build();

        persistence.create(existingProduct);
        Thread.sleep(2000);
        assertCount(PRODUCT, 1);
    }

    @After
    public void tearDown() throws Exception {

        existingCategory = null;
        existingProduct = null;
    }

    @Test
    public void getProduct_shouldFindExistingProduct() {

        Product product = persistence.getProduct(existingProduct.getProductId());
        assertThat(existingProduct.compareTo(product), is(0));
    }

    @Test
    public void getProductListByCategory_shouldFindListWithExistingProductByCategory() {

        List<Product> products = persistence.getProductListByCategory(existingCategory.getCategoryId());
        assertThat(products.size(), is(1));
        assertThat(existingProduct.compareTo(products.get(0)), is(0));
    }

    @Test
    public void searchProductListByName_shouldFindListWithExistingProductByName() {

        List<Product> products = persistence.searchProductList(existingProduct.getName());
        assertThat(products.size(), is(1));
        assertThat(existingProduct.compareTo(products.get(0)), is(0));
    }

}
