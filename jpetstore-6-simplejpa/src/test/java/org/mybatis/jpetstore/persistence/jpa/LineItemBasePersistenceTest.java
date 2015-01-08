package org.mybatis.jpetstore.persistence.jpa;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mybatis.jpetstore.domain.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mybatis.jpetstore.persistence.helper.PersistenceReflection.assertHaveSamePersistentFields;
import static org.mybatis.jpetstore.persistence.helper.TestBuilderFactory.*;

/**
 * @author Igor Baiborodine
 */
public class LineItemBasePersistenceTest extends AbstractBasePersistenceTest {

    @Autowired
    private LineItemBasePersistence persistence;

    private Category existingCategory;
    private Product existingProduct;
    private Supplier existingSupplier;
    private Item existingItem;

    @Before
    public void setUp() {

        cleanDomain(Domain.LINEITEM);

        existingCategory = createCategoryBuilderWithAllFields().build();
        existingProduct = createProductBuilderWithAllFields(existingCategory).build();
        existingSupplier = createSupplierBuilderWithAllFields().build();
        existingItem = createItemBuilderWithAllFields(existingProduct).supplier(existingSupplier).build();
    }

    @After
    public void tearDown() {

        existingCategory = null;
        existingProduct = null;
        existingSupplier = null;
        existingItem = null;
    }

    @Test
    public void insertLineItem_shouldInsertNewLineItem() {

        LineItem newLineItem = createLineItemBuilderWithAllFields(existingItem).build();
        persistence.insertLineItem(newLineItem);

        LineItem persistedLineItem = persistence.read(newLineItem.getId());
        assertHaveSamePersistentFields(newLineItem, persistedLineItem, "item");
    }

    @Test
    public void getLineItemsByOrderId_shouldFindListWithExistingLineItemsByOrderId() {

        LineItem existingLineItem = createLineItemBuilderWithAllFields(existingItem).build();
        persistence.create(existingLineItem);

        List<LineItem> lineItems = persistence.getLineItemsByOrderId(existingLineItem.getOrderId());
        assertThat(lineItems.size(), is(1));
        assertHaveSamePersistentFields(existingLineItem, lineItems.get(0), "item");
    }
}
