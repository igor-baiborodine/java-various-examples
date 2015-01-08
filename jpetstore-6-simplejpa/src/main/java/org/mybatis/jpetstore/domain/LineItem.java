package org.mybatis.jpetstore.domain;

import com.google.common.base.Objects;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;
import java.io.Serializable;
import java.math.BigDecimal;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

@Entity(name = "LineItem")
// Named queries not supported by SimpleJPA
@NamedQuery(name = "LineItem.getLineItemsByOrderId",
        query = "select li from LineItem li where li.orderId = :orderId")
public class LineItem implements Serializable {

    private String orderId;
    private String lineNumber;
    private String itemId;

    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal total;
    private transient Item item;

    protected LineItem() {}

    public LineItem(final int lineNumber, @Nonnull final CartItem cartItem) {

        checkNotNull(lineNumber, "Argument[lineNumber] must not be null");
        checkNotNull(cartItem, "Argument[cartItem] must not be null");

        setIntLineNumber(lineNumber);
        setItem(cartItem.getItem());
        setQuantity(cartItem.getQuantity());
    }

    @Id
    public String getId() {
        return orderId + "-" + lineNumber;
    }

    public void setId(String id) {
        // empty
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(@Nonnull final String orderId) {

        checkNotNull(orderId, "Argument[orderId] must not be null");
        checkArgument(orderId.equals(new Integer(orderId).toString()),
                "Argument[orderId] has illegal value[" + orderId + "]");
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

    public String getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(@Nonnull final String lineNumber) {

        checkNotNull(lineNumber, "Argument[lineNumber] must not be null");
        checkArgument(lineNumber.equals(new Integer(lineNumber).toString()),
                "Argument[lineNumber] has illegal value[" + lineNumber + "]");
        this.lineNumber = lineNumber;
    }

    @Transient
    public Integer getIntLineNumber() {
        return new Integer(lineNumber);
    }

    public void setIntLineNumber(@Nonnull final Integer intLineNumber) {

        checkNotNull(intLineNumber, "Argument[intLineNumber] must not be null");
        this.lineNumber = intLineNumber.toString();
    }

    public String getItemId() {
        return itemId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(final Integer quantity) {

        checkNotNull(quantity, "Argument[quantity] must not be null");
        this.quantity = quantity;
        calculateTotal();
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(@Nonnull final BigDecimal unitPrice) {

        checkNotNull(unitPrice, "Argument[unitPrice] must not be null");
        this.unitPrice = unitPrice.stripTrailingZeros();
    }

    @Transient
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

    public void setItemId(@Nonnull final String itemId) {
        checkNotNull(itemId, "Argument[itemId] must not be null");
        this.itemId = itemId;
    }

    public @Nullable BigDecimal getTotal() {
        return total;
    }

    public void setTotal(@Nullable final BigDecimal total) {
        this.total = total;
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
                .add("id", getId())
                .add("orderId", orderId)
                .add("lineNumber", lineNumber)
                .add("itemId", itemId)
                .add("quantity", quantity)
                .add("unitPrice", unitPrice)
                .add("total", total)
                .add("item", item)
                .toString();
    }

}
