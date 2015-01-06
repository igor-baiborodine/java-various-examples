package org.mybatis.jpetstore.persistence.mongodb;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import org.mybatis.jpetstore.domain.Category;
import org.mybatis.jpetstore.persistence.CategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.emptyToNull;

/**
 * @author Igor Baiborodine
 */
public class CategoryDao
        extends AbstractBaseDao<Category, String>
        implements CategoryMapper {

    private static final Logger logger = LoggerFactory.getLogger(CategoryDao.class);

    public CategoryDao(final String collectionName) {

        super(collectionName);
    }

    public List<Category> getCategoryList() {

        List<Category> categories = new ArrayList<>();

        try (DBCursor cursor = collection.find().sort(new BasicDBObject("category_id", 1))) {
            while (cursor.hasNext()) {
                DBObject categoryObj = cursor.next();
                categories.add(Category.fromDBObject(categoryObj));
            }
        }
        logger.info("Found [{}] categories", categories.size());

        return categories;
    }

    public Category getCategory(final String categoryId) {

        checkNotNull(emptyToNull(categoryId), "Argument[categoryId] must not be empty or null");

        Category category = super.read(categoryId);
        logger.info("Found for id[{}] following category[{}]", categoryId, category);

        return category;
    }
}
