package org.mybatis.jpetstore.persistence.jpa;

import org.mybatis.jpetstore.domain.Category;
import org.mybatis.jpetstore.domain.Product;
import org.mybatis.jpetstore.persistence.ProductMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Igor Baiborodine
 */
@Repository
public class ProductBasePersistence
        extends AbstractBasePersistence<Product, String>
        implements ProductMapper {

    private static final Logger logger = LoggerFactory.getLogger(ProductBasePersistence.class);

    @Override
    public List<Product> getProductListByCategory(final String categoryId) {

        checkNotNull(categoryId, "Argument[categoryId] must not be null");
        // Query query = createNamedQuery("Product.getProductListByCategory");
        // Named queries not supported by SimpleJPA

        Category category = new Category("CAT-1");
        Product product = new Product("1", category);
        Query query = getEntityManager().createQuery("select p from Product p where p.categoryId = :categoryId");
        query.setParameter("categoryId", categoryId);

        @SuppressWarnings("unchecked")
        List<Product> products = (List<Product>) query.getResultList();
        closeEntityManager();
        logger.info("Read [{}] products by category", products.size());

        return products;
    }

    @Override
    public Product getProduct(final String productId) {
        return read(productId);
    }

    @Override
    public List<Product> searchProductList(final String name) {

        // Search is case-sensitive since LOWER() not supported by SimpleJPA

        // Query query = createNamedQuery("Product.searchProductList");
        // Named queries not supported by SimpleJPA

        // Query query = getEntityManager().createQuery("select p from Product p where p.name like :name");
        // LOWER() string expression not supported by SimpleJPA

        Query query = getEntityManager().createQuery("select p from Product p where p.name like :name");
        query.setParameter("name", "%" + name + "%");

        @SuppressWarnings("unchecked")
        List<Product> products = (List<Product>) query.getResultList();
        closeEntityManager();
        logger.info("Read [{}] products by name[{}]", products.size(), name);

        return products;
    }
}
