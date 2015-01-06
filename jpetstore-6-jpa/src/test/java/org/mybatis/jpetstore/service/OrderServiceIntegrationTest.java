package org.mybatis.jpetstore.service;

import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.jpetstore.domain.Account;
import org.mybatis.jpetstore.domain.Item;
import org.mybatis.jpetstore.domain.Order;
import org.mybatis.jpetstore.persistence.AccountMapper;
import org.mybatis.jpetstore.persistence.ItemMapper;
import org.mybatis.jpetstore.persistence.helper.DatabaseCleaner;
import org.mybatis.jpetstore.persistence.helper.DatabaseLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mybatis.jpetstore.persistence.helper.PersistenceReflection.assertHaveSamePersistentFields;
import static org.mybatis.jpetstore.persistence.helper.TestBuilderFactory.createOrderWithAllFields;

/**
 * @author Igor Baiborodine
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:/persistence-context-test.xml")
public class OrderServiceIntegrationTest {

    private static final String USERNAME = "j2ee";
    private static final String ITEMID = "EST-6";

    @Autowired
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    @Autowired
    private OrderService service;
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private ItemMapper itemMapper;

    @Before
    public void cleanDatabase() throws Exception {
        entityManager = entityManagerFactory.createEntityManager();
        new DatabaseCleaner(entityManager).clean();
        new DatabaseLoader().loadTestData();
    }

    @After
    public void tearDown() {
        entityManager.close();
        entityManager = null;
    }

    @Test
    public void getsOrdersByUsername() {
        List<Order> orders = service.getOrdersByUsername(USERNAME);
        MatcherAssert.assertThat(orders.size(), equalTo(0));
    }

    @Test
    public void insertsANewOrder() {
        Account account = accountMapper.getAccountByUsername(USERNAME);
        assertThat(account, notNullValue());

        Item item = itemMapper.getItem(ITEMID);
        assertThat(item, notNullValue());

        Order order = createOrderWithAllFields(account, item);
        assertThat(order.getOrderId(), nullValue());
        assertThat(order.getVersion(), nullValue());

        service.insertOrder(order);
        assertThat(order.getOrderId(), notNullValue());
        assertThat(order.getVersion(), notNullValue());

        Order insertedOrder = service.getOrder(order.getOrderId());
        assertHaveSamePersistentFields(order, insertedOrder);
    }
}
