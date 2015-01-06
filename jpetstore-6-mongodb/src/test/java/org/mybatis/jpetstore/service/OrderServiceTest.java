package org.mybatis.jpetstore.service;

import org.junit.Test;
import org.mybatis.jpetstore.domain.*;
import org.mybatis.jpetstore.persistence.AccountMapper;
import org.mybatis.jpetstore.persistence.ItemMapper;
import org.mybatis.jpetstore.persistence.SequenceMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.mybatis.jpetstore.persistence.helper.TestBuilderFactory.*;

/**
 * JUnit-based integration tests for the {@link OrderService} class.
 *
 * @author Igor Baiborodine
 */
public class OrderServiceTest
        extends AbstractBaseServiceTest {

    @Autowired
    private OrderService service;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private SequenceMapper sequenceMapper;

    @Test
    public void orderService_integrationTests() {

        Sequence sequence = sequenceMapper.getSequence(new Sequence("order", -1));
        assertThat(sequence, notNullValue());

        assertThat(service.getOrdersByUsername(USERNAME).size(), equalTo(0));

        Account account = accountMapper.getAccountByUsername(USERNAME);
        assertThat(account, notNullValue());

        Item item = itemMapper.getItem(ITEMID);
        assertThat(item, notNullValue());

        service.insertOrder(createOrderWithAllFields(account, item));
        List<Order> orders = service.getOrdersByUsername(USERNAME);
        assertThat(orders.size(), equalTo(1));

        Order order = orders.get(0);
        assertThat(sequence.getNextId(), equalTo(order.getOrderId()));

        List<LineItem> lineItems = order.getLineItems();
        assertThat(lineItems.size(), equalTo(1));

        Integer lineItemQty = lineItems.get(0).getQuantity();
        Integer adjustedItemInventoryQty = itemMapper.getInventoryQuantity(item.getItemId());
        assertThat(adjustedItemInventoryQty, equalTo(item.getQuantity() - lineItemQty));

        Sequence adjustedSequence = sequenceMapper.getSequence(new Sequence("order", -1));
        assertThat(sequence.getNextId() + 1, equalTo(adjustedSequence.getNextId()));
    }

}
