package org.mybatis.jpetstore.persistence.jpa;

import org.mybatis.jpetstore.domain.Category;
import org.mybatis.jpetstore.domain.Product;
import org.mybatis.jpetstore.persistence.ProductMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * @author Igor Baiborodine
 */
@Repository
public class ProductBasePersistence
        extends AbstractBasePersistence<Product, String>
        implements ProductMapper {
    private Logger logger = LoggerFactory.getLogger(ProductBasePersistence.class);

    @Override
    public List<Product> getProductListByCategory(final String categoryId) {
        Category category = entityManager.find(Category.class, categoryId);
        Query query = createNamedQuery("Product.getProductListByCategory");
        query.setParameter("category", category);

        @SuppressWarnings("unchecked")
        List<Product> products = (List<Product>) query.getResultList();
        logger.info("Read [{}] products by category", products.size());
        return products;
    }

    @Override
    public Product getProduct(final String productId) {
        return read(productId);
    }

    @Override
    public List<Product> searchProductList(final String name) {
        Query query = createNamedQuery("Product.searchProductList");
        query.setParameter("name", name);

        @SuppressWarnings("unchecked")
        List<Product> products = (List<Product>) query.getResultList();
        logger.info("Read [{}] products by name", products.size());
        return products;
    }
}
