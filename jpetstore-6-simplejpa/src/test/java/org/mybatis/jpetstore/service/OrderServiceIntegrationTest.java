package org.mybatis.jpetstore.service;

import org.junit.Before;
import org.junit.Test;
import org.mybatis.jpetstore.domain.*;
import org.mybatis.jpetstore.persistence.AccountMapper;
import org.mybatis.jpetstore.persistence.ItemMapper;
import org.mybatis.jpetstore.persistence.SequenceMapper;
import org.mybatis.jpetstore.persistence.jpa.AbstractBasePersistenceTest;
import org.mybatis.jpetstore.util.InitialDataLoadUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mybatis.jpetstore.persistence.helper.PersistenceReflection.assertHaveSamePersistentFields;
import static org.mybatis.jpetstore.persistence.helper.TestBuilderFactory.*;

/**
 * @author Igor Baiborodine
 */
public class OrderServiceIntegrationTest extends AbstractBasePersistenceTest {

    @Autowired
    private OrderService service;
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private InitialDataLoadUtils loadUtils;
    @Autowired
    private SequenceMapper sequenceMapper;

    @Before
    public void setUp() {

        cleanDatabase();
        loadUtils.importInitialDataToSimpleDB();
    }

    @Test
    public void insertsNewOrder_shouldInsertNewOrder() {

        Account account = accountMapper.getAccountByUsername(USERNAME);
        assertThat(account, notNullValue());

        Item item = itemMapper.getItem(ITEM_ID);
        assertThat(item, notNullValue());

        Order order = createOrderWithAllFields(account, item);
        Sequence nextSequence = sequenceMapper.getNextSequence(Domain.ORDER.toString());
        assertThat(nextSequence, notNullValue());
        assertThat(nextSequence.getNextId(), is("1001"));
        order.setOrderId(nextSequence.getNextId());

        service.insertOrder(order);

        Order insertedOrder = service.getOrder(order.getOrderId());
        assertHaveSamePersistentFields(order, insertedOrder);

        List<Order> orders = service.getOrdersByUsername(USERNAME);
        assertThat(orders.size(), is(1));
    }
}
