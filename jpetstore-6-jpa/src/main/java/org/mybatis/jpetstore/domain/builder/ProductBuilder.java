package org.mybatis.jpetstore.domain.builder;

import org.mybatis.jpetstore.domain.Category;
import org.mybatis.jpetstore.domain.Product;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Igor Baiborodine
 */
public class ProductBuilder implements Builder<Product> {

    // required
    private String productId;
    private Category category;

    // optional
    private String name;
    private String description;

    public ProductBuilder(@Nonnull final String productId, @Nonnull final Category category) {
        // non null validation is done in Product(productId, category) constructor
        this.productId = productId;
        this.category = category;
    }

    public ProductBuilder name(@Nullable final String name) {
        this.name = name;
        return this;
    }

    public ProductBuilder description(@Nullable final String description) {
        this.description = description;
        return this;
    }

    @Override
    @Nonnull public Product build() {
        Product product = new Product(productId, category);
        product.setName(name);
        product.setDescription(description);
        return product;
    }
}
