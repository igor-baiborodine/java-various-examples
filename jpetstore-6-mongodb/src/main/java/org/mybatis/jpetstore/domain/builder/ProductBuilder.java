package org.mybatis.jpetstore.domain.builder;

import org.mybatis.jpetstore.domain.Product;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Igor Baiborodine
 */
public class ProductBuilder
    implements Builder<Product> {

  // required
  private String productId;
  private String categoryId;

  // optional
  private String name;
  private String description;

  public ProductBuilder(@Nonnull final String productId, @Nonnull final String categoryId) {
    // non null validation is done in Product(productId, categoryId) constructor
    this.productId = productId;
    this.categoryId = categoryId;
  }

  public ProductBuilder name(@Nullable final String name) {
    this.name = name;
    return this;
  }

  public ProductBuilder description(@Nullable final String description) {
    this.description = description;
    return this;
  }

  @Nonnull
  public Product build() {
    Product product = new Product(productId, categoryId);
    product.setName(name);
    product.setDescription(description);
    return product;
  }
}
