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
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.mybatis.jpetstore.domain.builder.ProductBuilder;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.emptyToNull;

/**
 * @author Igor Baiborodine
 */
public class Product
        extends AbstractBaseDomain<String>
        implements Serializable {

    private String productId;
    private String categoryId;
    private String name;
    private String description;

    protected Product() {}

    public Product(@Nonnull final String productId, @Nonnull final String categoryId) {

        checkNotNull(emptyToNull(productId), "Argument[productId] must not be null");
        checkNotNull(emptyToNull(categoryId), "Argument[categoryId] must not be null");
        this.productId = productId.trim();
        this.categoryId = categoryId.trim();
    }

    @Override
    public String getId() {
        return productId;
    }

    @Override
    public DBObject toDBObject() {

        BasicDBObject productObj = new BasicDBObject();
        appendTo(productObj, "_id", getProductId());
        appendTo(productObj, "product_id", getProductId());
        appendTo(productObj, "category_id", getCategoryId());
        appendTo(productObj, "name", getName());
        appendTo(productObj, "name_keywords", getNameKeywords());
        appendTo(productObj, "description", getDescription());

        return productObj;
    }

    @Nonnull
    public static Product fromDBObject(@Nonnull final DBObject productObj) {

        checkNotNull(productObj, "Argument[productObj] must not be null");
        ProductBuilder builder =
                new ProductBuilder(
                        (String) productObj.get("product_id"),
                        (String) productObj.get("category_id"))
                        .name((String) productObj.get("name"))
                        .description((String) productObj.get("description"));
        return builder.build();
    }

    @Nonnull
    public static Product copy(Product product) {

        checkNotNull(product, "Argument[product] must not be null");
        return new ProductBuilder(product.getProductId(), product.getCategoryId())
                .name(product.getName())
                .description(product.getDescription())
                .build();
    }

    public List<String> getNameKeywords() {

        Iterable<String> iterable = Splitter.on(" ").split(getName());
        List<String> nameKeywords = Lists.newArrayList(iterable);

        for (int i=0; i < nameKeywords.size(); i++) {
            nameKeywords.set(i, nameKeywords.get(i).toLowerCase());
        }
        return nameKeywords;
    }

    public String getProductId() {
        return productId;
    }
    // no setter for productId

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
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
                .add("categoryId", categoryId)
                .add("name", name)
                .add("description", description)
                .toString();
    }

}
