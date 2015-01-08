package org.mybatis.jpetstore.util;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.jpetstore.domain.Category;
import org.mybatis.jpetstore.domain.Domain;
import org.mybatis.jpetstore.persistence.jpa.CategoryBasePersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mybatis.jpetstore.domain.Domain.CATEGORY;
import static org.mybatis.jpetstore.domain.Domain.ITEM;

/**
 * JUnit based unit tests for the {@link org.mybatis.jpetstore.util.InitialDataLoadUtils} class.
 *
 * @author Igor Baiborodine
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:persistence-context-test.xml")
public class InitialDataLoadUtilsTest {

    @Autowired
    private DatabaseUtils databaseUtils;

    @Autowired
    private InitialDataLoadUtils loadUtils;

    @Autowired
    private CategoryBasePersistence categoryBasePersistence;

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void importInitialDataToSimpleDB_shouldImportInitialDataSetForSupplierDomain() {

        Domain domain = ITEM;

        databaseUtils.cleanDomain(domain);
        loadUtils.importInitialDataToSimpleDB(domain);

        JSONArray jsonArray = loadUtils.readJSONFile(domain);
        databaseUtils.assertCount(domain, jsonArray.size());
    }

    @Test
    public void importDomain_shouldImportCategoryDomainData() {

        databaseUtils.cleanDomain(CATEGORY);

        JSONArray categoriesJson = new JSONArray();
        JSONObject categoryJson = getCategoryJson();
        categoriesJson.add(categoryJson);

        loadUtils.importDomain(CATEGORY, categoriesJson);
        databaseUtils.assertCount(CATEGORY, 1);

        String categoryId = (String) categoryJson.get("itemName()");
        Category category = categoryBasePersistence.getCategory(categoryId);
        assertThat(category, notNullValue());

        assertThat(category.getCategoryId(), is((String) categoryJson.get("itemName()")));
        assertThat(category.getName(), is((String) categoryJson.get("name")));
        assertThat(category.getDescription(), is((String) categoryJson.get("description")));
    }

    private JSONObject getCategoryJson() {

        JSONObject result = new JSONObject();
        result.put("itemName()", "FISH");
        result.put("name", "Fish");
        result.put("description",
                "<image src='../images/fish_icon.gif'><font size='5' color='blue'> Fish</font>");
        return result;
    }
}
