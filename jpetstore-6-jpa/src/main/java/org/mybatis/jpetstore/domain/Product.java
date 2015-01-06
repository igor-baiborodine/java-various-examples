package org.mybatis.jpetstore.domain;

import com.google.common.base.Objects;
import org.mybatis.jpetstore.domain.builder.ProductBuilder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.*;
import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.emptyToNull;

@Entity(name = "product")
@NamedQueries({
        @NamedQuery(name = "Product.getProductListByCategory",
                query = "select p from product p where p.category = :category"),
        @NamedQuery(name = "Product.searchProductList",
                query = "select p from product p where lower(p.name) like :name")
})
public class Product implements Serializable {

    @Nonnull
    @Id
    @Column(name = "productid", unique = true, nullable = false, length = 10)
    private String productId;

    @Nullable
    @Column(name = "name", nullable = true, length = 80)
    private String name;

    @Nullable
    @Column(name = "descn", nullable = true, length = 255)
    private String description;

    @Nonnull
    @ManyToOne(optional = false, cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "categoryid", referencedColumnName = "catid")
    private Category category;

    protected Product() {}

    public Product(@Nonnull final String productId, @Nonnull final Category category) {
        checkNotNull(emptyToNull(productId), "Argument[productId] must not be null");
        checkNotNull(category, "Argument[category] must not be null");
        this.productId = productId.trim();
        this.category = Category.copy(category);
    }

    @Nonnull public static Product copy(Product product) {
        checkNotNull(product, "Argument[product] must not be null");
        return new ProductBuilder(product.getProductId(), product.getCategory())
                .name(product.getName())
                .description(product.getDescription())
                .build();
    }

    @Nonnull public String getProductId() {
        return productId;
    }
    // no setter for productId

    @Nonnull public String getCategoryId() {
        return category.getCategoryId();
    }

    @Nonnull
    public Category getCategory() {
        return category;
    }

    @Nullable public String getName() {
        return name;
    }

    public void setName(@Nullable final String name) {
        this.name = name;
    }

    @Nullable public String getDescription() {
        return description;
    }

    public void setDescription(@Nullable final String description) {
        this.description = description;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (! (o instanceof Product)) {
            return false;
        }
        Product that = (Product) o;
        return Objects.equal(this.getProductId(), that.getProductId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getProductId());
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("productId", productId)
                .add("categoryId", getCategoryId())
                .add("name", name)
                .add("description", description)
                .toString();
    }
}
