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

import javax.annotation.Nonnull;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Eduardo Macarron, Igor Baiborodine
 */
@Entity(name = "Order")
// Named queries not supported by SimpleJPA
@NamedQuery(name = "Order.getOrdersByUsername",
        query = "select o from Order o where o.username = :username")
public class Order implements Serializable {

    private String orderId;
    private String username;
    private Date orderDate;

    private String courier;
    private BigDecimal totalPrice;
    private String locale;
    private String status;

    private String shipToFirstName;
    private String shipToLastName;
    private String shipAddress1;
    private String shipAddress2;
    private String shipCity;
    private String shipState;
    private String shipZip;
    private String shipCountry;

    private String billToFirstName;
    private String billToLastName;
    private String billAddress1;
    private String billAddress2;
    private String billCity;
    private String billState;
    private String billZip;
    private String billCountry;

    private String creditCard;
    private String expiryDate;
    private String cardType;

    private List<LineItem> lineItems = new ArrayList<>();

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

    @Id
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(final String orderId) {
        this.orderId = orderId;
    }

    @Transient
    public Integer getIntOrderId() {
        return new Integer(orderId);
    }

    public void setIntOrderId(@Nonnull final Integer intOrderId) {

        checkNotNull(intOrderId, "Argument[intOrderId] must not be null");
        this.orderId = intOrderId.toString();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(final Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getCourier() {
        return courier;
    }

    public void setCourier(final String courier) {
        this.courier = courier;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(final BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(final String locale) {
        this.locale = locale;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public String getShipToFirstName() {
        return shipToFirstName;
    }

    public void setShipToFirstName(final String shipToFirstName) {
        this.shipToFirstName = shipToFirstName;
    }

    public String getShipToLastName() {
        return shipToLastName;
    }

    public void setShipToLastName(final String shipToLastName) {
        this.shipToLastName = shipToLastName;
    }

    public String getShipAddress1() {
        return shipAddress1;
    }

    public void setShipAddress1(final String shipAddress1) {
        this.shipAddress1 = shipAddress1;
    }

    public String getShipAddress2() {
        return shipAddress2;
    }

    public void setShipAddress2(final String shipAddress2) {
        this.shipAddress2 = shipAddress2;
    }

    public String getShipCity() {
        return shipCity;
    }

    public void setShipCity(final String shipCity) {
        this.shipCity = shipCity;
    }

    public String getShipState() {
        return shipState;
    }

    public void setShipState(final String shipState) {
        this.shipState = shipState;
    }

    public String getShipZip() {
        return shipZip;
    }

    public void setShipZip(final String shipZip) {
        this.shipZip = shipZip;
    }

    public String getShipCountry() {
        return shipCountry;
    }

    public void setShipCountry(final String shipCountry) {
        this.shipCountry = shipCountry;
    }

    public String getBillToFirstName() {
        return billToFirstName;
    }

    public void setBillToFirstName(final String billToFirstName) {
        this.billToFirstName = billToFirstName;
    }

    public String getBillToLastName() {
        return billToLastName;
    }

    public void setBillToLastName(final String billToLastName) {
        this.billToLastName = billToLastName;
    }

    public String getBillAddress1() {
        return billAddress1;
    }

    public void setBillAddress1(final String billAddress1) {
        this.billAddress1 = billAddress1;
    }

    public String getBillAddress2() {
        return billAddress2;
    }

    public void setBillAddress2(final String billAddress2) {
        this.billAddress2 = billAddress2;
    }

    public String getBillCity() {
        return billCity;
    }

    public void setBillCity(final String billCity) {
        this.billCity = billCity;
    }

    public String getBillState() {
        return billState;
    }

    public void setBillState(final String billState) {
        this.billState = billState;
    }

    public String getBillZip() {
        return billZip;
    }

    public void setBillZip(final String billZip) {
        this.billZip = billZip;
    }

    public String getBillCountry() {
        return billCountry;
    }

    public void setBillCountry(final String billCountry) {
        this.billCountry = billCountry;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(final String creditCard) {
        this.creditCard = creditCard;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(final String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(final String cardType) {
        this.cardType = cardType;
    }

    @Transient
    public List<LineItem> getLineItems() {
        return lineItems;
    }

    public void setLineItems(final List<LineItem> lineItems) {
        this.lineItems = lineItems;
    }
}
