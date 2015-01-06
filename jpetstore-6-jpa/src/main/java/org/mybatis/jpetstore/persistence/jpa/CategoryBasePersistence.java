package org.mybatis.jpetstore.persistence.jpa;

import org.mybatis.jpetstore.domain.Category;
import org.mybatis.jpetstore.persistence.CategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * @author Igor Baiborodine
 */
@Repository
public class CategoryBasePersistence
        extends AbstractBasePersistence<Category, String>
        implements CategoryMapper {
    private Logger logger = LoggerFactory.getLogger(CategoryBasePersistence.class);

    @Override
    public List<Category> getCategoryList() {
        Query query = createNamedQuery("Category.getCategoryList");
        @SuppressWarnings("unchecked")
        List<Category> categories = (List<Category>) query.getResultList();
        logger.info("Read [{}] categories", categories.size());
        return categories;
    }

    @Override
    public Category getCategory(final String categoryId) {
        return read(categoryId);
    }
}
