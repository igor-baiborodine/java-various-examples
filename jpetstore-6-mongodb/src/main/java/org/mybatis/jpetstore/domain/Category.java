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
import org.mybatis.jpetstore.domain.builder.CategoryBuilder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Igor Baiborodine
 */
public class Category
        extends AbstractBaseDomain<String>
        implements Serializable {

  private String categoryId;
  private String name;
  private String description;

    protected Category() {}

    public Category(String categoryId) {

        checkNotNull(categoryId, "Argument[categoryId] must not be null");
        this.categoryId = categoryId.trim();
    }

    @Override
    public String getId() {
        return categoryId;
    }

    @Nonnull
    public DBObject toDBObject() {

        BasicDBObject categoryObj = new BasicDBObject();
        appendTo(categoryObj, "_id", getCategoryId());
        appendTo(categoryObj, "category_id", getCategoryId());
        appendTo(categoryObj, "name", getName());
        appendTo(categoryObj, "description", getDescription());

        return categoryObj;
    }

    @Nonnull
    public static Category fromDBObject(@Nonnull final DBObject categoryObj) {

        checkNotNull(categoryObj, "Argument[categoryObj] must not be null");
        CategoryBuilder builder =
                new CategoryBuilder((String) categoryObj.get("category_id"))
                .name((String) categoryObj.get("name"))
                .description((String) categoryObj.get("description"));
        return builder.build();
    }

    @Nonnull
    public static Category copy(@Nonnull final Category category) {

        checkNotNull(category, "Argument[category] must not be null");
        return new CategoryBuilder(category.getCategoryId())
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }

    @Nonnull
    public String getCategoryId() {
        return categoryId;
    }
    // no setter for categoryId

    @Nullable
    public String getName() {
        return name;
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    @Override
    public boolean equals(final Object o) {

        if (o == this) {
            return true;
        }
        if (! (o instanceof Category)) {
            return false;
        }
        Category that = (Category) o;
        return Objects.equal(this.getCategoryId(), that.getCategoryId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getCategoryId());
    }

    @Override
    public String toString() {

        return Objects.toStringHelper(this)
                .add("categoryId", categoryId)
                .add("name", name)
                .add("description", description)
                .toString();
    }

}
