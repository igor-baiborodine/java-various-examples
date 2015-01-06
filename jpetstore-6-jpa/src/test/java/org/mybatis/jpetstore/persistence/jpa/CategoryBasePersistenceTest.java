package org.mybatis.jpetstore.persistence.jpa;

import org.junit.Before;
import org.junit.Test;
import org.mybatis.jpetstore.domain.Category;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.mybatis.jpetstore.persistence.helper.TestBuilderFactory.createCategoryBuilderWithAllFields;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

/**
 * @author Igor Baiborodine
 */
public class CategoryBasePersistenceTest
        extends AbstractBasePersistenceTest {

    @Autowired
    private CategoryBasePersistence persistence;

    @Before
    public void setUp() throws Exception {
        persistence.clearContext();
        cleanDatabase(persistence.getEntityManager());
    }

    @Test
    public void getsAnExistingCategory() {
        Category persistedCategory = createCategoryBuilderWithAllFields().build();
        String categoryId = persistedCategory.getCategoryId();
        persistence.getEntityManager().persist(persistedCategory);

        persistence.flushContext();
        persistence.detachEntity(persistedCategory);
        assertFalse(persistence.contextContains(persistedCategory));

        Category existingCategory = persistence.getCategory(categoryId);
        assertReflectionEquals(persistedCategory, existingCategory);
    }

    @Test
    public void getsACategoryList() {
        Category persistedCategory = createCategoryBuilderWithAllFields().build();
        persistence.getEntityManager().persist(persistedCategory);

        persistence.flushContext();
        persistence.detachEntity(persistedCategory);
        assertFalse(persistence.contextContains(persistedCategory));

        List<Category> categories = persistence.getCategoryList();
        assertThat(categories.size(), equalTo(1));
        assertReflectionEquals(persistedCategory, categories.get(0));
    }
}
