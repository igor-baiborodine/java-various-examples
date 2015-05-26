/*
 *    Copyright 2010-2013 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.mybatis.jpetstore.domain;

import com.google.common.base.Objects;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nonnull;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * @author Eduardo Macarron, Igor Baiborodine
 */
public class Order
    extends AbstractBaseDomain<Integer>
    implements Serializable {

  private Integer orderId;
  private String username;
  private Date orderDate;

  private String courier;
  private BigDecimal totalPrice;
  private String locale;
  private String status;

  // shipping_address sub-document
  private String shipToFirstName;
  private String shipToLastName;
  private String shipAddress1;
  private String shipAddress2;
  private String shipCity;
  private String shipState;
  private String shipZip;
  private String shipCountry;

  // billing_address sub-document
  private String billToFirstName;
  private String billToLastName;
  private String billAddress1;
  private String billAddress2;
  private String billCity;
  private String billState;
  private String billZip;
  private String billCountry;

  // payment_info sub-document
  private String creditCard;
  private String expiryDate;
  private String cardType;

  private List<LineItem> lineItems = new ArrayList<>();

  public static Order fromDBObject(@Nonnull final DBObject dbObj) {

    checkNotNull(dbObj, "Argument[dbObj] must not be null");

    BasicDBObject orderObj = (BasicDBObject) dbObj;
    Order order = new Order();

    order.setOrderId(orderObj.getInt("order_id"));
    order.setUsername(orderObj.getString("username"));
    order.setOrderDate(orderObj.getDate("order_date"));
    order.setCourier(orderObj.getString("courier"));

    String totalPrice = orderObj.getString("total_price");
    if (!isNullOrEmpty(totalPrice)) {
      order.setTotalPrice(new BigDecimal(totalPrice));
    }
    order.setLocale(orderObj.getString("locale"));
    order.setStatus(orderObj.getString("status"));

    BasicDBObject shipToObj = (BasicDBObject) orderObj.get("shipping_address");
    order.setShipToFirstName(shipToObj.getString("ship_to_firstname"));
    order.setShipToLastName(shipToObj.getString("ship_to_lastname"));
    order.setShipAddress1(shipToObj.getString("ship_address_1"));
    order.setShipAddress2(shipToObj.getString("ship_address_2"));
    order.setShipCity(shipToObj.getString("ship_city"));
    order.setShipState(shipToObj.getString("ship_state"));
    order.setShipZip(shipToObj.getString("ship_zip"));
    order.setShipCountry(shipToObj.getString("ship_country"));

    BasicDBObject billToObj = (BasicDBObject) orderObj.get("billing_address");
    order.setBillToFirstName(billToObj.getString("bill_to_firstname"));
    order.setBillToLastName(billToObj.getString("bill_to_lastname"));
    order.setBillAddress1(billToObj.getString("bill_address_1"));
    order.setBillAddress2(billToObj.getString("bill_address_2"));
    order.setBillCity(billToObj.getString("bill_city"));
    order.setBillState(billToObj.getString("bill_state"));
    order.setBillZip(billToObj.getString("bill_zip"));
    order.setBillCountry(billToObj.getString("bill_country"));

    BasicDBObject paymentInfoObj = (BasicDBObject) orderObj.get("payment_info");
    order.setCreditCard(paymentInfoObj.getString("credit_card"));
    order.setExpiryDate(paymentInfoObj.getString("expiry_date"));
    order.setCardType(paymentInfoObj.getString("card_type"));

    List<DBObject> lineItemObjs = (ArrayList<DBObject>) orderObj.get("line_items");
    List<LineItem> lineItems = new ArrayList<>();

    for (DBObject lineItemObj : lineItemObjs) {
      LineItem lineItem = LineItem.fromDBObject(lineItemObj);
      lineItem.setOrderId(order.getOrderId());
      lineItems.add(lineItem);
    }
    order.setLineItems(lineItems);
    return order;
  }

  @Override
  public Integer getId() {
    return orderId;
  }

  @Override
  public DBObject toDBObject() {

    BasicDBObject orderObj = new BasicDBObject();

    appendTo(orderObj, "_id", getOrderId());
    appendTo(orderObj, "order_id", getOrderId());
    appendTo(orderObj, "username", getUsername());
    appendTo(orderObj, "order_date", getOrderDate());
    appendTo(orderObj, "courier", getCourier());
    appendTo(orderObj, "total_price", getTotalPrice());
    appendTo(orderObj, "locale", getLocale());
    appendTo(orderObj, "status", getStatus());

    BasicDBObject shipToObj = new BasicDBObject();
    appendTo(shipToObj, "ship_to_firstname", getShipToFirstName());
    appendTo(shipToObj, "ship_to_lastname", getShipToLastName());
    appendTo(shipToObj, "ship_address_1", getShipAddress1());
    appendTo(shipToObj, "ship_address_2", getShipAddress2());
    appendTo(shipToObj, "ship_city", getShipCity());
    appendTo(shipToObj, "ship_state", getShipState());
    appendTo(shipToObj, "ship_zip", getShipZip());
    appendTo(shipToObj, "ship_country", getShipCountry());
    appendTo(orderObj, "shipping_address", shipToObj);

    BasicDBObject billToObj = new BasicDBObject();
    appendTo(billToObj, "bill_to_firstname", getBillToFirstName());
    appendTo(billToObj, "bill_to_lastname", getBillToLastName());
    appendTo(billToObj, "bill_address_1", getBillAddress1());
    appendTo(billToObj, "bill_address_2", getBillAddress2());
    appendTo(billToObj, "bill_city", getBillCity());
    appendTo(billToObj, "bill_state", getBillState());
    appendTo(billToObj, "bill_zip", getBillZip());
    appendTo(billToObj, "bill_country", getBillCountry());
    appendTo(orderObj, "billing_address", billToObj);

    BasicDBObject paymentInfoObj = new BasicDBObject();
    appendTo(paymentInfoObj, "credit_card", getCreditCard());
    appendTo(paymentInfoObj, "expiry_date", getExpiryDate());
    appendTo(paymentInfoObj, "card_type", getCardType());
    appendTo(orderObj, "payment_info", paymentInfoObj);

    List<DBObject> lineItemObjs = new ArrayList<>();
    for (LineItem lineItem : lineItems) {
      lineItemObjs.add(lineItem.toDBObject());
    }
    appendTo(orderObj, "line_items", lineItemObjs);

    return orderObj;
  }

  @Override
  public boolean equals(final Object o) {

    if (this == o) return true;
    if (!(o instanceof Order)) return false;

    final Order that = (Order) o;
    return Objects.equal(this.orderId, that.getOrderId());
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(orderId);
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(this)
        .add("orderId", orderId)
        .add("username", username)
        .add("orderDate", orderDate)
        .add("shipAddress1", shipAddress1)
        .add("shipAddress2", shipAddress2)
        .add("shipCity", shipCity)
        .add("shipState", shipState)
        .add("shipZip", shipZip)
        .add("shipCountry", shipCountry)
        .add("billAddress1", billAddress1)
        .add("billAddress2", billAddress2)
        .add("billCity", billCity)
        .add("billState", billState)
        .add("billZip", billZip)
        .add("billCountry", billCountry)
        .add("courier", courier)
        .add("totalPrice", totalPrice)
        .add("billToFirstName", billToFirstName)
        .add("billToLastName", billToLastName)
        .add("shipToFirstName", shipToFirstName)
        .add("shipToLastName", shipToLastName)
        .add("creditCard", creditCard)
        .add("expiryDate", expiryDate)
        .add("cardType", cardType)
        .add("locale", locale)
        .add("status", status)
        .add("lineItems", lineItems)
        .toString();
  }

  public Integer getOrderId() {
    return orderId;
  }

  public void setOrderId(Integer orderId) {
    this.orderId = orderId;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Date getOrderDate() {
    return orderDate;
  }

  public void setOrderDate(Date orderDate) {
    this.orderDate = orderDate;
  }

  public String getShipAddress1() {
    return shipAddress1;
  }

  public void setShipAddress1(String shipAddress1) {
    this.shipAddress1 = shipAddress1;
  }

  public String getShipAddress2() {
    return shipAddress2;
  }

  public void setShipAddress2(String shipAddress2) {
    this.shipAddress2 = shipAddress2;
  }

  public String getShipCity() {
    return shipCity;
  }

  public void setShipCity(String shipCity) {
    this.shipCity = shipCity;
  }

  public String getShipState() {
    return shipState;
  }

  public void setShipState(String shipState) {
    this.shipState = shipState;
  }

  public String getShipZip() {
    return shipZip;
  }

  public void setShipZip(String shipZip) {
    this.shipZip = shipZip;
  }

  public String getShipCountry() {
    return shipCountry;
  }

  public void setShipCountry(String shipCountry) {
    this.shipCountry = shipCountry;
  }

  public String getBillAddress1() {
    return billAddress1;
  }

  public void setBillAddress1(String billAddress1) {
    this.billAddress1 = billAddress1;
  }

  public String getBillAddress2() {
    return billAddress2;
  }

  public void setBillAddress2(String billAddress2) {
    this.billAddress2 = billAddress2;
  }

  public String getBillCity() {
    return billCity;
  }

  public void setBillCity(String billCity) {
    this.billCity = billCity;
  }

  public String getBillState() {
    return billState;
  }

  public void setBillState(String billState) {
    this.billState = billState;
  }

  public String getBillZip() {
    return billZip;
  }

  public void setBillZip(String billZip) {
    this.billZip = billZip;
  }

  public String getBillCountry() {
    return billCountry;
  }

  public void setBillCountry(String billCountry) {
    this.billCountry = billCountry;
  }

  public String getCourier() {
    return courier;
  }

  public void setCourier(String courier) {
    this.courier = courier;
  }

  public BigDecimal getTotalPrice() {
    return totalPrice;
  }

  public void setTotalPrice(BigDecimal totalPrice) {
    this.totalPrice = totalPrice;
  }

  public String getBillToFirstName() {
    return billToFirstName;
  }

  public void setBillToFirstName(String billToFirstName) {
    this.billToFirstName = billToFirstName;
  }

  public String getBillToLastName() {
    return billToLastName;
  }

  public void setBillToLastName(String billToLastName) {
    this.billToLastName = billToLastName;
  }

  public String getShipToFirstName() {
    return shipToFirstName;
  }

  public void setShipToFirstName(String shipFoFirstName) {
    this.shipToFirstName = shipFoFirstName;
  }

  public String getShipToLastName() {
    return shipToLastName;
  }

  public void setShipToLastName(String shipToLastName) {
    this.shipToLastName = shipToLastName;
  }

  public String getCreditCard() {
    return creditCard;
  }

  public void setCreditCard(String creditCard) {
    this.creditCard = creditCard;
  }

  public String getExpiryDate() {
    return expiryDate;
  }

  public void setExpiryDate(String expiryDate) {
    this.expiryDate = expiryDate;
  }

  public String getCardType() {
    return cardType;
  }

  public void setCardType(String cardType) {
    this.cardType = cardType;
  }

  public String getLocale() {
    return locale;
  }

  public void setLocale(String locale) {
    this.locale = locale;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public List<LineItem> getLineItems() {
    return lineItems;
  }

  public void setLineItems(List<LineItem> lineItems) {
    this.lineItems = lineItems;
  }

  public void initOrder(Account account, Cart cart) {

    username = account.getUsername();
    orderDate = new Date();

    shipToFirstName = account.getFirstName();
    shipToLastName = account.getLastName();
    shipAddress1 = account.getAddress1();
    shipAddress2 = account.getAddress2();
    shipCity = account.getCity();
    shipState = account.getState();
    shipZip = account.getZip();
    shipCountry = account.getCountry();

    billToFirstName = account.getFirstName();
    billToLastName = account.getLastName();
    billAddress1 = account.getAddress1();
    billAddress2 = account.getAddress2();
    billCity = account.getCity();
    billState = account.getState();
    billZip = account.getZip();
    billCountry = account.getCountry();

    totalPrice = cart.getSubTotal();

    creditCard = "999 9999 9999 9999";
    expiryDate = "12/03";
    cardType = "Visa";
    courier = "UPS";
    locale = "CA";
    status = "P";

    Iterator<CartItem> i = cart.getAllCartItems();
    while (i.hasNext()) {
      CartItem cartItem = i.next();
      addLineItem(cartItem);
    }

  }

  public void addLineItem(CartItem cartItem) {
    LineItem lineItem = new LineItem(lineItems.size() + 1, cartItem);
    addLineItem(lineItem);
  }

  public void addLineItem(LineItem lineItem) {
    lineItems.add(lineItem);
  }

}
