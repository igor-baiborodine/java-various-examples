package org.mybatis.jpetstore.domain;

import com.google.common.base.Objects;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

import static com.google.common.base.Preconditions.checkNotNull;

@Entity(name = "lineitem")
@IdClass(LineItemID.class)
@NamedQuery(name = "LineItem.getLineItemsByOrderId",
        query = "select li from lineitem li where li.orderId = :orderId")
public class LineItem
        extends BaseDomain
        implements Serializable {

    @Nonnull @Id @Column(name = "orderid", insertable = true, nullable = false)
    private Integer orderId;

    @Nonnull @Id @Column(name = "linenum", insertable = true, nullable = false)
    private Integer lineNumber;

    @Nonnull @Column(name = "itemid", nullable = false)
    private String itemId;

    @Nonnull @Column(name = "unitprice", nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @Nonnull @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Nonnull
    private transient Item item;
    @Nullable
    private transient BigDecimal total;

    protected LineItem() {}

    public LineItem(@Nonnull final Integer lineNumber, @Nonnull final CartItem cartItem) {
        checkNotNull(lineNumber, "Argument[lineNumber] must not be null");
        checkNotNull(cartItem, "Argument[cartItem] must not be null");
        this.lineNumber = lineNumber;
        setItem(cartItem.getItem());
        setQuantity(cartItem.getQuantity());
    }

    @Nonnull
    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(@Nonnull final Integer orderId) {
        this.orderId = orderId;
    }

    @Nonnull
    public Integer getLineNumber() {
        return lineNumber;
    }
    // no setter for lineNumber

    @Nonnull
    public String getItemId() {
        return itemId;
    }

    @Nonnull
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    @Nonnull
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(@Nonnull final Integer quantity) {
        checkNotNull(quantity, "Argument[quantity] must not be null");
        this.quantity = quantity;
        calculateTotal();
    }

    @Nonnull
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

    public @Nullable BigDecimal getTotal() {
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
                .add("version", version)
                .add("itemId", itemId)
                .add("unitPrice", unitPrice)
                .add("quantity", quantity)
                .add("item", item)
                .add("total", total)
                .toString();
    }
}
