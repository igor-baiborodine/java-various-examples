package org.mybatis.jpetstore.service;

import org.junit.Test;
import org.mybatis.jpetstore.domain.Account;
import org.mybatis.jpetstore.domain.Item;
import org.mybatis.jpetstore.domain.LineItem;
import org.mybatis.jpetstore.domain.Order;
import org.mybatis.jpetstore.domain.Sequence;
import org.mybatis.jpetstore.persistence.AccountMapper;
import org.mybatis.jpetstore.persistence.ItemMapper;
import org.mybatis.jpetstore.persistence.SequenceMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mybatis.jpetstore.persistence.helper.TestBuilderFactory.ITEM_ID;
import static org.mybatis.jpetstore.persistence.helper.TestBuilderFactory.USERNAME;
import static org.mybatis.jpetstore.persistence.helper.TestBuilderFactory.createOrderWithAllFields;

/**
 * JUnit-based integration tests for the {@link OrderService} class.
 *
 * @author Igor Baiborodine
 */
public class OrderServiceTest extends AbstractServiceTest {

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

    assertThat(service.getOrdersByUsername(USERNAME).size(), is(0));

    Account account = accountMapper.getAccountByUsername(USERNAME);
    assertThat(account, notNullValue());

    Item item = itemMapper.getItem(ITEM_ID);
    assertThat(item, notNullValue());

    service.insertOrder(createOrderWithAllFields(account, item));
    List<Order> orders = service.getOrdersByUsername(USERNAME);
    assertThat(orders.size(), is(1));

    Order order = orders.get(0);
    assertThat(sequence.getNextId(), is(order.getOrderId()));

    List<LineItem> lineItems = order.getLineItems();
    assertThat(lineItems.size(), is(1));

    Integer lineItemQty = lineItems.get(0).getQuantity();
    Integer adjustedItemInventoryQty = itemMapper.getInventoryQuantity(item.getItemId());
    assertThat(adjustedItemInventoryQty, is(item.getQuantity() - lineItemQty));

    Sequence adjustedSequence = sequenceMapper.getSequence(new Sequence("order", -1));
    assertThat(sequence.getNextId() + 1, is(adjustedSequence.getNextId()));
  }

}
