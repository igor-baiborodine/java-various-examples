package org.mybatis.jpetstore.persistence.helper;

import org.mybatis.jpetstore.domain.Account;
import org.mybatis.jpetstore.domain.Cart;
import org.mybatis.jpetstore.domain.CartItem;
import org.mybatis.jpetstore.domain.Category;
import org.mybatis.jpetstore.domain.Item;
import org.mybatis.jpetstore.domain.LineItem;
import org.mybatis.jpetstore.domain.Order;
import org.mybatis.jpetstore.domain.Product;
import org.mybatis.jpetstore.domain.builder.AccountBuilder;
import org.mybatis.jpetstore.domain.builder.Builder;
import org.mybatis.jpetstore.domain.builder.CategoryBuilder;
import org.mybatis.jpetstore.domain.builder.ItemBuilder;
import org.mybatis.jpetstore.domain.builder.LineItemBuilder;
import org.mybatis.jpetstore.domain.builder.ProductBuilder;

import java.math.BigDecimal;

import javax.annotation.Nonnull;

/**
 * @author Igor Baiborodine
 */
public class TestBuilderFactory {

  public static final String USERNAME = "brodriguez";
  public static final String PASSWORD = "beer";
  public static final String FIRST_NAME = "Bender";
  public static final String LAST_NAME = "Rodriguez";
  public static final String ITEM_ID = "EST-1";

  public static Builder<Account> createAccountBuilderWithBaseFields(
      String username, String password, String firstname, String lastname) {

    return new AccountBuilder(username, password, firstname, lastname);
  }

  public static Builder<Account> createAccountBuilderWithBaseFields() {

    return new AccountBuilder(USERNAME, PASSWORD, FIRST_NAME, LAST_NAME);
  }

  public static Builder<Account> createAccountBuilderWithAllFields() {

    return ((AccountBuilder) createAccountBuilderWithBaseFields())
        .email("bender.rodriguez@planet-express.earth")
        .status("OK")
        .address1("101 Robot Arms Ave.")
        .address2("#901")
        .city("New New York City")
        .state("NY")
        .zip("01234")
        .country("Earth")
        .phone("0123456789")
        .favouriteCategoryId("DOGS")
        .languagePreference("Martian")
        .listOption(true)
        .bannerOption(true)
        .bannerName("<image src=\"../images/banner_dogs.gif\">");
  }

  public static Builder<Category> createCategoryBuilderWithAllFields() {
    return new CategoryBuilder("FISH")
        .name("Fish")
        .description("<image src='../images/fish_icon.gif'><font size='5' color='blue'>Fish</font>");
  }

  public static ProductBuilder createProductBuilderWithBaseFields(
      @Nonnull final String productId, @Nonnull final String categoryId) {
    return new ProductBuilder(productId, categoryId);
  }

  public static ProductBuilder createProductBuilderWithAllFields() {
    return new ProductBuilder("FI-SW-01", "FISH")
        .name("Angelfish")
        .description("<image src='../images/fish1.gif'>Salt Water fish from Australia");
  }

  public static ItemBuilder createItemBuilderWithBaseFields() {
    return createItemBuilderWithBaseFields(ITEM_ID, createProductBuilderWithAllFields().build());
  }

  public static ItemBuilder createItemBuilderWithBaseFields(final String itemId, final Product product) {
    return new ItemBuilder(itemId, product);
  }

  public static ItemBuilder createItemBuilderWithAllFields() {
    return new ItemBuilder("EST-1", createProductBuilderWithAllFields().build())
        .supplierId(new Integer("1"))
        .status("P")
        .listPrice(new BigDecimal("16.50"))
        .unitCost(new BigDecimal("10.00"))
        .quantity(new Integer("100"))
        .attribute1("attribute1 value")
        .attribute2("attribute2 value")
        .attribute3("attribute3 value")
        .attribute4("attribute4 value")
        .attribute5("attribute5 value");
  }

  public static LineItemBuilder createLineItemBuilderWithAllFields(
      @Nonnull final Integer orderId, @Nonnull final Integer lineNumber) {
    Item item = createItemBuilderWithAllFields().build();
    return createLineItemBuilderWithAllFields(orderId, lineNumber, item);
  }

  public static LineItemBuilder createLineItemBuilderWithAllFields(
      @Nonnull final Integer orderId, @Nonnull final Integer lineNumber, @Nonnull final Item item) {
    CartItem cartItem = new CartItem();
    cartItem.setItem(item);
    cartItem.setQuantity(10);
    return new LineItemBuilder(lineNumber, cartItem).orderId(orderId);
  }

  public static Order createOrderWithAllFields() {

    Order order = createOrderWithAllFields(
        createAccountBuilderWithAllFields().build(),
        createItemBuilderWithAllFields().build());
    order.setOrderId(new Integer("1"));

    for (LineItem lineItem : order.getLineItems()) {
      lineItem.setOrderId(order.getOrderId());
    }
    return order;
  }

  public static Order createOrderWithAllFields(@Nonnull Account account, @Nonnull Item item) {
    Cart cart = new Cart();
    boolean isInStock = true;
    cart.addItem(item, isInStock);

    Order order = new Order();
    order.initOrder(account, cart);
    return order;
  }
}
