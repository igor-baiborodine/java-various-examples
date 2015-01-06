package org.mybatis.jpetstore.persistence.jpa;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mybatis.jpetstore.domain.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.mybatis.jpetstore.persistence.helper.PersistenceReflection.assertHaveSamePersistentFields;
import static org.mybatis.jpetstore.persistence.helper.TestBuilderFactory.*;
import static org.mybatis.jpetstore.persistence.helper.TestBuilderFactory.createItemBuilderWithAllFields;

/**
 * @author Igor Baiborodine
 */
public class LineItemBasePersistenceTest
        extends AbstractBasePersistenceTest {

    @Autowired
    private LineItemBasePersistence persistence;
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
    }

    @After
    public void tearDown() throws Exception {
        category = null;
        product = null;
        supplier = null;
        item = null;
    }

    @Test
    public void insertsALineItem() {
        LineItem newLineItem = createLineItemBuilderWithAllFields(item).build();
        assertThat(newLineItem.getVersion(), nullValue());

        persistence.insertLineItem(newLineItem);
        assertThat(newLineItem.getVersion(), notNullValue());

        persistence.flushContext();
        persistence.detachEntity(newLineItem);
        assertFalse(persistence.contextContains(newLineItem));

        LineItem persistedLineItem = persistence.getEntityManager().find(LineItem.class,
                new LineItemID(newLineItem.getOrderId(), newLineItem.getLineNumber()));
        assertHaveSamePersistentFields(newLineItem, persistedLineItem, "version");
    }

    @Test
    public void getsLineItemsByOrderId() {
        LineItem persistedLineItem = createLineItemBuilderWithAllFields(item).build();
        persistence.insertLineItem(persistedLineItem);
        persistence.flushContext();
        persistence.detachEntity(persistedLineItem);

        List<LineItem> lineItems = persistence.getLineItemsByOrderId(persistedLineItem.getOrderId());
        assertThat(lineItems.size(), equalTo(1));
        assertHaveSamePersistentFields(persistedLineItem, lineItems.get(0));
    }
}
