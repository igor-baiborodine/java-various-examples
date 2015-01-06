package org.mybatis.jpetstore.persistence.mongodb;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.junit.Test;
import org.mybatis.jpetstore.domain.Category;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mybatis.jpetstore.persistence.helper.TestBuilderFactory.createCategoryBuilderWithAllFields;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

/**
 * JUnit based unit tests for the {@link CategoryDao} class.
 *
 * @author Igor Baiborodine
 */
public class CategoryDaoTest
        extends AbstractBaseDaoTest {

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public String getCollectionName() {

        return categoryDao.getCollectionName();
    }

    @Test(expected = NullPointerException.class)
    public void getCategory_shouldThrowNPEForNullCategoryIdArgument() {

        categoryDao.getCategory(null);
    }

    @Test(expected = NullPointerException.class)
    public void getCategory_shouldThrowNPEForEmptyCategoryIdArgument() {

        categoryDao.getCategory("");
    }

    @Test
    public void getCategory_shouldFindExistingCategory() {

        Category existingCategory = createCategoryBuilderWithAllFields().build();
        insertCategory(existingCategory);

        Category category = categoryDao.getCategory(existingCategory.getCategoryId());
        assertReflectionEquals(existingCategory, category);
    }

    @Test
    public void getCategoryList_shouldFindListWithExistingCategories() {

        Category existingCategory = createCategoryBuilderWithAllFields().build();
        insertCategory(existingCategory);

        List<Category> categories = categoryDao.getCategoryList();
        assertThat(categories.size(), equalTo(1));
        assertReflectionEquals(existingCategory, categories.get(0));
    }

    private void insertCategory(final Category category) {

        collection.insert(category.toDBObject());

        DBObject categoryObj = collection.findOne(
                new BasicDBObject("_id", category.getCategoryId()));
        assertNotNull("Cannot find category with id["
                + category.getCategoryId() + "]", categoryObj);
    }

}