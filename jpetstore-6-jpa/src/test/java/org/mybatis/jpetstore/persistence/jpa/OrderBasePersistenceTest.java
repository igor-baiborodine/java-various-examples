package org.mybatis.jpetstore.persistence.jpa;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mybatis.jpetstore.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mybatis.jpetstore.persistence.helper.PersistenceReflection.assertHaveSamePersistentFields;
import static org.mybatis.jpetstore.persistence.helper.TestBuilderFactory.createOrderWithAllFields;

/**
 * @author Igor Baiborodine
 */
public class OrderBasePersistenceTest
        extends AbstractBasePersistenceTest {

    @Autowired
    private OrderBasePersistence persistence;
    private Order order;

    @Before
    public void setUp() {
        persistence.clearContext();
        cleanDatabase(persistence.getEntityManager());
    }

    @After
    public void tearDown() {
        order = null;
    }

    @Test
    public void insertsANewOrder() {
        Order newOrder = createOrderWithAllFields();
        persistence.insertOrder(newOrder);
        assertThat(newOrder.getOrderId(), notNullValue());
        assertThat(newOrder.getVersion(), notNullValue());

        persistence.flushContext();
        persistence.detachEntity(newOrder);

        Order persistedOrder = persistence.getEntityManager()
                .find(Order.class, newOrder.getOrderId());
        assertHaveSamePersistentFields(newOrder, persistedOrder);
    }

    @Test
    public void getsAnExistingOrder() {
        insertOrder();
        Order existingOrder = persistence.getOrder(order.getOrderId());
        assertHaveSamePersistentFields(order, existingOrder);
    }

    @Test
    public void getsOrdersByUsername() {
        insertOrder();
        List<Order> orders = persistence.getOrdersByUsername("brodriguez");
        assertThat(orders.size(), equalTo(1));
        assertHaveSamePersistentFields(order, orders.get(0));
    }

    private void insertOrder() {
        order = createOrderWithAllFields();
        persistence.getEntityManager().persist(order);
        persistence.flushContext();
        persistence.detachEntity(order);
    }
}
