package org.mybatis.jpetstore.domain;

import com.google.common.base.Objects;

import javax.annotation.Nonnull;
import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

import static com.google.common.base.Preconditions.checkNotNull;

@Entity(name = "Item")
// Named queries not supported by SimpleJPA
@NamedQueries({
        @NamedQuery(name = "Item.getItemListByProductId",
                query = "select i from Item i where i.productId = :productId"),
        @NamedQuery(name = "Item.getInventoryQuantity",
                query = "select i.quantity from Item i where i.itemId = :itemId"),
        @NamedQuery(name = "Item.updateInventoryQuantity",
                query = "update Item i set i.quantity = :quantity where i.itemId = :itemId")
})
public class Item
        implements Serializable {

    private String itemId;
    private String productId;
    private Product product;
    private String supplierId;
    private Supplier supplier;

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
        setItemId(itemId);
        setProduct(product);
    }

    @Id
    public String getItemId() {
        return itemId;
    }

    public void setItemId(final String itemId) {
        this.itemId = itemId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(final String productId) {
        this.productId = productId;
    }

    @Transient
    public Product getProduct() {
        return product;
    }

    public void setProduct(final Product product) {
        this.product = product;
        if (product != null) {
            setProductId(product.getProductId());
        }
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(final String supplierId) {
        this.supplierId = supplierId;
    }

    @Transient
    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(final Supplier supplier) {
        this.supplier = supplier;
        if (supplier != null) {
            setSupplierId(supplier.getSupplierId());
        }
    }

    public BigDecimal getListPrice() {
        return listPrice;
    }

    public void setListPrice(final BigDecimal listPrice) {
        if (listPrice != null) {
            this.listPrice = listPrice.stripTrailingZeros();
        }
    }

    public BigDecimal getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(final BigDecimal unitCost) {
        if (unitCost != null) {
            this.unitCost = unitCost.stripTrailingZeros();
        }
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
                .add("productId", productId)
                .add("supplierId", supplierId)
                .add("listPrice", listPrice)
                .add("unitCost", unitCost)
                .add("status", status)
                .add("quantity", quantity)
                .add("attribute1", attribute1)
                .add("attribute2", attribute2)
                .add("attribute3", attribute3)
                .add("attribute4", attribute4)
                .add("attribute5", attribute5)
                .toString();
    }

}
