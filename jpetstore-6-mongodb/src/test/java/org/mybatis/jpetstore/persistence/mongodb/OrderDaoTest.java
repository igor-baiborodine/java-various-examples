package org.mybatis.jpetstore.persistence.mongodb;

import com.google.common.collect.Lists;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.junit.Test;
import org.mybatis.jpetstore.domain.LineItem;
import org.mybatis.jpetstore.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mybatis.jpetstore.persistence.helper.TestBuilderFactory.createLineItemBuilderWithAllFields;
import static org.mybatis.jpetstore.persistence.helper.TestBuilderFactory.createOrderWithAllFields;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

/**
 * JUnit based unit tests for the {@link OrderDao} class.
 *
 * @author Igor Baiborodine
 */
public class OrderDaoTest extends AbstractBaseDaoTest {

    @Autowired
    private OrderDao orderDao;

    @Override
    public String getCollectionName() {

        return orderDao.getCollectionName();
    }

    @Test
    public void lineItem_toDBObject_fromDBObject() {

        Integer orderId = null;
        Integer lineNumber = new Integer("1");
        LineItem expectedLineItem = createLineItemBuilderWithAllFields(orderId, lineNumber).build();
        DBObject lineItemObj = expectedLineItem.toDBObject();

        LineItem actualLineItem = LineItem.fromDBObject(lineItemObj);
        assertReflectionEquals(expectedLineItem, actualLineItem);
    }

    @Test
    public void order_toDBObject_fromDBObject() {

        Order expectedOrder = createOrderWithAllFields();
        DBObject orderObj = expectedOrder.toDBObject();

        Order actualOrder = Order.fromDBObject(orderObj);
        assertReflectionEquals(expectedOrder, actualOrder);
    }

    @Test
    public void getLineItemsByOrderId_shouldFindListWithLineItemsForExistingOrder() {

        Order existingOrder = createOrderWithAllFields();
        insertOrder(existingOrder);

        List<LineItem> lineItems = orderDao.getLineItemsByOrderId(existingOrder.getOrderId());
        assertReflectionEquals(existingOrder.getLineItems(), lineItems);
    }

    @Test
    public void insertLineItem_shouldAddNewLineItemToExistingOrder() {

        Order existingOrder = createOrderWithAllFields();
        insertOrder(existingOrder);

        Integer lineNumber = existingOrder.getLineItems().size() + 1;
        LineItem newLineItem = createLineItemBuilderWithAllFields(existingOrder.getOrderId(), lineNumber).build();
        orderDao.insertLineItem(newLineItem);

        DBObject orderObj = collection.findOne(new BasicDBObject("_id", existingOrder.getOrderId()));
        List<LineItem> lineItems = Lists.newArrayList();

        if (orderObj != null) {
            Order order = Order.fromDBObject(orderObj);
            lineItems.addAll(order.getLineItems());
        }
        assertThat(lineItems.contains(newLineItem), is(true));
    }

    @Test
    public void getOrder_shouldFindExistingOrder() {

        Order existingOrder = createOrderWithAllFields();
        insertOrder(existingOrder);

        Order order = orderDao.getOrder(existingOrder.getOrderId());
        assertReflectionEquals(existingOrder, order);
    }

    @Test
    public void insertOrder_shouldInsertNewOrderIntoCollection() {

        Order newOrder = createOrderWithAllFields();
        orderDao.insertOrder(newOrder);

        DBObject orderObj = collection.findOne(new BasicDBObject("_id", newOrder.getOrderId()));
        assertThat("Cannot find order with id [" + newOrder.getOrderId() + "]", orderObj, notNullValue());

        Order persistedOrder = Order.fromDBObject(orderObj);
        assertReflectionEquals(newOrder, persistedOrder);
    }

    @Test
    public void getOrderByUsername_shouldFindListWithExistingOrders() {

        Order existingOrder = createOrderWithAllFields();
        insertOrder(existingOrder);

        List<Order> orders = orderDao.getOrdersByUsername(existingOrder.getUsername());
        assertThat(orders.size(), is(1));
        assertReflectionEquals(existingOrder, orders.get(0));
    }

    @Test
    public void getOrderByUsername_shouldFindEmptyListForNonExistingUsername() {

        List<Order> orders = orderDao.getOrdersByUsername("NON_EXISTING_USERNAME");
        assertThat(orders.size(), is(0));
    }

    @Test
    public void insertOrderStatus_shouldUpdateStatusForExistingOrder() {

        Order existingOrder = createOrderWithAllFields();
        insertOrder(existingOrder);
        existingOrder.setStatus("NEW_STATUS");

        orderDao.insertOrderStatus(existingOrder);
        DBObject orderObj = collection.findOne(new BasicDBObject("_id", existingOrder.getOrderId()));
        Order orderWithNewStatus = Order.fromDBObject(orderObj);
        assertThat(existingOrder.getStatus(), is(orderWithNewStatus.getStatus()));
    }

    @Test(expected = RuntimeException.class)
    public void insertOrderStatus_shouldThrowRuntimeExceptionForNonExistingOrder() {

        Order nonExistingOrder = createOrderWithAllFields();
        DBObject orderObj = collection.findOne(new BasicDBObject("_id", nonExistingOrder.getOrderId()));
        assertThat(orderObj, nullValue());

        orderDao.insertOrderStatus(nonExistingOrder);
    }

    private void insertOrder(final Order order) {

        collection.insert(order.toDBObject());

        DBObject orderObj = collection.findOne(new BasicDBObject("_id", order.getOrderId()));
        assertThat("Cannot find order with id[" + order.getOrderId() + "]", orderObj, notNullValue());
    }

}
