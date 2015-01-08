package org.mybatis.jpetstore.domain;

import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import org.mybatis.jpetstore.domain.builder.ProductBuilder;

import javax.annotation.Nonnull;
import javax.persistence.*;
import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.emptyToNull;

@Entity(name = "Product")
// Named queries not supported by SimpleJPA
@NamedQueries({
        @NamedQuery(name = "Product.getProductListByCategory",
                query = "select p from Product p where p.categoryId = :categoryId"),
        @NamedQuery(name = "Product.searchProductList",
                query = "select p from Product p where lower(p.name) like :name")
})
public class Product implements Serializable, Comparable<Product> {

    private String productId;
    private String name;
    private String description;
    private String categoryId;
    private Category category;

    protected Product() {}

    public Product(@Nonnull final String productId, @Nonnull final Category category) {

        checkNotNull(emptyToNull(productId), "Argument[productId] must not be null");
        checkNotNull(category, "Argument[category] must not be null");
        setProductId(productId.trim());
        setCategory(category);
    }

    public static Product copy(Product product) {

        checkNotNull(product, "Argument[product] must not be null");
        return new ProductBuilder(product.getProductId(), product.getCategory())
                .name(product.getName())
                .description(product.getDescription())
                .build();
    }

    @Id
    public String getProductId() {
        return productId;
    }

    public void setProductId(final String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(final String categoryId) {
        this.categoryId = categoryId;
    }

    @Transient
    public Category getCategory() {
        return category;
    }

    public void setCategory(final Category category) {
        this.category = category;
        if (category != null) {
            setCategoryId(category.getCategoryId());
        }
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
                .add("categoryId", categoryId)
                .add("name", name)
                .add("description", description)
                .toString();
    }

    @Override
    public int compareTo(final Product that) {

        return ComparisonChain.start()
                .compare(this.getProductId(), that.getProductId())
                .compare(this.getCategoryId(), that.getCategoryId())
                .compare(this.getName(), that.getName())
                .compare(this.getDescription(), that.getDescription())
                .result();
    }
}


