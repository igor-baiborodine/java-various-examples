package org.mybatis.jpetstore.persistence.jpa;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mybatis.jpetstore.domain.Category;
import org.mybatis.jpetstore.domain.Item;
import org.mybatis.jpetstore.domain.Product;
import org.mybatis.jpetstore.domain.Supplier;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.mybatis.jpetstore.persistence.helper.PersistenceReflection.assertHaveSamePersistentFields;
import static org.mybatis.jpetstore.persistence.helper.TestBuilderFactory.*;
import static org.mybatis.jpetstore.domain.ItemInventoryParam.ITEM_ID;
import static org.mybatis.jpetstore.domain.ItemInventoryParam.INCREMENT;

/**
 * @author Igor Baiborodine
 */
public class ItemBasePersistenceTest
        extends AbstractBasePersistenceTest {

    @Autowired
    private ItemBasePersistence persistence;

    private Category category;
    private Product product;
    private Supplier supplier;
    private Item item;

    @Before
    public void setUp() {
        persistence.clearContext();
        cleanDatabase(persistence.getEntityManager());

        category = createCategoryBuilderWithAllFields().build();
        persistence.getEntityManager().persist(category);
        persistence.flushContext();

        product = createProductBuilderWithAllFields(category).build();
        persistence.getEntityManager().persist(product);
        persistence.flushContext();

        supplier = createSupplierBuilderWithAllFields().build();
        persistence.getEntityManager().persist(supplier);
        persistence.flushContext();

        item = createItemBuilderWithAllFields(product).supplier(supplier).build();
        persistence.getEntityManager().persist(item);
        persistence.flushContext();

        persistence.detachEntity(item);
        assertFalse(persistence.contextContains(item));
    }

    @After
    public void tearDown() throws Exception {
        category = null;
        product = null;
        supplier = null;
        item = null;
    }

    @Test
    public void getsAnExistingItem() {
        Item existingItem = persistence.getItem(item.getItemId());
        assertHaveSamePersistentFields(item, existingItem, "supplier");
        assertHaveSamePersistentFields(item.getSupplier(), existingItem.getSupplier(), "version");
    }

    @Test
    public void getsAnItemListByCategory() {
        List<Item> items = persistence.getItemListByProduct(product.getProductId());
        assertThat(items.size(), equalTo(1));
        assertHaveSamePersistentFields(item, items.get(0), "supplier");
    }

    @Test
    public void getsAnInventoryQuantity() {
        int quantity = persistence.getInventoryQuantity(item.getItemId());
        assertThat(quantity, equalTo(item.getQuantity()));
    }

    @Test
    public void updatesAnInventoryQuantity() {
        Integer increment = new Integer("10");
        Map<String, Object> param = new HashMap<String, Object>(2);
        param.put(ITEM_ID.toString(), item.getItemId());
        param.put(INCREMENT.toString(), increment);

        persistence.updateInventoryQuantity(param);
        Item updatedItem = persistence.read(item.getItemId());
        assertThat(updatedItem.getQuantity(), equalTo(item.getQuantity() - increment));
    }

    @Test(expected = NullPointerException.class)
    public void throwsNullPointerExceptionWhenUpdatesAnInventoryQuantityWithNullParam() {
        persistence.updateInventoryQuantity(null);
    }

    @Test(expected = IllegalStateException.class)
    public void throwsIllegalStateExceptionWhenUpdatesAnInventoryWithParamWithMoreThanTwoKeyValueMappings() {
        Integer increment = new Integer("10");
        Map<String, Object> param = new HashMap<String, Object>(2);
        param.put(ITEM_ID.toString(), item.getItemId());
        param.put(INCREMENT.toString(), increment);
        param.put("invalidKey", "invalidValue");
        persistence.updateInventoryQuantity(param);
    }

    @Test(expected = IllegalStateException.class)
    public void throwsIllegalStateExceptionWhenUpdatesAnInventoryWithParamWithLessThanTwoKeyValueMappings() {
        Map<String, Object> param = new HashMap<String, Object>();
        persistence.updateInventoryQuantity(param);
    }
}
