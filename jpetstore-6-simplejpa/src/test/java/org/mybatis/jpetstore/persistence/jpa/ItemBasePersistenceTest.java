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

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mybatis.jpetstore.domain.Domain.ITEM;
import static org.mybatis.jpetstore.domain.ItemInventoryParam.INCREMENT;
import static org.mybatis.jpetstore.domain.ItemInventoryParam.ITEM_ID;
import static org.mybatis.jpetstore.persistence.helper.PersistenceReflection.assertHaveSamePersistentFields;
import static org.mybatis.jpetstore.persistence.helper.TestBuilderFactory.*;

/**
 * @author Igor Baiborodine
 */
public class ItemBasePersistenceTest extends AbstractBasePersistenceTest {

    @Autowired
    private ItemBasePersistence persistence;

    private Category existingCategory;
    private Product existingProduct;
    private Supplier existingSupplier;
    private Item existingItem;

    @Before
    public void setUp() {

        cleanDomain(ITEM);

        existingCategory = createCategoryBuilderWithAllFields().build();
        existingProduct = createProductBuilderWithAllFields(existingCategory).build();
        existingSupplier = createSupplierBuilderWithAllFields().build();
        existingItem = createItemBuilderWithAllFields(existingProduct).supplier(existingSupplier).build();

        persistence.create(existingItem);
        assertCount(ITEM, 1);
    }

    @After
    public void tearDown() {

        existingCategory = null;
        existingProduct = null;
        existingSupplier = null;
        existingItem = null;
    }

    @Test
    public void getItem_shouldFindExistingItem() {

        Item foundItem = persistence.getItem(this.existingItem.getItemId());
        assertHaveSamePersistentFields(this.existingItem, foundItem, "supplier");
    }

    @Test
    public void getItemListByProduct_shouldFindListWithExistingItemByProduct() {

        List<Item> items = persistence.getItemListByProduct(existingProduct.getProductId());
        assertThat(items.size(), is(1));
        assertHaveSamePersistentFields(existingItem, items.get(0), "supplier");
    }

    @Test
    public void getInventoryQuantity_shouldFindInventoryQuantityByItemId() {

        int quantity = persistence.getInventoryQuantity(existingItem.getItemId());
        assertThat(quantity, is(existingItem.getQuantity()));
    }

    @Test
    public void updateInventoryQuantity_shouldUpdateQuantityForExistingItem() {

        Integer increment = new Integer("10");
        Map<String, Object> param = new HashMap<>(2);
        param.put(ITEM_ID.toString(), existingItem.getItemId());
        param.put(INCREMENT.toString(), increment);

        persistence.updateInventoryQuantity(param);
        Item updatedItem = persistence.read(existingItem.getItemId());
        assertThat(updatedItem.getQuantity(), is(existingItem.getQuantity() - increment));
    }

    @Test(expected = NullPointerException.class)
    public void updateInventoryQuantity_shouldThrowNPEOnNullParamMap() {

        persistence.updateInventoryQuantity(null);
    }

    @Test(expected = IllegalStateException.class)
    public void updateInventoryQuantity_shouldThroeIllegalStateExceptionOnParamMapWithMoreThanTwoKeyValueMappings() {

        Integer increment = new Integer("10");
        Map<String, Object> param = new HashMap<>(2);
        param.put(ITEM_ID.toString(), existingItem.getItemId());
        param.put(INCREMENT.toString(), increment);
        param.put("invalidKey", "invalidValue");
        persistence.updateInventoryQuantity(param);
    }

    @Test(expected = IllegalStateException.class)
    public void updateInventoryQuantity_shouldThrowIllegalStateExceptionOnParamMapWithLessThanTwoKeyValueMappings() {

        Map<String, Object> param = new HashMap<>();
        persistence.updateInventoryQuantity(param);
    }
}
