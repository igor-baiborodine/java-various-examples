package org.mybatis.jpetstore.persistence.mongodb;

import com.google.common.collect.Lists;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import org.junit.Test;
import org.mybatis.jpetstore.domain.Item;
import org.mybatis.jpetstore.domain.Product;
import org.mybatis.jpetstore.persistence.NumericValueUpdateParam;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.*;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mybatis.jpetstore.persistence.helper.TestBuilderFactory.createItemBuilderWithAllFields;
import static org.mybatis.jpetstore.persistence.helper.TestBuilderFactory.createItemBuilderWithBaseFields;
import static org.mybatis.jpetstore.persistence.helper.TestBuilderFactory.createProductBuilderWithAllFields;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;
import static org.mybatis.jpetstore.persistence.NumericValueUpdateParam.*;

/**
 * JUnit based unit tests for the {@link ItemDao} class.
 *
 * @author Igor Baiborodine
 */
public class ItemDaoTest
        extends AbstractBaseDaoTest {

    @Autowired
    private ItemDao itemDao;

    @Override
    public String getCollectionName() {
        return itemDao.getCollectionName();
    }

    @Test(expected = NullPointerException.class)
    public void getItem_shouldThrowNPEForNullItemIdArgument() {

        itemDao.getItem(null);
    }

    @Test(expected = NullPointerException.class)
    public void getItem_shouldThrowNPEForEmptyItemIdArgument() {

        itemDao.getItem("");
    }

    @Test
    public void getItem_shouldFindExistingItemWithAllFields() {

        Item existingItem = createItemBuilderWithAllFields().build();
        insertItem(existingItem);

        Item item = itemDao.getItem(existingItem.getItemId());
        assertReflectionEquals(existingItem, item);
    }

    @Test
    public void getItem_shouldFindExistingItemWithBaseFields() {

        Item existingItem = createItemBuilderWithBaseFields().build();
        insertItem(existingItem);

        Item item = itemDao.getItem(existingItem.getItemId());
        assertReflectionEquals(existingItem, item);
    }

    @Test
    public void getInventoryQuantity_shouldFindQuantityForExistingItem() {

        Item existingItem = createItemBuilderWithBaseFields()
                .quantity(100).build();
        insertItem(existingItem);

        int quantity = itemDao.getInventoryQuantity(existingItem.getItemId());
        assertThat(quantity, equalTo(100));
    }

    @Test
    public void shouldSortItemByQuantity() {

        List<Item> items = Lists.newArrayListWithCapacity(3);
        Product product = createProductBuilderWithAllFields().build();

        Item item = createItemBuilderWithBaseFields("item-1", product).quantity(1).build();
        insertItem(item);
        items.add(item);

        item = createItemBuilderWithBaseFields("item-2", product).quantity(10).build();
        insertItem(item);
        items.add(item);

        item = createItemBuilderWithBaseFields("item-3", product).quantity(2).build();
        insertItem(item);
        items.add(item);

        Collections.sort(items, new Comparator<Item>() {
            @Override
            public int compare(final Item o1, final Item o2) {
                return o1.getQuantity().compareTo(o2.getQuantity());
            }
        });

        List<Item> sortedPersistedItems = new ArrayList<>();

        try (DBCursor cursor = collection.find().sort(new BasicDBObject("quantity", 1))) {
            while (cursor.hasNext()) {
                DBObject itemObj = cursor.next();
                sortedPersistedItems.add(Item.fromDBObject(itemObj));
            }
        }

        assertThat(sortedPersistedItems.size(), equalTo(3));
        assertThat(sortedPersistedItems.get(0).getQuantity(), equalTo(items.get(0).getQuantity()));
        assertThat(sortedPersistedItems.get(1).getQuantity(), equalTo(items.get(1).getQuantity()));
        assertThat(sortedPersistedItems.get(2).getQuantity(), equalTo(items.get(2).getQuantity()));
    }

    @Test
    public void shouldSortItemByUnitCost() {

        List<Item> items = Lists.newArrayListWithCapacity(3);
        Product product = createProductBuilderWithAllFields().build();

        Item item = createItemBuilderWithBaseFields("item-1", product)
                .unitCost(new BigDecimal("1.00")).build();
        insertItem(item);
        items.add(item);

        item = createItemBuilderWithBaseFields("item-2", product)
                .unitCost(new BigDecimal("10.00")).build();
        insertItem(item);
        items.add(item);

        item = createItemBuilderWithBaseFields("item-3", product)
                .unitCost(new BigDecimal("2.00")).build();
        insertItem(item);
        items.add(item);

        Collections.sort(items, new Comparator<Item>() {
            @Override
            public int compare(final Item o1, final Item o2) {
                return o1.getUnitCost().compareTo(o2.getUnitCost());
            }
        });


        List<Item> sortedPersistedItems = new ArrayList<>();

        try (DBCursor cursor = collection.find().sort(new BasicDBObject("unit_cost", 1))) {
            while (cursor.hasNext()) {
                DBObject itemObj = cursor.next();
                sortedPersistedItems.add(Item.fromDBObject(itemObj));
            }
        }
        assertThat(sortedPersistedItems.size(), equalTo(3));

        BigDecimal actualUnitCost = sortedPersistedItems.get(0).getUnitCost();
        assertThat(actualUnitCost.compareTo(items.get(0).getUnitCost()), equalTo(0));

        actualUnitCost = sortedPersistedItems.get(1).getUnitCost();
        assertThat(actualUnitCost.compareTo(items.get(1).getUnitCost()), equalTo(0));

        actualUnitCost = sortedPersistedItems.get(2).getUnitCost();
        assertThat(actualUnitCost.compareTo(items.get(2).getUnitCost()), equalTo(0));
    }

    @Test
    public void updateInventoryQuantity_shouldUpdateInventoryQuantityForExistingItem() {

        Item existingItem = createItemBuilderWithBaseFields().quantity(10).build();
        insertItem(existingItem);

        Map<NumericValueUpdateParam, Object> params = new HashMap<>(3);
        params.put(ID, existingItem.getItemId());
        params.put(KEY, "quantity");
        params.put(INCREMENT, 10);

        itemDao.updateInventoryQuantity(params);

        Item updatedItem = itemDao.getItem(existingItem.getItemId());
        assertThat(updatedItem.getQuantity(), equalTo(0));
    }

    @Test
    public void getItemListByProduct_shouldFindListWithExistingItems() {

        Item existingItem = createItemBuilderWithBaseFields().build();
        insertItem(existingItem);

        List<Item> items = itemDao.getItemListByProduct(existingItem.getProduct().getProductId());
        assertThat(items.size(), equalTo(1));
        assertReflectionEquals(existingItem, items.get(0));
    }

    private void insertItem(final Item item) {

        collection.insert(item.toDBObject());

        DBObject itemObj = collection.findOne(new BasicDBObject("_id", item.getItemId()));
        assertNotNull("Cannot find item with id[" + item.getItemId() + "]", itemObj);
    }

}
