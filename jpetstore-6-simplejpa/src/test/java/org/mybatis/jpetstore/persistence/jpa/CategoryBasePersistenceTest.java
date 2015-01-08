package org.mybatis.jpetstore.persistence.jpa;

import org.junit.Before;
import org.junit.Test;
import org.mybatis.jpetstore.domain.Category;
import org.mybatis.jpetstore.domain.Domain;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mybatis.jpetstore.persistence.helper.PersistenceReflection.assertHaveSamePersistentFields;
import static org.mybatis.jpetstore.persistence.helper.TestBuilderFactory.createCategoryBuilderWithAllFields;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

/**
 * @author Igor Baiborodine
 */
public class CategoryBasePersistenceTest extends AbstractBasePersistenceTest {

    @Autowired
    private CategoryBasePersistence persistence;

    @Before
    public void setUp() throws Exception {

        cleanDomain(Domain.CATEGORY);
    }

    @Test
    public void getCategory_shouldFindExistingCategory() {

        Category existingCategory = createCategoryBuilderWithAllFields().build();
        persistence.create(existingCategory);

        Category category = persistence.getCategory(existingCategory.getCategoryId());
        assertHaveSamePersistentFields(existingCategory, category);
    }

    @Test
    public void getCategoryList_shouldFindListWithExistingCategories() {

        Category existingCategory = createCategoryBuilderWithAllFields().build();
        persistence.create(existingCategory);

        List<Category> categories = persistence.getCategoryList();
        assertThat(categories.size(), is(1));
        assertReflectionEquals(existingCategory, categories.get(0));
    }
}
