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
import org.mybatis.jpetstore.domain.builder.ItemBuilder;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.math.BigDecimal;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.isNullOrEmpty;
import static org.springframework.data.simpledb.attributeutil.AmazonSimpleDBUtil.decodeRealNumber;
import static org.springframework.data.simpledb.attributeutil.AmazonSimpleDBUtil.encodeAsRealNumber;

/**
 * @author Igor Baiborodine
 *
 */
public class Item
        extends AbstractBaseDomain<String>
        implements Serializable {

    private String itemId;
    private Product product;
    private Integer supplierId;

    private BigDecimal listPrice;
    private BigDecimal unitCost;
    private String status;
    private Integer quantity;

    private String attribute1;
    private String attribute2;
    private String attribute3;
    private String attribute4;
    private String attribute5;

    protected Item() {}

    public Item(@Nonnull final String itemId, @Nonnull final Product product) {

        checkNotNull(itemId, "Argument[itemId] must not be null");
        checkNotNull(product, "Argument[product] must not be null");
        this.itemId = itemId;
        this.product = product;
        this.quantity = new Integer("0");
    }

    @Override
    public String getId() {
        return itemId;
    }

    @Override
    public DBObject toDBObject() {

        BasicDBObject itemObj = new BasicDBObject();

        appendTo(itemObj, "_id", getItemId());
        appendTo(itemObj, "item_id", getItemId());
        appendTo(itemObj, "product", getProduct().toDBObject());
        appendTo(itemObj, "supplier_id", getSupplierId());

        appendTo(itemObj, "list_price", encodeAsRealNumber(getListPrice()));
        appendTo(itemObj, "unit_cost", encodeAsRealNumber(getUnitCost()));
        appendTo(itemObj, "status", getStatus());
        appendTo(itemObj, "quantity", getQuantity());

        appendTo(itemObj, "attribute_1", getAttribute1());
        appendTo(itemObj, "attribute_2", getAttribute2());
        appendTo(itemObj, "attribute_3", getAttribute3());
        appendTo(itemObj, "attribute_4", getAttribute4());
        appendTo(itemObj, "attribute_5", getAttribute5());

        return itemObj;
    }

    public static Item fromDBObject(@Nonnull final DBObject dbObj) {

        checkNotNull(dbObj, "Argument[dbObj] must not be null");

        BasicDBObject itemObj = (BasicDBObject) dbObj;

        DBObject productObj = (DBObject) itemObj.get("product");
        ItemBuilder builder = new ItemBuilder(itemObj.getString("item_id"),
                                              Product.fromDBObject(productObj));
        if (itemObj.get("supplier_id") != null) {
            builder.supplierId(itemObj.getInt("supplier_id"));
        }
        String listPrice = itemObj.getString("list_price");
        if (!isNullOrEmpty(listPrice)) {
            builder.listPrice(decodeRealNumber(listPrice));
        }
        String unitCost = itemObj.getString("unit_cost");
        if (!isNullOrEmpty(unitCost)) {
            builder.unitCost(decodeRealNumber(unitCost));
        }

        builder.status(itemObj.getString("status"))
                .quantity(itemObj.getInt("quantity"))
                .attribute1(itemObj.getString("attribute_1"))
                .attribute2(itemObj.getString("attribute_2"))
                .attribute3(itemObj.getString("attribute_3"))
                .attribute4(itemObj.getString("attribute_4"))
                .attribute5(itemObj.getString("attribute_5"));

        return builder.build();
    }

    public static Item copy(@Nonnull Item item) {

        checkNotNull(item, "Argument[item] must not be null");
        return new ItemBuilder(item.getItemId(), item.getProduct())
                .status(item.getStatus())
                .supplierId(item.getSupplierId())
                .listPrice(item.getListPrice())
                .unitCost(item.getUnitCost())
                .quantity(item.getQuantity())
                .attribute1(item.getAttribute1())
                .attribute2(item.getAttribute2())
                .attribute3(item.getAttribute3())
                .attribute4(item.getAttribute4())
                .attribute5(item.getAttribute5())
                .build();
    }

    @Override
    public boolean equals(final Object o) {

        if (this == o) return true;
        if (!(o instanceof Item)) return false;

        final Item that = (Item) o;
        return Objects.equal(this.itemId, that.getItemId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(itemId);
    }

    @Override
    public String toString() {

        return Objects.toStringHelper(this)
                .add("itemId", itemId)
                .add("product", product)
                .add("supplierId", supplierId)
                .add("listPrice", listPrice)
                .add("unitCost", unitCost)
                .add("status", status)
                .add("attribute1", attribute1)
                .add("attribute2", attribute2)
                .add("attribute3", attribute3)
                .add("attribute4", attribute4)
                .add("attribute5", attribute5)
                .add("quantity", quantity)
                .toString();
    }

    public String getItemId() {
        return itemId;
    }
    // no setter for itemId

    public Product getProduct() {
        return product;
    }

    public void setProductId(final Product product) {
        this.product = product;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(final Integer supplierId) {
        this.supplierId = supplierId;
    }

    public BigDecimal getListPrice() {
        return listPrice;
    }

    public void setListPrice(final BigDecimal listPrice) {
        this.listPrice = listPrice;
    }

    public BigDecimal getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(final BigDecimal unitCost) {
        this.unitCost = unitCost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(final Integer quantity) {
        checkNotNull(quantity, "Argument[quantity] must not be null");
        this.quantity = quantity;
    }

    public String getAttribute1() {
        return attribute1;
    }

    public void setAttribute1(final String attribute1) {
        this.attribute1 = attribute1;
    }

    public String getAttribute2() {
        return attribute2;
    }

    public void setAttribute2(final String attribute2) {
        this.attribute2 = attribute2;
    }

    public String getAttribute3() {
        return attribute3;
    }

    public void setAttribute3(final String attribute3) {
        this.attribute3 = attribute3;
    }

    public String getAttribute4() {
        return attribute4;
    }

    public void setAttribute4(final String attribute4) {
        this.attribute4 = attribute4;
    }

    public String getAttribute5() {
        return attribute5;
    }

    public void setAttribute5(final String attribute5) {
        this.attribute5 = attribute5;
    }

}
