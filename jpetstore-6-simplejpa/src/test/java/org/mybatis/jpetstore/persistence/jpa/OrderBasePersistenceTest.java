package org.mybatis.jpetstore.persistence.jpa;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mybatis.jpetstore.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mybatis.jpetstore.domain.Domain.ORDER;
import static org.mybatis.jpetstore.persistence.helper.PersistenceReflection.assertHaveSamePersistentFields;
import static org.mybatis.jpetstore.persistence.helper.TestBuilderFactory.createOrderWithAllFields;

/**
 * @author Igor Baiborodine
 */
public class OrderBasePersistenceTest extends AbstractBasePersistenceTest {

    @Autowired
    private OrderBasePersistence persistence;

    @Before
    public void setUp() {

        cleanDomain(ORDER);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void getOrder_shouldFindExistingOrder() {

        Order existingOrder = createOrderWithAllFields();
        existingOrder.setIntOrderId(1);
        persistence.create(existingOrder);

        Order order = persistence.getOrder(existingOrder.getOrderId());
        assertHaveSamePersistentFields(existingOrder, order, "lineItems");
    }

    @Test
    public void insertOrder_shouldInsertNewOrder() {

        Order newOrder = createOrderWithAllFields();
        newOrder.setOrderId("1");
        persistence.insertOrder(newOrder);

        Order persistedOrder = persistence.read(newOrder.getOrderId());
        assertHaveSamePersistentFields(newOrder, persistedOrder, "lineItems");
    }

    @Test
    public void getOrderByUsername_shouldFindListWithExistingOrders() {

        Order existingOrder = createOrderWithAllFields();
        existingOrder.setIntOrderId(1);
        persistence.create(existingOrder);

        List<Order> orders = persistence.getOrdersByUsername(existingOrder.getUsername());
        assertThat(orders.size(), is(1));
        assertHaveSamePersistentFields(existingOrder, orders.get(0), "lineItems");
    }

    @Test
    public void getOrderByUsername_shouldFindEmptyListForNonExistingUsername() {

        List<Order> orders = persistence.getOrdersByUsername("NON_EXISTING_USERNAME");
        assertThat(orders.size(), is(0));
    }

}
