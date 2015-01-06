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

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.math.BigDecimal;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * @author Igor Baiborodine
 *
 */
public class LineItem
        extends AbstractBaseDomain<Integer>
        implements Serializable {

    private Integer orderId;
    private Integer lineNumber;
    private String itemId;

    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal total;
    private transient Item item;

    protected LineItem() {}

    public LineItem(@Nonnull final Integer lineNumber, @Nonnull final CartItem cartItem) {

        checkNotNull(lineNumber, "Argument[lineNumber] must not be null");
        checkNotNull(cartItem, "Argument[cartItem] must not be null");
        this.lineNumber = lineNumber;
        setItem(cartItem.getItem());
        setQuantity(cartItem.getQuantity());
    }

    @Override
    public Integer getId() {
        return orderId;
    }

    @Override
    public DBObject toDBObject() {

        BasicDBObject lineItemObj = new BasicDBObject();
        appendTo(lineItemObj, "line_number", getLineNumber());
        appendTo(lineItemObj, "item_id", getItemId());
        appendTo(lineItemObj, "quantity", getQuantity());
        appendTo(lineItemObj, "unit_price", getUnitPrice());

        return lineItemObj;
    }

    public static LineItem fromDBObject(@Nonnull final DBObject dbObj) {

        checkNotNull(dbObj, "Argument[dbObj] must not be null");

        BasicDBObject lineItemObj = (BasicDBObject) dbObj;
        LineItem lineItem = new LineItem();

        lineItem.setLineNumber(lineItemObj.getInt("line_number"));
        lineItem.setItemId(lineItemObj.getString("item_id"));

        String unitPrice = lineItemObj.getString("unit_price");
        if (!isNullOrEmpty(unitPrice)) {
            lineItem.setUnitPrice(new BigDecimal(unitPrice));
        }
        lineItem.setQuantity(lineItemObj.getInt("quantity"));

        return lineItem;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(@Nonnull final Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getLineNumber() {
        return lineNumber;
    }

    void setLineNumber(@Nonnull final Integer lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getItemId() {
        return itemId;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(@Nonnull final Integer quantity) {

        checkNotNull(quantity, "Argument[quantity] must not be null");
        this.quantity = quantity;
        calculateTotal();
    }

    public Item getItem() {
        return item;
    }

    public void setItem(@Nonnull final Item item) {
        checkNotNull(item, "Argument[item] must not be null");
        this.item = item;
        setItemId(item.getItemId());
        setUnitPrice(item.getListPrice());
        calculateTotal();
    }

    private void setItemId(@Nonnull final String itemId) {

        checkNotNull(itemId, "Argument[itemId] must not be null");
        this.itemId = itemId;
    }

    private void setUnitPrice(@Nonnull final BigDecimal unitPrice) {

        checkNotNull(unitPrice, "Argument[unitPrice] must not be null");
        this.unitPrice = unitPrice;
    }

    public BigDecimal getTotal() {
        return total;
    }

    private void calculateTotal() {

        if (quantity == null || unitPrice == null) {
            total = null;
        } else {
            total = unitPrice.multiply(new BigDecimal(quantity));
        }
    }

    @Override
    public boolean equals(final Object o) {

        if (this == o) return true;
        if (!(o instanceof LineItem)) return false;

        final LineItem that = (LineItem) o;
        return Objects.equal(this.orderId, that.orderId)
                && Objects.equal(this.orderId, that.orderId);
    }

    @Override
    public int hashCode() {

        int result = orderId != null ? Objects.hashCode(orderId) : 0;
        result = result + (lineNumber != null ? Objects.hashCode(lineNumber) : 0);
        return result;
    }

    @Override
    public String toString() {

        return Objects.toStringHelper(this)
                .add("orderId", orderId)
                .add("lineNumber", lineNumber)
                .add("itemId", itemId)
                .add("unitPrice", unitPrice)
                .add("quantity", quantity)
                .add("item", item)
                .add("total", total)
                .toString();
    }

}
